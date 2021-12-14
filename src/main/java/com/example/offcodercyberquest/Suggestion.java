package com.example.offcodercyberquest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Suggestion {
    @JsonProperty("contestId")
    public int getContestId() {
        return this.contestId; }
    public void setContestId(int contestId) {
        this.contestId = contestId; }
    int contestId;
    @JsonProperty("index")
    public String getIndex() {
        return this.index; }
    public void setIndex(String index) {
        this.index = index; }
    String index;
    @JsonProperty("name")
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    String name;
    @JsonProperty("type")
    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    String type;
    @JsonProperty("rating")
    public int getRating() {
        return this.rating; }
    public void setRating(int rating) {
        this.rating = rating; }
    int rating;
    @JsonProperty("tags")
    public List<String> getTags() {
        return this.tags; }
    public void setTags(List<String> tags) {
        this.tags = tags; }
    List<String> tags;
    public String toString(){
        return "Name: "+this.getName()+"  Rating: "+(this.getRating()==0?"undefined":this.getRating());
    }
}
