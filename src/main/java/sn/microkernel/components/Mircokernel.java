package sn.microkernel.components;

import java.util.ArrayList;

import sn.microkernel.component.model.ServerModel;

public interface Mircokernel {
	
	public long loadServer(String name,String cmd);
	public long unloadServer(long pid);
	public ArrayList<ServerModel> getServers();
	public int startServer(long pid);
	public int stopServer(long pid);
	public String callServer(String serverName , String params);
	
}
