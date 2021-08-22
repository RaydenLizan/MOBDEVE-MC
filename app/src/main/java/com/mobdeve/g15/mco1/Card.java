package com.mobdeve.g15.mco1;

public class Card {

    private String type;
    private int image;

    public Card(String type, int image){
        this.type = type;
        this.image = image;
    }

    public String getType(){
        return this.type;
    }

    public int getImage(){
        return this.image;
    }
}
