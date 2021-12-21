package com.example.offcodercyberquest;

import com.example.offcodercyberquest.Beans.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAuth {


    public int makeUser(String user_name, String user_password, String user_email) throws IOException, InterruptedException {


        //CHECKING FOR VALID EMAIL OR NOT

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user_email);
        if(!(matcher.matches()))
            return -1;

        //CHECKING FOR CORRECT PASSWORD
        Automation automation = new Automation();
        int r = automation.login(user_name,user_password);
        if(r == -1)
            return -1;
//        System.out.println(automation.getCSRF());
        //API CALL FOR USER INFO

        String url = "https://codeforces.com/api/user.info?handles=";
        url = url+user_name;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        //JSON NODES TO ACCESS DATA
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.body());
        String status =(String)node.get("status").asText();

        //RETURN IF USER DOSEN'T EXIST ON CODEFORCES
        if(status.equals("FAILED"))
            return -1;
        JsonNode result = node.get("result");


//      USER CLASS CALL FOR INITIALIZATION
        User user = User.getInstance();
        user.setInstance(result,user_password,user_email);
        UserStats stat = new UserStats();
        stat.avatar = user.titlePhoto;
        serializeUser();
        return 0;
    }

    //SERIALISING USER
    public void serializeUser() throws IOException {

        User user =User.getInstance();
        String filename = ".\\UserFiles\\"+user.handle+".txt";
        FileOutputStream fout=new FileOutputStream(filename);
        ObjectOutputStream out=new ObjectOutputStream(fout);
        out.writeObject(user);
        out.flush();
        out.close();
        fout.close();
    }

    //DESERIALISING
    public int deserializeUser(String user_name,String password) throws IOException, ClassNotFoundException, InterruptedException {

        //CHECKING IF THE FILE EXISTS OR NOT

        String filename = ".\\UserFiles\\"+user_name+".txt";
        File f = new File(filename);
        if(!f.isFile()) {
            return -1;
        }
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in=new ObjectInputStream(file);
        User user=(User)in.readObject();
        in.close();
        file.close();
        if(password.equals(user.password)) {

            //CALCULATING OTHER CHARTS AT LOGIN TIME TO REDUCE WAITING TIME LATER
            UserStats stats = new UserStats();
            stats.makeChart(user.handle);
            stats.avatar = user.titlePhoto;
            return 1;
        }
        return 0;
    }

}
