package sn.microkernel;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import sn.microkernel.components.ProcScheduler;

@SpringBootTest
class MicrokernelApplicationTests {

	@Test
	public void main()
	{
		ProcScheduler p=new ProcScheduler(new ArrayList<>());
		p.loadServer("test", "echo Yolo Homs!!","ui5","");
	}

}
