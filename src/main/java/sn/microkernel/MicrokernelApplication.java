package sn.microkernel;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("sn.microkernel")
public class MicrokernelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrokernelApplication.class, args);
	}


	  @PreDestroy
	  public void onExit() {
		  System.out.println("###STOPing###");
	    try {

	    	MKController.kernel.procMgr.destroyAll();
	    	
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());;
	    }
	    System.out.println("###STOP FROM THE LIFECYCLE###");
	  }
	  
}
