package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.springframework.boot.SpringApplication;

/**
 * Checks for switch back to primary source when circuit is open
 * @author Gregory Green
 */
public class CircuitCloserRunner implements Runnable
{
    private final CircuitBreaker  circuitBreaker;
    private final long sleepPeriodMs;

    public CircuitCloserRunner(CircuitBreaker circuitBreaker, long sleepPeriodMs)
    {
        this.circuitBreaker = circuitBreaker;
        this.sleepPeriodMs = sleepPeriodMs;
    }//-----------------------------------------------

    /**
     * Implement switch to the secondary cluster connection
     */
    @Override
    public void run()
    {
        try{ Thread.sleep(5000); } catch(Exception e){}

        try
        {
            while(true)
            {
                System.out.println("Checking if primary source is  up");
                if(this.circuitBreaker.isPrimaryUp())
                {
                    System.out.println("primary source is UP ->  switch to it primary");
                    this.circuitBreaker.closeToPrimary();

                    System.out.println("SUCCESSFULLY ->  switched to it primary");
                    break;
                }

                Thread.sleep(sleepPeriodMs);
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
