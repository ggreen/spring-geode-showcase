package io.pivotal.gemfire.playground.orders.demo;

import java.nio.file.Paths;

import org.apache.geode.cache.RegionShortcut;
import gedi.solutions.geode.util.GemFireMgmt;
import nyla.solutions.core.io.IO;
import nyla.solutions.core.operations.Shell;
import nyla.solutions.core.operations.Shell.ProcessInfo;
import nyla.solutions.core.patterns.jmx.JMX;
import nyla.solutions.core.util.Config;
import nyla.solutions.core.util.Debugger;


public class Gfsh
{
	private final String location = Config.getProperty("gfsh_location");
	private final String runtimeDir = Config.getProperty("runtime_location","runtime");
	public Gfsh()
	{
	}//------------------------------------------------
	public void startCluster()
	throws Exception
	{
		IO.mkdir(Paths.get(runtimeDir+"/locator").toFile());
		IO.mkdir(Paths.get(runtimeDir+"/server").toFile());
		
		Shell shell = new Shell();
		ProcessInfo pi = shell.execute(location+"/gfsh","-e","start locator  --dir=runtime/locator  --name=locator  --port=10334");
		
		
		System.out.println(pi.exitValue);
		System.out.println(pi.output);
		System.out.println(pi.error);
		
		 pi = shell.execute(location+"/gfsh",
		"-e","start server --name=server --dir="+runtimeDir+"/server --locators=localhost[10334]"); 
		 
			System.out.println(pi.exitValue);
			System.out.println("OUTPUT:"+pi.output);
			System.out.println("ERROR:"+pi.error);
	}
	
	public void createRegion(String regionName,RegionShortcut regionShortcut)
	{

		Shell shell = new Shell();
		
		 ProcessInfo pi = shell.execute(location+"/gfsh",
		 "-e","connect",
		 "-e","create region --name=Test --type="+regionShortcut);
		 
		System.out.println(pi.exitValue);
		System.out.println("OUTPUT:"+pi.output);
		System.out.println("ERROR:"+pi.error);
		
	}//------------------------------------------------
	public void shutdown()
	{
		try(JMX jmx = JMX.connect("localhost", 1099))
		{
			String[] members = GemFireMgmt.shutDown(jmx);
			
			Debugger.println("members:"+Debugger.toString(members));
			
			GemFireMgmt.stopLocator(jmx, "locator");
		}
	}//------------------------------------------------
}
