package com.mobdeve.g15.mco1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GameAdapterPlayer2 extends RecyclerView.Adapter<ViewHolderPlayer2> {
    private ArrayList<Card> hand;

    public GameAdapterPlayer2(ArrayList<Card> hand)
    {
        this.hand = hand;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderPlayer2 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cards, parent, false);

        ViewHolderPlayer2 holder = new ViewHolderPlayer2(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderPlayer2 holder, int position) {
        holder.setCard(hand.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return this.hand.size();
    }


}
