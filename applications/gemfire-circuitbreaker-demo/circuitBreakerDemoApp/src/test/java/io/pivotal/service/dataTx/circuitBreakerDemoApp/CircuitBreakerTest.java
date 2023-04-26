package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/** 
* CircuitBreaker Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 5, 2019</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@IfProfileValue(name = "integration")
public class CircuitBreakerTest {

    @Value("${primaryLocators}")
    private String primaryLocators;

    @Value("${secondaryLocators}")
    private String secondaryLocators;


    /**
     * Test the ability to open/close circuits
     */
    @Test
    public void test_self_maintenance(){
        CircuitBreaker cb = createCircuitBreaker();

        ConfigurableApplicationContext applicationContext = Mockito.mock(ConfigurableApplicationContext.class);

        ApplicationArguments appArgs = mock(ApplicationArguments.class);

        when(applicationContext.getBean(ApplicationArguments.class)).thenReturn(appArgs);
        CircuitBreaker.setContext(applicationContext);

        cb.openToSecondary();

        Mockito.verify(applicationContext).close();

    }//-------------------------------------------

    /**
     *
     * @throws Exception when unknown exception occurs
     */
    @Test
    public void test_is_primary_up ()
    throws Exception
    {
        CircuitBreaker cb = createCircuitBreaker();


        assertTrue(cb.isPrimaryUp());

    }//----------------------------------------------------

    private CircuitBreaker createCircuitBreaker()
    {
        return new CircuitBreaker("A","B",
                primaryLocators,
                secondaryLocators,
                2000);
    }

    /**
     *
     * @throws Exception when the secondard pool is up down
     */
    @Test
    public void test_is_secondard_up ()
    throws Exception
    {
        CircuitBreaker cb = createCircuitBreaker();

        assertTrue(cb.isSecondaryUp());
    }
} 
