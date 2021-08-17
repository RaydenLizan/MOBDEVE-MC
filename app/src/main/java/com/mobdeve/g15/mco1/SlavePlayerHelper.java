package com.mobdeve.g15.mco1;

import java.util.ArrayList;
import java.util.Collections;

public class SlavePlayerHelper {
    public ArrayList<Card> initializeData() {
        String[] type = {"King", "Slave", "Citizen"};
        int[] image = {R.drawable.emperor, R.drawable.slave, R.drawable.citizen};

        ArrayList<Card> data = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            data.add(new Card(
                    type[2],
                    image[2]));
        }
        data.add(new Card(
                type[1],
                image[1]));

        Collections.shuffle(data);

        return data;
    }
}
