package com.example.offcodercyberquest;

public class Result{

    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    int id;

    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    String name;

    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    String type;

    public String getPhase() {
        return this.phase; }
    public void setPhase(String phase) {
        this.phase = phase; }
    String phase;

    public boolean getFrozen() {
        return this.frozen; }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen; }
    boolean frozen;

    public int getDurationSeconds() {
        return this.durationSeconds; }
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds; }
    int durationSeconds;

    public int getStartTimeSeconds() {
        return this.startTimeSeconds; }
    public void setStartTimeSeconds(int startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds; }
    int startTimeSeconds;

    public int getRelativeTimeSeconds() {
        return this.relativeTimeSeconds; }
    public void setRelativeTimeSeconds(int relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds; }
    int relativeTimeSeconds;
    public String toString(){
        return getName();
    }
}
