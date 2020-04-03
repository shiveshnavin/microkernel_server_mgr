package sn.microkernel.components;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import sn.microkernel.component.model.ServerModel;

public class BaseMircokernel implements Mircokernel{
	
	@Autowired
	public InterProcessCom commMgr=new InterProcessCom();
	@Autowired
	public MemoryManager memMgr=new MemoryManager();
	@Autowired
	public ProcScheduler procMgr=new ProcScheduler();
	@Override
	public String loadServer(String name, String cmd,String env,String dir) {
		return procMgr.loadServer(name, cmd, env,dir);
		 
	}
	@Override
	public String unloadServer(String name) {

		return ""+procMgr.stopServer(name);
	}
	@Override
	public ArrayList<ServerModel> getServers() {
		
		return procMgr.getServers();
	}
	@Override
	public String startServer(String name) {
		return procMgr.startServer(name);
	}
	@Override
	public String stopServer(String name) {
		return ""+procMgr.stopServer(name);
 	}
	@Override
	public String callServer(String src,String serverName, String params) {
		return commMgr.call(src,serverName,params);
	}
	
	
	 
	
	
	 
		
	 

}
