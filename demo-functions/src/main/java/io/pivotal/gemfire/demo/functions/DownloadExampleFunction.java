package io.pivotal.gemfire.demo.functions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadExampleFunction implements Function<String[]>
{

	private static final long serialVersionUID = 1638106763427761339L;

	public void execute(FunctionContext<String[]> context)
	{
		if(!(context instanceof RegionFunctionContext))
			throw new FunctionException("Must execution the function onRegion");
		
		
		Logger logger = LogManager.getLogger();
		
		
		RegionFunctionContext rfc = (RegionFunctionContext) context;
		
		
		Region<String,PdxInstance> region = rfc.getDataSet();
		
		//Local export local data
		region = PartitionRegionHelper.getLocalData(region);
		

		File file = new File("exportFile.txt");
		
		logger.info("START Writing file:"+file.getAbsolutePath());
		
		//Get local keys
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8.newEncoder()))
		{
				for (Map.Entry<String, PdxInstance> entry : region.entrySet())
				{
					writer.write(JSONFormatter.toJSON(entry.getValue())+"\n");
				}
				
				logger.info("END Wrote file:"+file.getAbsolutePath());
				
				context.getResultSender().lastResult(file.getAbsolutePath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			context.getResultSender().sendException(e);
			throw new FunctionException(e.getMessage(),e);
		}
		
	}//------------------------------------------------
	@Override
	public String getId()
	{
		return getClass().getSimpleName();
	}
}
