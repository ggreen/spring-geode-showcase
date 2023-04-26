package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.apache.geode.cache.client.*;
import org.apache.geode.cache.execute.*;
import org.apache.geode.distributed.PoolCancelledException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.ConnectException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Circuit breaker implementation object.
 *
 * @author Gregory Green
 */
public class CircuitBreaker
{

    private final String primaryPoolName;
    private final String secondaryPoolName;
    private final String primaryLocators;
    private final String secondaryLocators;
    private final long sleepPeriodMs;
    private static final String FUNCTION_NAME ="CircuitBreakerStatusFunc";
    private final ExecutorService executor;
    private static ConfigurableApplicationContext context=null;

    /**
     * Constructor
     * @param primaryPool the primary connection pool
     * @param backupPool the backyp secondary pool
     * @param primaryLocators the primary locator connection string
     * @param secondaryLocators the secondary locator connection string
     * @param sleepPeriodMs the sleep period
     */
    public CircuitBreaker(String primaryPool, String backupPool, String primaryLocators, String secondaryLocators,
                          long sleepPeriodMs)
    {
        this.primaryLocators = primaryLocators;
        this.secondaryLocators = secondaryLocators;
        this.primaryPoolName = primaryPool;
        this.secondaryPoolName = backupPool;
        this.sleepPeriodMs = sleepPeriodMs;
        this.executor = Executors.newCachedThreadPool();
    }//-------------------------------------------

    /**
     * Determine if the primary cluster is and up
     * @return true if primary is up
     * @throws Exception when unknown error occurs (ex: circuit breaker function not deployed)
     */
    public boolean isPrimaryUp()
    throws Exception
    {

        return doCheck(PoolManager.find(this.primaryPoolName));

    }//-------------------------------------------

    /**
     * Determine if the secondary cluster is up and running
     * @return true if secondary is up
     */
    public boolean isSecondaryUp()
    throws Exception
    {
          return doCheck(PoolManager.find(this.secondaryPoolName));
    }//-------------------------------------------

    /**
     * Set the spring context singleton
     * @param appContext the application context
     */
    public static void setContext(ConfigurableApplicationContext appContext)
    {
        CircuitBreaker.context = appContext;
    }//------------------------------------

    /**
     * Close circuit from secondary and switch to primary
     */
    public void closeToPrimary()
    {
        Runnable r = () -> {
            CircuitBreaker.context.close();

            String[] sourceArgs = {"--spring.data.gemfire.locators="+this.primaryLocators};

            CircuitBreaker.context = SpringApplication.run(DemoApp.class,sourceArgs );

        };

        executor.submit(r);

    }//-----------------------------------------------
    /**
     * Open circuit to switch from primary to secondary
     */
    public void openCircuit()
    throws Exception
    {
        System.out.println("CHECKING IF primaur is UP");

        if(this.isPrimaryUp()){
            System.out.println("Primary is UP, so NOT SWITHING");
            return;
        }

        this.openToSecondary();
    }//-------------------------------------------
    /**
     * Open current connection to secondary
     */
    public void openToSecondary()
    {
        System.out.println("Opening to secondary");

        Runnable openAndSwitch = () -> {


            CircuitBreaker.context.close();


            CircuitBreaker.context = null;
            System.gc();

            String[] sourceArgs = {"--spring.data.gemfire.locators="+this.secondaryLocators};


            CircuitBreaker.context = SpringApplication.run(DemoApp.class,sourceArgs );

        };

        this.executor.submit(openAndSwitch);

       // startCloseCircuitRunner();


    }//-------------------------------------------

    /**
     * Start a thread to closer the circuit and switch batch to primary
     */
    public void startCloseCircuitRunner()
    {
        Runnable closerRunner = new CircuitCloserRunner(this,sleepPeriodMs);
        this.executor.submit(closerRunner);
    }//-------------------------------------------

    private boolean doCheck(Pool secondaryPool)
    throws Exception
    {
        try
        {
            ResultCollector rc = FunctionService
                    .onServer(secondaryPool)
                    .execute(FUNCTION_NAME);

            return checkResultsOK(rc);

        }
        catch(ServerConnectivityException | ConnectException | PoolCancelledException e){
            e.printStackTrace();
            return false;
        }


    }//-------------------------------------------

    private boolean checkResultsOK(ResultCollector<?, ?> resultCollector)
    throws Exception
    {
        if(resultCollector == null)
            throw new IllegalArgumentException("resultCollector required");


        Object resultsObject = resultCollector.getResult();
        if(resultsObject == null)
            throw new RuntimeException("No results found");

        //Return a result in collection (for a single response)
        Collection<Object> collectionResults = (Collection<Object>)resultsObject;

        //if empty return null
        if(collectionResults.isEmpty())
            throw new RuntimeException("No results found");


        for (Object inputObj : collectionResults)
        {
            if(inputObj instanceof Exception )
                throw (Exception)inputObj;

            if(inputObj == null)
                continue;

            if(inputObj instanceof Collection)
            {
                Collection<Object> innerCollection = (Collection)inputObj;
                for (Object obj:innerCollection) {
                    if( obj instanceof Exception)
                        throw (Exception)obj;
                }
            }
        }

        return true;

    }
}
