package sn.microkernel.components;

import java.util.ArrayList;

import sn.microkernel.component.model.ServerModel;

public interface Mircokernel {
	
	public String loadServer(String name,String cmd,String env,String dir);
	public String unloadServer(String port);
	public ArrayList<ServerModel> getServers();
	public String startServer(String name);
	public String stopServer(String name);
	public String callServer(String src,String serverName , String params);
	
}
