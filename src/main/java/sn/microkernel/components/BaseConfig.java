package sn.microkernel.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

	@Bean
	public BaseMircokernel getBaseKernel()
	{
		return new BaseMircokernel();
	}

	@Bean
	public InterProcessCom getInterProcessCom()
	{
		return new InterProcessCom();
	}
	
	@Bean
	public MemoryManager getMemoryManager()
	{
		return new MemoryManager();
	}
	
	@Bean
	public ProcScheduler getProcScheduler()
	{
		return new ProcScheduler();
	}

}
