package com.example.offcodercyberquest.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.example.offcodercyberquest.Scrapper.ProblemScrapper;
import com.example.offcodercyberquest.Beans.Suggestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import org.json.JSONArray;
import org.json.JSONObject;

public class SuggestionController implements Initializable{
    @FXML
    private CheckBox dp,bf,graph,implement;
    @FXML
    private Slider rating;
    @FXML
    private Button suggest;
    @FXML
    private ListView<Suggestion> suggestionList;
    List<String> tag;
    String url = "https://codeforces.com/api/problemset.problems?";
    public void graphchange() {
        url+=("tags=graphs");
        System.out.println(url);
    }

    public  void findSuggestion() throws Exception {
        suggestionList.getItems().clear();
        System.out.print("abc");
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
       //System.out.println(response);
       JSONObject obj1 = new JSONObject(response.toString());
       JSONObject problems=obj1.getJSONObject("result");
       JSONArray arr=problems.getJSONArray("problems");
        for (int i = 0; i < Math.min(arr.length(),10); i++)
        {
            Suggestion s=new Suggestion();
            s.setContestId(arr.getJSONObject(i).getInt("contestId"));
            s.setName(arr.getJSONObject(i).getString("name"));
            if(arr.getJSONObject(i).has("rating"))
                s.setRating(arr.getJSONObject(i).getInt("rating"));
            suggestionList.getItems().add(s);
        }
       ProblemScrapper sc=new ProblemScrapper();
       sc.myScrapper("https://codeforces.com/contest/1586/problem/A");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        rating.setMin(0);
        rating.setMax(2500);
        rating.adjustValue(1200);
    }

}
