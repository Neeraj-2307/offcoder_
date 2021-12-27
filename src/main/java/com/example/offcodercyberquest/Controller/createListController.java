package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Beans.User;
import com.example.offcodercyberquest.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class createListController implements Initializable {
    @FXML
    private ListView<String> question,list;
    @FXML
    private Text result;
    @FXML
    private TextField name;

    public void addnew(ActionEvent actionEvent) throws IOException {

        if(name.getText().length() < 1){
            result.setText("Please enter apt name of list");

        }else{
            String url=".\\"+User.getInstance().handle+"\\"+name.getText()+".txt";
            File f=new File(url);
            if(f.exists()){
                result.setText("Already exists");
                return;
            }
            String fileName=".\\"+ User.getInstance().handle+"\\favLists.txt";
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.newLine();
            // Writing on output stream
            out.write(name.getText());

            // Closing the connection
            out.close();
            f.createNewFile();
            result.setText("Added in list");
            setList();
        }
    }

    public void addquestion(ActionEvent actionEvent) throws IOException {

        String listName=list.getSelectionModel().getSelectedItem();
        String questionName=question.getSelectionModel().getSelectedItem();
        if(listName == null){
            result.setText("No list selected");
            return ;
        }
        if(questionName == null){
            result.setText("No question selected");
            return;
        }
        String fileName=".\\"+ User.getInstance().handle+"\\"+listName+".txt";
        BufferedWriter out = new BufferedWriter(
                new FileWriter(fileName, true));
        out.newLine();
        // Writing on output stream
        out.write(questionName);
        // Closing the connection
        out.close();
        result.setText("Question " + questionName + " added successfully in "+listName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setList();
            setQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setQuestions() throws IOException {
        String dirName= User.getInstance().handle;
        String url=".\\questions\\questions.txt";
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
            question.getItems().add(st);
        }
    }

    private void setList() throws IOException {
        list.getItems().clear();
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
            list.getItems().add(st);
        }
    }

    public void backTo(ActionEvent actionEvent) throws IOException {
        HelloApplication m=new HelloApplication();
        m.changeScene("Question.fxml");
    }
}
