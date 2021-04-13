package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
//	private int nThreads;							// number of threads in the thread pool
//	private TaskRunner threadPool[];				// the array of the threads (or pool)
	private BlockingQueue<Task> blockingFIFO;		// blocking FIFO queue	
		
	public TaskExecutorImpl(int n)
	{
//		this.nThreads = n;
//		this.threadPool = new TaskRunner[this.nThreads];
		this.blockingFIFO = new BlockingQueue<Task>(100);	
		TaskRunner currentTask = new TaskRunner(this.blockingFIFO);	
		
		for(int i = 0; i < n; i++)
		{
		
			Thread currentThread = new Thread(currentTask);						
			currentThread.start();														
//			threadPool[i] = currentTask;										
		}
	}
	
	@Override
	public void addTask(Task task)
	{
		this.blockingFIFO.put(task);
	}

}