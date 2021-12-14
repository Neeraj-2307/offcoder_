package com.example.offcodercyberquest.Controller;

import java.net.URL;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


class InternetObject{
static int count=0;
 int Process;
 int requestno;
 int id;
 String desc;
 public InternetObject() {
	 count++;
	 requestno=count;
 }
 void setProcess(int k) {
	 Process=k;
 }
 void setItem(int id,String desc) {
	 this.id=id;
	 this.desc=desc;
 }
 public String toString() {
	 return this.desc;
 }
}
class The_Comparator implements Comparator<InternetObject>{

	@Override
	public int compare(InternetObject o1, InternetObject o2) {
		if(o1.id == o2.id)
			return 0;
		if(o1.id < o2.id)
			return 1;
		return -1;
	}
	
}
public class listcontroller implements Initializable {
	
	@FXML
	private ListView<InternetObject> subP,DownP;
	@FXML
	private Button deleteButton;
	@FXML
	private Label DetailsLabel;
	PriorityQueue<InternetObject> p=new PriorityQueue<InternetObject>(new The_Comparator());
	private InternetObject deleteI;
	public void deleteFromList() {
		
		p.remove(deleteI);
		System.out.println("Hello" + deleteI + p.size());
		DownP.getItems().remove(deleteI);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		InternetObject i=new InternetObject();
		i.setItem(1,"abc");
		p.add(i);
		InternetObject b=new InternetObject();
		b.setItem(2, "b");
		p.add(b);
		InternetObject b1=new InternetObject();
		b1.setItem(1, "abccc");
		p.add(b1);
		InternetObject b2=new InternetObject();
		b2.setItem(2, "bb");
		p.add(b2);
		Iterator<InternetObject> iterator = p.iterator();
		 int counter=0;
        while (iterator.hasNext()) {
    	i=iterator.next();
    	i.setProcess(++counter);
//        	(i.desc + " | requestNo: "+ i.requestno + " | currentProcessSequence: " + (++counter) + " | Verdict : InSubmission")
            if(i.id == 2) 
            	subP.getItems().add(i); 
            else
            	DownP.getItems().add(i);
        }
        subP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InternetObject>() {

	

			@Override
			public void changed(ObservableValue<? extends InternetObject> arg0, InternetObject arg1,
					InternetObject arg2) {
				// TODO Auto-generated method stub
				InternetObject temp=subP.getSelectionModel().getSelectedItem();
				DetailsLabel.setText((temp.desc + " | requestNo: "+ temp.requestno + " | currentProcessSequence: " + temp.Process + " | Verdict : InSubmission"));
				deleteButton.setDisable(true);
			}
        	
        });
        DownP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InternetObject>() {

			

			@Override
			public void changed(ObservableValue<? extends InternetObject> arg0, InternetObject arg1,
					InternetObject arg2) {
				// TODO Auto-generated method stub
				
				InternetObject temp=DownP.getSelectionModel().getSelectedItem();
				deleteI= temp;
				DetailsLabel.setText((temp.desc + " | requestNo: "+ temp.requestno + " | currentProcessSequence: " + temp.Process + " | Verdict : InDownloading"));
				deleteButton.setDisable(false);
			}
        	
        });
        
	}

}
