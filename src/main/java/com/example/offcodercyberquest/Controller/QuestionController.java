package com.example.offcodercyberquest.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    @FXML
    private Button HistoryList,favList,showList,all;
    @FXML
    private Label QuestionDetail;
    @FXML
    private ListView<String> questionList;
    List<String> list1= new ArrayList<String>();
    List<String> fav= new ArrayList<>();
    List<Integer> his=new ArrayList<Integer>();
    List<Integer> dp=new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fav.add("myFavDp");
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        dp.add(1);
        dp.add(2);
        his.add(1);
        his.add(2);
        for(String s:list1){
            questionList.getItems().add(s);
        }

    }
    public void showList(){
        questionList.getItems().clear();
        showList.setDisable(false);
        for(int i=0;i<dp.size();i++){
            questionList.getItems().add(list1.get(dp.get(i)));
        }

    }
    public void showHistory(){
        questionList.getItems().clear();
        for(int i=0;i<his.size();i++){
            questionList.getItems().add(list1.get(his.get(i)));
        }

    }
    public void showFavList(){
        questionList.getItems().clear();
        showList.setVisible(true);

        for(String s:fav){
            questionList.getItems().add(s);
        }

    }
    public void showAll(){
        questionList.getItems().clear();
        for(String s:list1){
            questionList.getItems().add(s);
        }
    }
}
