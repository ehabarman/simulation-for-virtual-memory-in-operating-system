package application.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportController implements Initializable{

	@FXML
	private TextField wait2,turnaround2;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView table;
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {


		table.getColumns().clear();
		
		
		TableColumn<Process, Integer> PID = new TableColumn<>("PID");
    	TableColumn<Process, Double> arrival = new TableColumn<>("Arrival");
		TableColumn<Process, Double> duration =  new TableColumn<>("Duration");
		TableColumn<Process, Double> wait = new TableColumn<>("Wait");
		TableColumn<Process, Double> finish = new TableColumn<>("Finish");
		TableColumn<Process, Double> turnaround = new TableColumn<>("Turnaround");
		TableColumn<Process, Double> burst = new TableColumn<>("Burst");
		TableColumn<Process, Double> memory = new TableColumn<>("memory");
		TableColumn<Process, Integer> hit = new TableColumn<>("Hit");
		TableColumn<Process, Integer> miss = new TableColumn<>("Miss");
		PID.setCellValueFactory(new PropertyValueFactory<Process, Integer>("PID"));
		arrival.setCellValueFactory(new PropertyValueFactory<Process, Double>("START"));
		duration.setCellValueFactory(new PropertyValueFactory<Process, Double>("DURATION"));
		wait.setCellValueFactory(new PropertyValueFactory<Process, Double>("wait"));
		finish.setCellValueFactory(new PropertyValueFactory<Process, Double>("finishTime"));
		turnaround.setCellValueFactory(new PropertyValueFactory<Process, Double>("turnaround"));
		burst.setCellValueFactory(new PropertyValueFactory<Process, Double>("burst"));
		memory.setCellValueFactory(new PropertyValueFactory<Process, Double>("innerWait"));
		hit.setCellValueFactory(new PropertyValueFactory<Process, Integer>("hit"));
		miss.setCellValueFactory(new PropertyValueFactory<Process, Integer>("miss"));
		
		ObservableList<application.Process> list= FXCollections.observableArrayList(Driver.done);
		table.getColumns().addAll(PID,arrival,duration,wait,finish,turnaround,burst,memory,hit,miss);
		table.setItems(list);
		
		
		long TATotal=0;
		long waitTotal=0;
		for(int i=0;i<Driver.done.size();i++)
		{
			TATotal+=Driver.done.get(i).turnaround;
			waitTotal+=Driver.done.get(i).wait;
		}
		wait2.setText(""+((double)waitTotal)/Driver.done.size()/1000);
		turnaround2.setText(""+((double)TATotal)/Driver.done.size()/1000);
		
	}

	
	
}
