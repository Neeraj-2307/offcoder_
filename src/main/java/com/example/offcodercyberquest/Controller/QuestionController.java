package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Beans.User;
import com.example.offcodercyberquest.HelloApplication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private Button HistoryList,favList,showList,all,edit;
    @FXML
    private Label QuestionDetail;
    @FXML
    private ListView<String> questionList;
    @FXML
    void load_dashboard(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("dashboard.fxml");
    }
    List<String> list1= new ArrayList<String>();
    List<String> fav= new ArrayList<>();
    List<Integer> his=new ArrayList<Integer>();
    List<Integer> dp=new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setMainList();
            setFavList();
            showAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFavList() throws IOException {
        String dirName= User.getInstance().handle;
        String url=".\\"+dirName+"\\favLists.txt";
        System.out.println(dirName);
        File f=new File(url);
        BufferedReader br= null;
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String st="Error try again after some time";

        while ((st = br.readLine()) != null) {
            fav.add(st);
        }
    }

    private void setMainList() throws IOException {
        String dirName= User.getInstance().handle;
        String url=".\\neeraj_2307\\questions.txt";
        System.out.println(url);
        File f=new File(url);
        BufferedReader br= null;
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String st="Error try again after some time";

        while ((st = br.readLine()) != null) {
            list1.add(st);
        }

    }

    public void showSelection() throws IOException {
        edit.setDisable(false);
        System.out.println(questionList.getSelectionModel().getSelectedItem());
        String url=generateURL(questionList.getSelectionModel().getSelectedItem());
        questionList.getItems().clear();
        showList.setVisible(false);
        File f=new File(url);
        BufferedReader br= null;
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String st="Error try again after some time";

        while ((st = br.readLine()) != null) {
            questionList.getItems().add(st);
        }
    }

    private String generateURL(String filename) {
        return ".\\neeraj_2307\\"+filename+".txt";
    }

    public void showFavList(){
        edit.setDisable(true);
        questionList.getItems().clear();
        showList.setVisible(true);

        for(String s:fav){
            questionList.getItems().add(s);
        }

    }
    public void showAll(){
        edit.setDisable(false);
        questionList.getItems().clear();
        showList.setVisible(false);
        for(String s:list1){
            questionList.getItems().add(s);
        }
    }
    public void gotoEditor(ActionEvent event) throws IOException {
        String filename=questionList.getSelectionModel().getSelectedItem();
        if(filename == null)
            return;
        String[] str=filename.split("_");
        HelloApplication m=new HelloApplication();
        System.out.println("at ques"+str[0]+str[1]);
        m.changeToEditor("editor-view.fxml",str[0],str[1],event);
    }
    public void gotoCreate(ActionEvent actionEvent) throws IOException {
        HelloApplication m=new HelloApplication();
        m.changeScene("createList.fxml");
    }
}
