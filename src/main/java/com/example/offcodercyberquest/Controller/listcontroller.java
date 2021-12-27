package com.example.offcodercyberquest.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.queue.Task;
import com.example.offcodercyberquest.queue.TaskPriorities;
import com.example.offcodercyberquest.queue.TaskQueue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class listcontroller implements Initializable {
	
	@FXML
	private ListView<String> subP,DownP;
	@FXML
	private Button deleteButton;
	@FXML
	private Label DetailsLabel;
	List<Task> p=new ArrayList<>();

	public void deleteFromList() {
		

		
	}
	@FXML
	void load_dashboard(ActionEvent event) throws IOException {
		HelloApplication m = new HelloApplication();
		m.changeScene("dashboard.fxml");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setQueue();
        subP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

	

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {
				// TODO Auto-generated method stub
				String label="In submission";
				DetailsLabel.setText(label);
				deleteButton.setDisable(true);
			}
        	
        });
        DownP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1,
					String arg2) {


				DetailsLabel.setText("Will Be Notified");
				deleteButton.setDisable(false);
			}
        	
        });
        
	}

	private void setQueue() {
		p= TaskQueue.getInstance().getTaskList();
		for(Task task : p){
			if(task.getPriority() == TaskPriorities.SUBMIT_TASK)
				subP.getItems().add("Contest: "+ task.getContestId() + "Problemid: "+task.getProblemId());
			else
				DownP.getItems().add("Contest: "+ task.getContestId() + "Problemid: "+task.getProblemId());
		}
	}

}
