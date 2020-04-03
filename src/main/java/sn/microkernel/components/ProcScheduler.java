package sn.microkernel.components;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.microkernel.ServerRepo;
import sn.microkernel.component.model.ProcessModel;
import sn.microkernel.component.model.ServerModel; 

@Service
public class ProcScheduler {

	@Autowired
	ServerRepo serverRepo;
	ArrayList<ServerModel> servers=new ArrayList<>();
	ArrayList<ProcessModel> processes=new ArrayList<>();
	
	public ProcScheduler()
	{
		
	}
	public ProcScheduler(ArrayList<ServerModel> srvs)
	{
		servers=srvs;
	}
	public void destroyAll()
	{
		for(ProcessModel p:processes)
		{
			try {
				
				p.stop();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	private int getRandomPort()
	{
		int Min = 2999;
		int Max = 9999;
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	public String loadServer(String name,String cmd,String env,String dir)
	{

		getServers();
		ServerModel serv=new ServerModel(name,cmd);
//		serv.dir=config.dir;
		
		boolean flag=true;
		int port=getRandomPort();
		for(int i=0;i<servers.size();i++)
		{
			ServerModel s=servers.get(i);
			if(s.port==port)
			{
				i=-1;
				port=getRandomPort();
				if(env.equals("python")) port=9000;
			}
			if(s.name .equals (name))
			{
				System.out.println("Server Already Exists");
				flag=false;
				serv=s;
			}
			
		}
		
		if(flag)
		{
			serv.port=port;
			serv.env=env;
			serv.dir=dir;
			servers.add(serv);
			
		}

		ProcessModel proc=new ProcessModel(serv); 
		 
			processes.add(proc);
			
			Thread th=new Thread(new Runnable() {
				
				@Override
				public void run() {


					try {
						startServer(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			th.start();
		 

		
		
		return "Server started on port "+port;
	}
	
	public String stopServer(String name)
	{
		ProcessModel pd=null;
		for(ProcessModel p:processes)
		{
			if(p.server.name.equals(name))
			{
				pd=p;
				 p.stop();
			}
		}
		if(pd!=null)
		{
			processes.remove(pd);
			return "Stopped"+pd.port;
		}
		return "Not found";
	}
	

	public String startServer(String name)
	{
 
		for(ProcessModel p:processes)
		{
			if(p.server.name.equals(name))
			{	
				try {
					Thread th=new Thread(new Runnable() {
						
						@Override
						public void run() {


							try {
								 p.start();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					th.start();
					return "Started";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "Not found";
	}
	
	
	public static void main(String[] args) throws Exception {
		ProcScheduler procMgr=new ProcScheduler(new ArrayList<>());
//		FileInputStream fs=new FileInputStream("C:\\Users\\I516430\\Documents\\workspace-sts-3.9.9.RELEASE\\microkernel\\run.config");
//		Scanner sc=new Scanner(fs);
//		String g="";
//		while(sc.hasNextLine())
//		{
//			g+=sc.nextLine();
//		}
//		sc.close();
//		System.out.println(g);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				procMgr.loadServer("test","node \"D:\\node\\nodejs_starter\\to_filter_serv.js\"","nodejs","");
				
			}
		}).start();
		System.out.println("Running...");
		Thread.sleep(10000);
		System.out.println("Stopping...");
		procMgr.destroyAll();
	}
	
	public static class RunConfig{
		String cmd;String dir;
	}

	public ArrayList<ServerModel> getServers() {

		//serverRepo.findAll()
		if(servers==null)
			servers=new ArrayList<ServerModel>();

		for(ServerModel s:servers)
		{
			ProcessModel pp=null;
			for(ProcessModel p:processes)
			{
				if(p.server.name.equals(s.name) )
				{
					pp=p;
				}
			}
			if(pp!=null)
			{
				s.setAlive(true);
			}
			else
			{
				s.setAlive(false);
			}
		}
		
		return servers;
	}
}
