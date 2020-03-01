package sn.microkernel.component.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="server")
public class ServerModel {

	@Id
	public Integer id;
	public String name;
	public String cmd;
	public int port;
	public String env;
	public String dir;
	
	public ServerModel() {};
	public ServerModel(String name,String cmd,int port)  
	{
		this.port=(port);
		this.name=name;
		this.cmd=cmd;
	}
	
	public String getStartCmd()
	{
		String portPrefix="";
		if(env==null) env = "spring";
		switch(env.toLowerCase().trim()) {
		
		case "spring":
			portPrefix=" --server.port="+port;
			break;
		case "nodejs":
			portPrefix=" --port "+port;
			break;
		case "flask":
			portPrefix=" -p "+port;
			break;
		case "java":
			portPrefix=" --port="+port;
			break;
		case "ui5":
			portPrefix=" --port "+port;
			break;
		}
		return cmd+portPrefix;
	}
		
}
