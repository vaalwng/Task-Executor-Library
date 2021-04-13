package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskRunner implements Runnable {
	
	private BlockingQueue<Task> blockingFIFO;
	
	public TaskRunner(BlockingQueue<Task> q)
	{
		this.blockingFIFO = q;
	}
	
	@Override
	public void run() 
	{
		Task task;
		
		while(true)
		{
			task = (Task)blockingFIFO.take();
			try
			{
				task.execute();
			}
			catch(Throwable t)
			{
				
			}
		}
	}
	
}
