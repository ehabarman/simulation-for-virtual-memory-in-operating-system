package application;

import java.util.ArrayList;

public class RAM extends Thread{
	
	public int[] frames;
	public int[] pIndex;//which process using frame
	public boolean[] used;//not used-yellow ,,,, used-white
	public int[] queue;//which next
	public static int next;
	public final int limit;
	public ArrayList<Integer>  use = new ArrayList<>();
	public ArrayList<Integer>  reUse = new ArrayList<>();
	public RAM(int size,int n)
	{
	
		limit=n;
		frames= new int[size];
		pIndex= new int[size];
		used= new boolean[size];
		queue= new int[size];
		for(int i=0;i<size;i++)
			queue[i]=i;
		
		for(int i=limit;i<size;i++)
			use.add(i);
	}
	
	public void start()
	{
		run();
	}
	public void run()
	{
		switch(Driver.state)
		{
		case 1: FIFO();
			break;
		case 2: LRU();
			break;
		case 3: sort();
		break;

		}
	}
	
	
	public int FIFO()//get next replacement place using FIFO
	{
		int temp;
		if(use.size()!=0)
		{
		  temp = use.get(0);
		  used[temp]=true;
		  use.remove(0);
		  reUse.add(temp+0);
		  
		}
		else
		{
			
			temp=reUse.get(0);
			int temp2=temp;
			reUse.remove(0);
			reUse.add(temp2);
		}
		next=temp;
		return temp;
	}
	
	public int LRU()//get next replacement place using LRU
	{
		int temp=queue[limit];
		for(int i=limit;i<queue.length-1;i++)
			{
			queue[i]=queue[i+1];
			}
		
		queue[queue.length-1]=temp;
		used[queue[queue.length-1]]=true;
		
		next=queue[queue.length-1];
		return queue[queue.length-1];
		
	}
	
	public void sort()
	{
		for(int i=limit+1;i<=queue.length-1;i++)
		{
			for (int j = i;j>limit;j--)
			{
				if(!used[queue[j]]&&used[queue[j-1]])
				{
					int temp = queue[j-1];
					queue[j-1]=queue[j];
					queue[j]=temp;
				}
			}
		}
		
		for(int i=0;i<reUse.size();i++)
		{
			if(!used[reUse.get(i)])
			{
				int temp = reUse.get(i);
				reUse.remove(i);
				use.add(temp);
				i--;
			}
		}
	}
	
	public void updateLRU(int index)//update queue after use
	{
		for(int i=0;i<queue.length;i++)
			if(queue[i]==index)
			{
				index=i;
				break;
			}
		int temp=queue[index];
		
		for(int i=index;i<queue.length-1;i++)
			queue[i]=queue[i+1];
		
		queue[queue.length-1]=temp;

	}
	
	public void reallocate(int index,int pid,int frame)//reallocate mememory for the given process id
	{
		int oldPID=pIndex[index];
		for(int i=0;i<Driver.queue.size();i++)
		{
			if(oldPID==Driver.queue.get(i).PID)
			{
				for(int j=0;j<Driver.queue.get(i).pmt.length;j++)
					if(Driver.queue.get(i).pmt[j]==index)
					{
						
						Driver.queue.get(i).valid[j]=false;
					}
				break;
				
			}
		}
		
		frames[index]=frame;
		pIndex[index]=pid;
	}
	
}
