package com.example.offcodercyberquest;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Contest{

    public String getStatus() {
        return this.status; }
    public void setStatus(String status) {
        this.status = status; }
    String status;

    public List<Result> getResult() {
        return this.result; }
    public void setResult(List<Result> result) {
        this.result = result; }
    List<Result> result;

    public void ObjectOfContest(String s) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Contest root = om.readValue(s, Contest.class);
    }
}