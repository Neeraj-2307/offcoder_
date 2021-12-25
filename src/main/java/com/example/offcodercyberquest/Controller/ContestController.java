package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Beans.Contest;
import com.example.offcodercyberquest.Beans.Result;
import com.example.offcodercyberquest.HelloApplication;
import com.example.offcodercyberquest.Scrapper.ContestScrapper;
import com.example.offcodercyberquest.queue.DownloadTask;
import com.example.offcodercyberquest.queue.TaskQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javafx.scene.control.Tab;
import org.json.*;

/*
* This contestController Class is Downloading Contest from codeforces site
*
* */
public class ContestController implements Initializable {
    @FXML
    private Button dashboard;
    @FXML
    private ListView<Result> ContestList;
    @FXML
    private Button Fetch;
    Contest contest=new Contest();
    /*
    * Fetching the list from codeforces site
    * */
    public  void fetchList() throws Exception{
        ContestList.getItems().clear();
        String url = "https://codeforces.com/api/contest.list?gym=false";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject obj1 = new JSONObject(response.toString());
        JSONArray arr = obj1.getJSONArray("result");
        for (int i = 0; i < arr.length(); i++)
        {
            String ver = arr.getJSONObject(i).getString("phase");
            if(ver.equals("FINISHED") ){
                Result r=new Result();
                r.setId(arr.getJSONObject(i).getInt("id"));
                r.setName(arr.getJSONObject(i).getString("name"));
                r.setType(arr.getJSONObject(i).getString("type"));
                ContestList.getItems().add(r);
            }
        }
        System.out.println("task done");

    }
    /*
    * Adding task to taskqueue and check for internet to arrive .
    *
    * */
    @FXML
    private void downloadContest() throws IOException {
        String contestid= String.valueOf(ContestList.getSelectionModel().getSelectedItem().getId());
        DownloadTask dt=new DownloadTask(contestid);
        TaskQueue.getInstance().addTask(dt);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /*
    * get back to dashboard
    * */
    @FXML
    void load_dashboard(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("dashboard.fxml");
    }

}
