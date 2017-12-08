package io.pivotal.gemfire.playground.orders.demo;

import java.nio.file.Paths;

import nyla.solutions.core.io.IO;
import nyla.solutions.core.operations.Shell;
import nyla.solutions.core.operations.Shell.ProcessInfo;
import nyla.solutions.core.util.Config;


public class Gfsh
{
	
	public void startCluster()
	throws Exception
	{
		
		String location = Config.getProperty("gfsh_location");
		String runtimeDir = Config.getProperty("runtime_location","runtime");
		

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
		
		
		 pi = shell.execute(location+"/gfsh",
		 "-e","connect",
		 "-e","create region --name=Test --type=PARTITION");
		
		 
	}
	
}
