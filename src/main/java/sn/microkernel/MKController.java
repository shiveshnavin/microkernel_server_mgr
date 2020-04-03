package sn.microkernel;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sn.microkernel.component.model.ServerModel;
import sn.microkernel.components.BaseMircokernel;

@RestController
public class MKController {
 
	public static BaseMircokernel kernel;
	
	public MKController() {

		if(kernel==null)
			kernel=new BaseMircokernel();
	}
	
	@RequestMapping("/")
	public String home()
	{
		
		if(kernel.commMgr==null)
		{
			 return "NULL ins";
		}else {
			
		}
		return "Home";
	}
	
	
	@RequestMapping("/load")
	public String loadServer(@RequestParam("name") String name,@RequestParam("cmd")  String cmd,@RequestParam("env") String env,@RequestParam("dir") String dir)
	{
		return kernel.loadServer(name, cmd, env,dir);
	}
	
	@RequestMapping("/unload")
	public String unloadServer(@RequestParam("name") String name) {

		return ""+kernel.stopServer(name);
	}

	@RequestMapping("/getall")
	public ArrayList<ServerModel> getServers() {
		
		return kernel.getServers();
	}

	@RequestMapping("/start")
	public String startServer(@RequestParam("name") String name) {
		System.out.println(name);
		return kernel.startServer(name);
	}

	@RequestMapping("/stop")
	public String stopServer(@RequestParam("name") String name) {
		return ""+kernel.stopServer(name);
 	}

	@RequestMapping("/call")
	public String callServer(@RequestParam("src") String src,@RequestParam("server") String serverName, @RequestParam("params") String params) {
		return kernel.callServer(src, serverName, params) ;
	}
	
	
	
	
}
