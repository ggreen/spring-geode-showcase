package io.pivotal.gemfire.playground.orders.demo;

public class JunitClusterStart
{
//	private final String gfshPath;
//	
//	public JunitClusterStart(String gfshPath)
//	{
//		this.gfshPath = gfshPath;
//	}
//	
//	public void execute(String command)
//	throws Exception
//	{
//		
//		String runtimeDir = Config.getProperty("runtime_location","runtime");
//		
//
//		IO.mkdir(Paths.get(runtimeDir+"/locator").toFile());
//		IO.mkdir(Paths.get(runtimeDir+"/server").toFile());
//		
//		Shell shell = new Shell();
//		ProcessInfo pi = shell.execute(location+"/gfsh","-e","start locator  --dir=runtime/locator  --name=locator  --port=10334");
//		
//		
//		System.out.println(pi.exitValue);
//		System.out.println(pi.output);
//		System.out.println(pi.error);
//		
//		 pi = shell.execute(location+"/gfsh",
//		"-e","start server --name=server --dir="+runtimeDir+"/server --locators=localhost[10334]");
//		
//		System.out.println(pi.exitValue);
//		System.out.println("OUTPUT:"+pi.output);
//		System.out.println("ERROR:"+pi.error);
//		
//		
//		 pi = shell.execute(location+"/gfsh",
//		 "-e","connect",
//		 "-e","create region --name=Test --type=PARTITION");
//		
//		 
//	}
	
}
