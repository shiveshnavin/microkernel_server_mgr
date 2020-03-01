package sn.microkernel.component.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.OS2CommandLauncher;

public class ProcessModel {
	
	ServerModel server;
	ExecuteWatchdog wd;
	boolean logToFile = false;
	public ProcessModel() {};
	
	public ProcessModel(ServerModel srv)
	{
		server=srv;
	}
	public String getLogFile()
	{
		File f=new File("./logs");
		if(!f.exists())
		{
			f.mkdir();
		}
		String fil="./logs/"+server.name+"_log.log";
		f=new File(fil);
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fil;
	}
	public void stop()
	{
        wd.destroyProcess();
	}
	public long start() throws  Exception
	{

		System.out.println(">>ProcScheduler:Start:"+server.name+ " on "+server.port+" logged at "+getLogFile());
        CommandLine commandLine = CommandLine.parse(server.getStartCmd());
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
	    OutputStream out=new FileOutputStream(getLogFile());
        executor.setStreamHandler(new PumpStreamHandler(System.out));
        final Process bash = Runtime.getRuntime().exec(server.getStartCmd());
       
        wd=new ExecuteWatchdog(1583092399l);
        executor.setWatchdog(wd);
        executor.execute(commandLine);
        
        return 0l;
	}
	public long start2()
	{
		long pid=0l;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe","/c",server.getStartCmd());
		System.out.println(">>ProcScheduler:Start:"+server.name+ " on "+server.port+" logged at "+getLogFile());
		processBuilder.redirectErrorStream(true);
		try {
		    final Process bash = Runtime.getRuntime().exec(server.getStartCmd());
		    run=bash;
		    pid=run.pid();
		  
		    OutputStream out=new FileOutputStream(getLogFile());
		    BufferedWriter bufw=new BufferedWriter(new OutputStreamWriter(out));
		    
		    Thread commandLineThread = new Thread(new Runnable() {
		        public void run() {
		            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bash.getInputStream()));
		            String line;
		            try {
		                while ((line = bufferedReader.readLine()) != null){
		                   
		                	if(logToFile)
		                	{
			                    bufw.write(line+"\n");
			                    bufw.flush();
		                	}
		                	else
		                	{
		                		System.out.println(line);
		                	}
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    });
		    commandLineThread.setDaemon(true);
		    commandLineThread.start();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return pid;
	}


}
