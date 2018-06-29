package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application{
	
	public static ArrayList<Process> queue=new ArrayList<>();
	public static ArrayList<Process> toAdd=new ArrayList<>();
	public static ArrayList<Process> done=new ArrayList<>();
	public static ArrayList<Instant> instants = new ArrayList<>();
	public static int turn=0;
	public static long clock=0;
	public static RAM memory;
	public static final int contextSwitch=1;
	public static Stage stage1;
	public static Stage stage2;
	public static Stage stage3;
	public static int state=2;
	public final static String input ="./input/os.txt";

	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException
	{
		
		launch(args);


	}
	
	public static ArrayList<Process> doInsertionSort(ArrayList<Process> input){
        
        Process temp;
        for (int i = 1; i < input.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(input.get(j).START < input.get(j-1).START){
                    temp = input.get(j);
                    input.set( j,  input.get(j-1) );
                    input.set( j-1,  temp );
                }
            }
        }
        return input;
    }

	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			
			FXMLLoader loader1 = new FXMLLoader(this.getClass().getResource("./View/Start.fxml"));
			Parent root1 = (Parent) loader1.load();	
			Scene scene1 = new Scene(root1);
			stage1= new Stage();
			stage1.sizeToScene();
			stage1.setScene(scene1);
			stage1.setTitle("Start");
			stage1.show();
			
			
			FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("./View/Detailed.fxml"));
			Parent root2 = (Parent) loader2.load();	
			Scene scene2 = new Scene(root2);
			stage2= new Stage();
			stage2.sizeToScene();
			stage2.setScene(scene2);
			stage2.setTitle("Tracing");


			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void function() throws FileNotFoundException
	{
		try {
			File f = new File(input);
			
			Scanner s = new Scanner(f);
			int n = s.nextInt();
			int m = s.nextInt();
			int S = s.nextInt();
			memory= new RAM(m,n);
			int firstArrive=0;
			for(int i=0;i<n;i++)
			{
				
				int pid=s.nextInt();
				
				int start=s.nextInt();
				int duration=s.nextInt();
				int size=s.nextInt();
				firstArrive=Math.min(firstArrive,start);
				toAdd.add(new Process(pid,start,duration,size));
				
			}
			s.close();
			clock=firstArrive;
			toAdd=doInsertionSort(toAdd);
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		
	while(queue.size()!=0||toAdd.size()!=0)
	{
		
		if(queue.size()==0 && toAdd.size()!=0)//if process have not arrive yet
		{
			
			while(clock<toAdd.get(0).START)
			{

				instants.add(new Instant(0,5,""));
				clock++;
			}
		}
		
		
		for (int i=0;i<contextSwitch;i++)//context switch
			{
			instants.add(new Instant(0,4,""));
			clock++;
			}
		
		if(queue.size()!=0)
		try{
			
			queue.get(turn).start();
			queue.get(turn).join();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		for(int i=0;i<toAdd.size();i++)//move from toAdd to queue
		{
			if(toAdd.get(i).START<=clock)
			{

				queue.add(toAdd.get(i));
				toAdd.remove(i);
				i--;					
			}
		}
		
		
		if(queue.get(turn).isDone==true)
		{

			done.add(queue.get(turn));
			for(int i=0;i<memory.used.length;i++)
			{
				if(queue.get(turn).PID==memory.pIndex[i])
					memory.used[i]=false;
			}
			memory.sort();
			queue.remove(turn);
			
		}
		else
			turn++;
		
		
		
		if(turn==queue.size())
			turn=0;
		
		
	}

	}

	
	

}