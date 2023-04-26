package io.pivotal.services.dataTx.circuitBreaker.functions;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;

/**
 * Simple Apache Geode/GemFire function to return true.
 * @author Gregory Green
 */
public class CircuitBreakerStatusFunc implements Function, Declarable
{
    @Override
    public boolean isHA()
    {
        return false;
    }

    @Override
    public void execute(FunctionContext context)
    {
        context.getResultSender().lastResult(true);
    }

    /**
     *
     * @return "CircuitBreakerStatusFunc"
     */
    @Override
    public String getId()
    {
        return CircuitBreakerStatusFunc.class.getSimpleName();
    }
}
