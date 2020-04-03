package sn.microkernel.component.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

public class ProcessModel {

	public int port;
	public ServerModel server;
	ExecuteWatchdog wd;
	Process bash;
	boolean logToFile = false;

	public ProcessModel() {
	};

	public ProcessModel(ServerModel srv) {
		port=srv.port;
		server = srv;
	}

	public String getLogFile() {
		File f = new File("./logs");
		if (!f.exists()) {
			f.mkdir();
		}
		String fil = "./logs/" + server.name + "_log.log";
		f = new File(fil);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fil;
	}

	public long stop() {

		Process wd = bash;
		if (wd != null) {

			wd.destroy();
			System.out.println(">>ProcScheduler:Stop:" + server.name + " on " + server.port);

		} else {

			System.out.println(">>ProcScheduler:Stop:ERR" + server.name + " on " + server.port);
		}

		return port;
	}

	public long start() throws Exception {

		if (bash != null && bash.isAlive()) {
			bash.destroyForcibly();
		}

		if (server.dir.length() > 2) {
			bash = Runtime.getRuntime().exec(server.getStartCmd(), null, new File(server.dir));
		} else {
			bash = Runtime.getRuntime().exec(server.getStartCmd());

		}

		System.out.println(">>ProcScheduler:Start:" + server.name + "(" + bash.pid() + ")" + " on " + server.port
				+ " logged at " + getLogFile()+" isAlive : "+bash.isAlive());

		OutputStream os = new FileOutputStream(getLogFile());
		BufferedReader br = new BufferedReader(new InputStreamReader(bash.getInputStream()));
		String str = null;
		String x;
		while ((x = br.readLine()) != null) {
			os.write(x.getBytes());
		}

		if (true)
			return bash.pid();

		CommandLine commandLine = CommandLine.parse(server.getStartCmd());
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(0);
		OutputStream out = new FileOutputStream(getLogFile());
		executor.setStreamHandler(new PumpStreamHandler(logToFile ? os : System.out));

		wd = new ExecuteWatchdog(1583092399l);
		executor.setWatchdog(wd);
		executor.execute(commandLine);

		return 0l;
	}

}
