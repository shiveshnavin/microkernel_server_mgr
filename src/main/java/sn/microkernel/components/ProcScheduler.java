package sn.microkernel.components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import sn.microkernel.ServerRepo;
import sn.microkernel.component.model.ProcessModel;
import sn.microkernel.component.model.ServerModel; 


public class ProcScheduler {

	@Autowired
	ServerRepo serverRepo;
	ArrayList<ServerModel> servers=new ArrayList<>();
	
	public ProcScheduler()
	{
		servers=new ArrayList<ServerModel>(serverRepo.findAll());
	}
	public ProcScheduler(ArrayList<ServerModel> srvs)
	{
		servers=srvs;
	}
	private int getRandomPort()
	{
		int Min = 2999;
		int Max = 9999;
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	public long loadServer(String name,String cmd)
	{
		int port=getRandomPort();
		for(int i=0;i<servers.size();i++)
		{
			ServerModel s=servers.get(i);
			if(s.port==port)
			{
				i=-1;
				port=getRandomPort();
			}
			
		}

//		Gson gs=new Gson();
//		RunConfig config=gs.fromJson(configJstr, RunConfig.class);
		
		ServerModel serv=new ServerModel(name,cmd,port);
//		serv.dir=config.dir;
		ProcessModel proc=new ProcessModel(serv);
		try {
			proc.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static void main(String[] args) throws Exception {
		ProcScheduler p=new ProcScheduler(new ArrayList<>());
//		FileInputStream fs=new FileInputStream("C:\\Users\\I516430\\Documents\\workspace-sts-3.9.9.RELEASE\\microkernel\\run.config");
//		Scanner sc=new Scanner(fs);
//		String g="";
//		while(sc.hasNextLine())
//		{
//			g+=sc.nextLine();
//		}
//		sc.close();
//		System.out.println(g);
		p.loadServer("test","C:\\Users\\I516430\\Desktop\\SAP\\onboarding\\ui\\run.bat");
		System.out.println("Running...");
	}
	
	public static class RunConfig{
		String cmd;String dir;
	}
}
