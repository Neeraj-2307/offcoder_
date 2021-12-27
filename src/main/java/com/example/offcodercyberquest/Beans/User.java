package com.example.offcodercyberquest.Beans;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class User implements Serializable {

    //DATA MEMBERS HERE
    public String handle;
    public String avatar;
    public String titlePhoto;
    public String rank;
    public int rating;
    public String password;
    public String email;
    public int friendOfCount;


    private static User user;

    //PRIVATE CONSTRUCTOR
    private User(){}

    public static User getInstance()
    {
        if(user == null)
        {
            user = new User();
        }
        return user;
    }
    public void setInstance(JsonNode result,String user_password,String user_email)
    {
        this.handle = result.get(0).get("handle").asText();
        this.avatar = result.get(0).get("avatar").asText();
        this.titlePhoto = result.get(0).get("titlePhoto").asText();
        this.rank = result.get(0).get("rank").asText();
        this.rating = result.get(0).get("rating").asInt();
        this.friendOfCount = result.get(0).get("friendOfCount").asInt();
        this.password = user_password;
        this.email = user_email;
    }
}
