package com.vmware.spring.geode.showcase.account.function;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.execute.*;
import org.apache.geode.cache.query.*;
import org.apache.geode.pdx.PdxInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Properties;

public class AccountCountInNyFunction implements Function<PdxInstance>, Declarable
{
    private Logger logger = LogManager.getLogger(AccountCountInNyFunction.class);
    private static final String empty ="";
    private Cache cache;
    private QueryService queryService;

    public void initialize(Cache cache, Properties properties) {
     this.cache = cache;
     this.queryService = cache.getQueryService();
    }

    @Override
    public String getId()
    {
        return getClass().getSimpleName();
    }

    @Override
    public void execute(FunctionContext<PdxInstance> functionContext)
    {
        logger.info("Executing account function");

        ResultSender<String> sender = functionContext.getResultSender();

        if(! (functionContext instanceof RegionFunctionContext)){
            sender.lastResult("");
            return;
        }
        RegionFunctionContext rfc = (RegionFunctionContext) functionContext;


        if(queryService == null)
            queryService = CacheFactory.getAnyInstance().getQueryService();

        Query query = queryService.newQuery("select count(*) as cnt from /Account a, /Location l where a.id = l.id and l.stateCode = 'NY'");

        try {

            Collection<Object> results = (Collection<Object>) query.execute(rfc);

            if(results == null || results.isEmpty()) {
                sender.lastResult("");
                return;
            }

            Object cnt = results.iterator().next();

           sender.lastResult(String.valueOf(cnt));
        }
        catch (FunctionDomainException | TypeMismatchException | NameResolutionException |QueryInvocationTargetException e) {
            logger.error(e);
            throw new FunctionException("Cannot execute query error:"+e.getMessage());
        }
    }
}
