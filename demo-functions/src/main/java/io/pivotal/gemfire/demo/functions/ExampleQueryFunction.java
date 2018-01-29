package io.pivotal.gemfire.demo.functions;

import java.util.List;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.query.Query;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.apache.geode.pdx.PdxInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleQueryFunction
implements Function<String[]>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3177843133740126757L;

	public String getId()
	{
		return getClass().getSimpleName();
	}//------------------------------------------------

	public boolean hasResult()
	{
		return true;
	}//------------------------------------------------

	@SuppressWarnings("unchecked")
	public void execute(FunctionContext<String[]> context)
	{
		Cache cache = CacheFactory.getAnyInstance();
		QueryService queryService = cache.getQueryService();

		String oql = context.getArguments()[0];
		
		Logger logger = LogManager.getLogger();
		
		try
		{
			Query query = queryService.newQuery(oql);
			
			logger.info("query:"+oql);

			SelectResults<PdxInstance> result =  (SelectResults<PdxInstance>)query
			.execute((RegionFunctionContext) context);

			List<PdxInstance> list = result.asList();
			
			logger.info("results:"+list);
			
			context.getResultSender().lastResult(list);
			
		}
		catch (RuntimeException e)
		{
			logger.error(e);
			context.getResultSender().sendException(e);
			throw e;
		}
		catch (Exception e)
		{
			logger.error(e);
			context.getResultSender().sendException(e);
			throw new FunctionException(e.getMessage(),e);
		}
	}
}
