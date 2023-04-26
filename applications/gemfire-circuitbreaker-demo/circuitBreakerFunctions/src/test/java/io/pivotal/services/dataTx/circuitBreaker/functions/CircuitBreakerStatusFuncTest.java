package io.pivotal.services.dataTx.circuitBreaker.functions;

import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.ResultSender;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * CircuitBreakerStatusFunc Tester.
 *
 * @author Gregory Green
 * @version 1.0
 * @since <pre>Aug 6, 2019</pre>
 */
public class CircuitBreakerStatusFuncTest
{

    /**
     * Method: isHA()
     */
    @Test
    public void testIsHA()
    throws Exception
    {

        assertFalse(new CircuitBreakerStatusFunc().isHA());
    }

    /**
     * Method: execute(FunctionContext context)
     */
    @Test
    public void testExecute()
    throws Exception
    {
        CircuitBreakerStatusFunc func = new CircuitBreakerStatusFunc();

        FunctionContext<?> context = Mockito.mock(FunctionContext.class);
        ResultSender<Object> rs = Mockito.mock(ResultSender.class);
        Mockito.when(context.getResultSender()).thenReturn(rs);
        func.execute(context);

        Mockito.verify(rs).lastResult(Mockito.anyBoolean());


    }

    @Test
    public void test_function_name(){


        assertEquals("CircuitBreakerStatusFunc",new CircuitBreakerStatusFunc().getId());
    }


} 
