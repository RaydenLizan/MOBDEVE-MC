package com.mobdeve.g15.mco1;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ViewHolderPlayer2 extends RecyclerView.ViewHolder {

    private ImageView card;

    public ViewHolderPlayer2(@NonNull @NotNull View itemView) {
        super(itemView);

        this.card = itemView.findViewById(R.id.iv_card);

    }


    public void setCard(int cardImg) {
        this.card.setImageResource(cardImg);
    }
}
