package sn.microkernel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.microkernel.components.BaseMircokernel;

@RestController
public class MKController {

	@Autowired
	BaseMircokernel kernel;
	
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
	
}
