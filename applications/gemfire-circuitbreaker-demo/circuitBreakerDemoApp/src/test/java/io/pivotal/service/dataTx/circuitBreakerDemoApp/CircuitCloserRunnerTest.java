package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CircuitCloserRunnerTest
{

    @Test
    public void run()
    {
        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        new CircuitCloserRunner(cb,2000);


    }
}