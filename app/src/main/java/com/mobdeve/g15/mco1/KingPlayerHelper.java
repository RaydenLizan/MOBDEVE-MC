package com.mobdeve.g15.mco1;
// your package name

import java.util.ArrayList;
import java.util.Collections;

public class KingPlayerHelper {
    public ArrayList<Card> initializeData() {
        String[] type = {"Emperor", "Slave", "Citizen"};
        int[] image = {R.drawable.emperor, R.drawable.slave, R.drawable.citizen};

        ArrayList<Card> data = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            data.add(new Card(
                    type[2],
                    image[2]));
        }
        data.add(new Card(
                type[0],
                image[0]));

        Collections.shuffle(data);

        return data;
    }
}