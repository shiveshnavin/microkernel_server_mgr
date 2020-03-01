package sn.microkernel.components;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import sn.microkernel.component.model.ServerModel;

public class BaseMircokernel implements Mircokernel{
	
	@Autowired
	public InterProcessCom commMgr;
	@Autowired
	public MemoryManager memMgr;
	@Autowired
	public ProcScheduler procMgr;
	@Override
	public long loadServer(String name, String cmd) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long unloadServer(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<ServerModel> getServers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int startServer(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int stopServer(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String callServer(String serverName, String params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 
	
	
	 
		
	 

}
