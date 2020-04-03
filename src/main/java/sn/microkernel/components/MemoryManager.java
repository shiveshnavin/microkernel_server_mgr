package sn.microkernel.components;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import sn.microkernel.MKController;
import sn.microkernel.component.model.ProcessModel;

@Service
public class MemoryManager {
	 
	Timer timer;
	TimerTask watchDog;

	public MemoryManager()
	{
		watchDog=new TimerTask() {
			
			@Override
			public void run() {

				if(MKController.kernel!=null)
				{
					ProcScheduler procMgr=MKController.kernel.procMgr;
					if(procMgr!=null)
					{
						if(procMgr.processes.size()>4)
						{
							ProcessModel pd=procMgr.processes.get(0);
							pd.stop();
							procMgr.processes.remove(0);
							System.out.println(">>Memory Out :: Killing 1 Process ");
						}
					}
				}
				
			}
		};
		timer=new Timer();
		timer.schedule(watchDog, 10, 3000);
	}
	
}
