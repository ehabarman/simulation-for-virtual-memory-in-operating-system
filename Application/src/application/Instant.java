package application;


public class Instant {

	
	public int mSize=Driver.memory.pIndex.length;
	public int[] p = new int[mSize];
	public int[] page = new int[mSize];
	public boolean[] used = new boolean[mSize];
	public int index;//miss or hit block
	public final int limit;
	public int state;//what current state (read(green-0),miss(red-1),wait(blue-2),replaced(orange-3),context switch-4,-waiting process-5)
	public String runnningProcess="";
	public int hit;
	public int miss;


	public Instant(int index, int state, String runnningProcess) {
		this.limit=Driver.memory.limit;
		for (int i=limit;i<mSize;i++)
		{
			p[i]=Driver.memory.pIndex[i];
			page[i]=Driver.memory.frames[i];
		    used[i]=Driver.memory.used[i];
			this.index=index;
			this.state=state;
			this.runnningProcess=runnningProcess;
		}
		hit=0;
		miss=0;
		for(int i=0;i<Driver.queue.size();i++)
		{
			hit+=Driver.queue.get(i).hit;
			miss+=Driver.queue.get(i).miss;
		}
		for(int i=0;i<Driver.done.size();i++)
		{
			hit+=Driver.done.get(i).hit;
			miss+=Driver.done.get(i).miss;
		}
	}

	
	
}
