package com.mobdeve.g15.mco1;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class ViewHolderPlayer1 extends RecyclerView.ViewHolder {

    private ImageView card;

    public ViewHolderPlayer1(@NonNull @NotNull View itemView) {
        super(itemView);

        this.card = itemView.findViewById(R.id.iv_card);

    }


    public void setCard(int cardImg) {
        this.card.setImageResource(cardImg);
    }
}
