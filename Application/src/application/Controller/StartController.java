package application.Controller;

import application.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

	@FXML
	private Button b,b1;
	
	
	@FXML
	public void startApp(){
		Driver.state=1;
		try
		{
			Driver.function();
		}
		catch(Exception e){
			
		}
		Driver.stage1.close();
		Driver.stage2.show();
	}
	
	@FXML
	public void startApp1()
	{
		Driver.state=2;
		try
		{
			Driver.function();
		}
		catch(Exception e){
			
		}
		
		Driver.stage1.close();
		Driver.stage2.show();
	}
}
