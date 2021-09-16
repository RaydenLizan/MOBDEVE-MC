package com.mobdeve.g15.mco1;

public class User {

    private String email;
    private String password;
    private int highscore;
    private int rounds;
    private String image;


    public User(String e, String p)
    {
        email = e;
        password = p;
        highscore = 0;
        rounds = 12;
        image = "kaiji";
    }

    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }

    public int getHighscore(){
        return this.highscore;
    }
    public int getRounds(){
        return this.rounds;
    }

    public String getImage(){
        return this.image;
    }





}
