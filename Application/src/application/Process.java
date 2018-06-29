package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Process extends Thread {
	//------------------------
	public static int RR=200;
	public static int TF=60;
	public final static String inputDirOfProccess= "./input/";
	//------------------------
	public final int PID;
	public final double START;//arrival time
	public double turnaround;
	public double wait;
	public double burst=0;
	public double innerWait=0;

	public final double DURATION;//number of addresses
	public final int SIZE;//table size
	public boolean [] valid;//is page in memory
	public int [] pmt;//page location in memory
	private File f;
	private Scanner s;
	public boolean isDone;//true when proccess is done
	public double finishTime=0;
	//---------------------------------
	public int finished=0;//how many lines done
	public String todo= null;
	//---------------------------------
	//stop and resume running
	public boolean running = true;
	public int hit=0;
	public int miss=0;
	
	public Process(int PID,int start,int duration,int size) throws FileNotFoundException
	{
		this.PID=PID;
		this.START=start;
		this.DURATION=duration;
		this.SIZE=size;
		valid= new boolean[SIZE];
		pmt = new int[SIZE];
		f=new File(inputDirOfProccess+PID+".txt");
		s=new Scanner(f);
		for(int i=0;i<pmt.length;i++)
			pmt[i]=Driver.memory.limit;
	}
		
	public void start()
	{
		try {
			this.Run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Run() throws InterruptedException
	{
		burst+=RR;
		
		int time=RR;
		if(todo!=null)
		{
			
			int location = Integer.parseInt(todo.charAt(0)+""+todo.charAt(1), 16) ;//location in table
				
				Driver.instants.add(new Instant(pmt[location],1,PID+""));
				Driver.clock++;
			for(int i=0;i<TF-1;i++)
			{
				
				Driver.instants.add(new Instant(pmt[location],2,PID+""));
				Driver.clock++;
				

			}
	
			//-------------------------------------
			
			
			Driver.memory.start();
			Driver.memory.join();
			int next=RAM.next;
			
			//int next=Driver.memory.LRU();
			//int next=Driver.memory.FIFO();
		
			//-------------------------------------
			
			valid[location]=true;
			pmt[location]=next;
			finished++;
			Driver.memory.reallocate(next,PID,location);
			todo=null;
			time=RR-TF;
			Driver.instants.add(new Instant(next,3,PID+""));
			Driver.clock++;
			
		}
		
		for (int i=1;i<=time&&DURATION-finished>0;i++)//run while not finished time quantom and still more addresses to read
		{
			
			String virtualAddress = s.next();

			int location = Integer.parseInt(virtualAddress.charAt(0)+""+virtualAddress.charAt(1), 16) ;//location in table
	
			
			if(valid[location]&& Driver.memory.used[pmt[location]])//hit
			{
				
				Driver.memory.updateLRU(pmt[location]);
				Driver.instants.add(new Instant(pmt[location],0,PID+""));
				Driver.clock++;
				finished++;
				hit++;
				
				
				continue;
				
			}
			else{//miss
				innerWait+=TF;
				miss++;
				if(i+TF>RR)
				{
					todo=virtualAddress;
					for(int j=0;j<RR-i;j++)
					{
						
						Driver.instants.add(new Instant(location,2,PID+""));
						Driver.clock++;

					}
					return;
				}
				Driver.instants.add(new Instant(pmt[location],1,PID+""));
				Driver.clock++;
				for(int h=0;h<TF-1;h++)
				{
				
					Driver.instants.add(new Instant(0,2,PID+""));
					Driver.clock++;

				}
				i+=TF;
				//---------------------------------
				
				
				Driver.memory.start();
				Driver.memory.join();
				int next=RAM.next;
				//int next=Driver.memory.LRU();
				//int next=Driver.memory.FIFO();		
				//---------------------------------
				try {
					Driver.memory.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				valid[location]=true;
				pmt[location]=next;
				finished++;
				Driver.memory.reallocate(next,PID,location);
				Driver.instants.add(new Instant(next,3,PID+""));
				Driver.clock++;
			}
			

		}
		if(DURATION-finished==0)
			{
			isDone=true;
			finishTime=Driver.clock;
			turnaround=(int) (finishTime-START);
			burst=innerWait+DURATION;
			wait= turnaround-burst;
			
			
			}
	
			
			running=false;
		
	}

	public double getTurnaround() {
		return turnaround/1000.0;
	}
	
	public double getWait() {
		return wait/1000.0;
	}

	public double getFinishTime() {
		return finishTime/1000.0;
	}

	public int getPID() {
		return PID;
	}

	public double getSTART() {
		return START/1000.0;
	}

	public double getDURATION() {
		return DURATION/1000.0;
	}
	public double getBurst(){
		return burst/1000.0;
	}
	
	public double getInnerWait(){
		return innerWait/1000.0;
	}
	public int getHit()
	{
		return hit;
	}
	public int getMiss(){
		return miss;
	}
}
