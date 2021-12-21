package com.example.offcodercyberquest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserStats {

    //DATA STRUCTURES REQUIRED FOR STATS
    static int ac = 0, wa = 0, tle = 0, ce = 0, re = 0;
    int count = 0;
    static Map<String ,Integer> solved = new TreeMap<String,Integer>();
    static String user_name;
    static ArrayList<String> history = new ArrayList<String>();
    static int currentRating = 0,maxRating = 0, totalContest = 0, ratingIncrease = 0, ratingDecrease = 0;
    static String avatar = "";


    //FUNCTION TO MAKE API CALL FOR MAKING CHART(USER STATUS API)
    public void callChart(String user_handle) throws IOException, InterruptedException {

        user_name = user_handle;
        String url = "https://codeforces.com/api/user.status?handle=";
        url = url + user_handle;

        //API CALL
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        //JSON NODES TO ACCESS DATA
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.body());
        JsonNode arrNode = node.get("result");

        //STORING INSIDE JSON FILE
        String filename = ".\\ApiResponse\\" + user_handle + "1.json";
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), arrNode);
    }

    //FUNCTION TO MAKE API CALL FOR MAKING RATING CHART(USER RATING API)
    public void callLineChart(String user_handle) throws IOException, InterruptedException {

        String url = "https://codeforces.com/api/user.rating?handle=";
        url = url+user_handle;

        //API CALL
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response2 =client.send(request, HttpResponse.BodyHandlers.ofString());

        //JSON NODES TO ACCESS DATA
        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonNode node2 = objectMapper2.readTree(response2.body());
        JsonNode arrNode2 = node2.get("result");

        //STORING INSIDE JSON FILE
        String filename2 = ".\\ApiResponse\\"+user_handle+"2.json";
        objectMapper2.writerWithDefaultPrettyPrinter().writeValue(new File(filename2), arrNode2);

    }
    //FUNCTION TO MAKE CHART
    public void makeChart(String user_handle) throws IOException, InterruptedException {

        //CHECKING IF THE FILE EXISTS OR NOT
        user_name = user_handle;
        String filename = ".\\ApiResponse\\"+user_handle+"1.json";
        File f = new File(filename);
        if(!f.isFile()) {
            callChart(user_handle);
        }

        //JSON NODES TO ACCESS DATA AND READ FROM FILE
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode arrNode = objectMapper.readTree(new File(filename));

        //PIE CHART AND SOLVED CHART LOGIC
        int size = arrNode.size();
        for(int i=0;i<size;i++)
        {
            if(arrNode.get(i).get("verdict").asText().equals("OK"))
                ac++;
            else if(arrNode.get(i).get("verdict").asText().equals("WRONG_ANSWER"))
                wa++;
            else if(arrNode.get(i).get("verdict").asText().equals("TIME_LIMIT_EXCEEDED"))
                tle++;
            else if(arrNode.get(i).get("verdict").asText().equals("COMPILATION_ERROR"))
                ce++;
            else if(arrNode.get(i).get("verdict").asText().equals("RUNTIME_ERROR"))
                re++;
            if(arrNode.get(i).get("verdict").asText().equals("OK")) {
                if(arrNode.get(i).get("problem").has("rating"))
                {
                    String k =(String) arrNode.get(i).get("problem").get("rating").asText();
                    solved.put(k, solved.getOrDefault(k, 0) + 1);
                }
            }

            //MAKING HISTORY OF SOLVED QUESTIONS (RECENT 20 QUESTIONS)
            if(count <=20 && arrNode.get(i).get("verdict").asText().equals("OK"))
            {
                String item = arrNode.get(i).get("problem").get("contestId").asText();
                item = item + arrNode.get(i).get("problem").get("index").asText();
                item = item + "    " + arrNode.get(i).get("problem").get("name").asText();
                if(arrNode.get(i).get("problem").has("rating"))
                {
                    item = item + "    " + arrNode.get(i).get("problem").get("rating").asText();
                }
                history.add(item);
                count++;
            }
        }

    }


    //MAKING PIE CHART FROM THE VALUES CALCULATED PREVIOUSLY
    public ObservableList<PieChart.Data> getPieChart()
    {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Accepted : "+ac , ac),
                new PieChart.Data("Wrong Answer : "+wa, wa),
                new PieChart.Data("Time Limit Exceeded : "+tle, tle),
                new PieChart.Data("Compilation Error : "+ce, ce),
                new PieChart.Data("Runtime Error : "+re, re));
        return pieChartData;
    }

    //MAKING BAR GRAPH FROM THE VALUES CALCULATED PREVIOUSLY
    public XYChart.Series getBarChart()
    {

        XYChart.Series chart = new XYChart.Series<>();
        chart.setName("Problem");
        for (Map.Entry<String,Integer> mapElement : solved.entrySet()) {
            String key = (String) mapElement.getKey();
            // Finding the value
            int value = (int) mapElement.getValue();
            if(key.equals("800") || key.equals("900"))
                chart.getData().add(new XYChart.Data(key,value)); // FIRST ADDING 800 AND 900 PROBLEMS

        }
        for (Map.Entry<String,Integer> mapElement : solved.entrySet()) {
            String key = (String) mapElement.getKey();
            // Finding the value
            int value = (int) mapElement.getValue();
            if(!(key.equals("800") || key.equals("900")))
                chart.getData().add(new XYChart.Data(key,value)); // NOW ADDING REMAINING PROBLEMS

        }
        return chart;
    }

    //MAKING LINE CHART
    public XYChart.Series getLineChart() throws IOException, InterruptedException {

        //CHECKING IF THE FILE EXISTS OR NOT
        String filename = ".\\ApiResponse\\"+user_name+"2.json";
        File f = new File(filename);
        if(!f.isFile()) {
            callLineChart(user_name);
        }

        //JSON NODES TO ACCESS DATA AND READ FROM FILE
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode arrNode = objectMapper.readTree(new File(filename));


        //LINE CHART LOGIC
        XYChart.Series lineChart = new XYChart.Series<>();
        lineChart.setName("Contest");
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy");
        int size = arrNode.size();
        totalContest = size;
        for(int i=0;i<size;i++)
        {
            int temp_final = arrNode.get(i).get("newRating").asInt();
            int temp_prev = arrNode.get(i).get("oldRating").asInt();
            maxRating = Math.max(maxRating,temp_final);
            long time = arrNode.get(i).get("ratingUpdateTimeSeconds").asInt();
            time*=1000;
            Date result = new Date(time);
            String date = (String)simple.format(result);
            lineChart.getData().add(new XYChart.Data(date,temp_final));
            currentRating = temp_final;
            if(temp_final >= temp_prev)
                ratingIncrease = Math.max(ratingIncrease,temp_final-temp_prev);
            else
                ratingDecrease = Math.max(ratingDecrease,temp_prev - temp_final);
        }
        return lineChart;
    }

    public ArrayList<String> getListItem()
    {
        return history;
    }
    public String getContest()
    {
        String s ="" +totalContest;
        return s;
    }
    public String getmaxRating()
    {
        String s ="" +maxRating;
        return s;
    }
    public String getcurrentRating()
    {
        String s ="" +currentRating;
        return s;
    }
    public String getmaxIncrease()
    {
        String s ="" +ratingIncrease;
        return s;
    }
    public String getmaxDecrease()
    {
        String s ="" +ratingDecrease;
        return s;
    }
    public String getUser_name()
    {
        return user_name;
    }
    public String getAvatar()
    {
        return avatar;
    }
}
