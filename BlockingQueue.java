package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

@SuppressWarnings("hiding")
public class BlockingQueue<Task> {
	
	// BlockingFIFO Implementation Notes from textbook
	private Task[] queue;
	private int nextIn, nextOut;	
	private int numberOfTasks;
	private int queueCapacity;
	
	Object notFull, notEmpty;		// monitors used for synchronization
	
	@SuppressWarnings("unchecked")
	public BlockingQueue(int c)
	{
		this.queueCapacity = c;
		this.queue = (Task[])new Object[c];
		this.nextIn = 0;
		this.nextOut = 0;
		this.numberOfTasks = 0;		
		this.notFull = new Object();
		this.notEmpty = new Object();
	}
	
	public void put(Task task)
	{
		if(numberOfTasks == queueCapacity)
		{
			synchronized(notFull)
			{
				try
				{
					notFull.wait();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		queue[nextIn] = task;
		nextIn = (nextIn + 1) % queueCapacity;
		numberOfTasks++;
					
		synchronized(notEmpty)
		{
			notEmpty.notify(); 			
		}
		
	}
	
	public Task take()
	{
		Task task = null;
		
		if(numberOfTasks == 0)
		{
			synchronized(notEmpty)
			{
				try
				{
					notEmpty.wait();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		task = queue[nextOut];
		nextOut = (nextOut + 1) % queueCapacity;
		numberOfTasks--;
				
		synchronized(notFull)
		{
			notFull.notify();
		}
		
		return task;
	}
}