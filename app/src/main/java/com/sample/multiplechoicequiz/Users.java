package com.sample.multiplechoicequiz;

public class Users {

    private String uName;
    private String pass;
    private int hScore;

    public Users(String uName, int hScore){
        this.uName = uName;
        //default values
        this.hScore = hScore;
        this.pass = "";
    }
    public Users(String uName, String pass){
        this.uName = uName;
        //default values
        this.hScore = 0;
        this.pass = pass;
    }

    public Users(String uName, String pass, int hScore){
        this.uName = uName;
        this.pass = pass;
        this.hScore = hScore;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int gethScore() {
        return hScore;
    }

    public void sethScore(int hScore) {
        this.hScore = hScore;
    }

}
