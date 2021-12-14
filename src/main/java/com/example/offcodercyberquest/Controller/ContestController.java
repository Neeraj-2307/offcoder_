package com.example.offcodercyberquest.Controller;

import com.example.offcodercyberquest.Contest;
import com.example.offcodercyberquest.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.net.*;
import org.json.*;
public class ContestController implements Initializable {
    @FXML
    private ListView<Result> ContestList;
    @FXML
    private Button Fetch;
    Contest contest=new Contest();
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
            if(!ver.equals("FINISHED") ){
                Result r=new Result();
                r.setId(arr.getJSONObject(i).getInt("id"));
                r.setName(arr.getJSONObject(i).getString("name"));
                r.setType(arr.getJSONObject(i).getString("type"));
                ContestList.getItems().add(r);
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
