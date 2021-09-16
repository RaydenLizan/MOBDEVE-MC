package com.mobdeve.g15.mco1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String userId;

    private TextView tv_score_num;
    private TextView tv_roundsNum;

    private Button btn_6Rounds;
    private Button btn_12Rounds;
    private Button btn_18Rounds;

    private DatabaseReference reference;

    private ImageView character1;
    private ImageView character2;
    private ImageView character3;
    private ImageView preview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        this.tv_score_num = findViewById(R.id.tv_score_num);
        this.tv_roundsNum = findViewById(R.id.tv_roundsNum);
        this.btn_6Rounds = findViewById(R.id.btn_6Rounds);

        this.btn_12Rounds = findViewById(R.id.btn_12Rounds);
        this.btn_18Rounds = findViewById(R.id.btn_18Rounds);

        this.character1 = findViewById(R.id.iv_character1);
        this.character2 = findViewById(R.id.iv_character2);
        this.character3 = findViewById(R.id.iv_character3);
        this.preview = findViewById(R.id.iv_avatarPreview);

        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();

        this.reference = FirebaseDatabase.getInstance("https://egame-55b1c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        reference.child(this.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String highscore = snapshot.child("highscore").getValue().toString();
                tv_score_num.setText(highscore);
                String rounds = snapshot.child("rounds").getValue().toString();
                tv_roundsNum.setText(rounds);

                String image = snapshot.child("image").getValue().toString();
                if(image.equals("kaiji"))
                {
                    preview.setImageResource(R.drawable.kaiji);
                }

                else if(image.equals("kazutaka_hyodo"))
                {
                    preview.setImageResource(R.drawable.kazutaka_hyodo);
                }

                else {
                    preview.setImageResource(R.drawable.kazuya_hyodou);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_6Rounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("rounds").setValue(6);
            }
        });

        btn_12Rounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("rounds").setValue(12);
            }
        });

        btn_18Rounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("rounds").setValue(18);
            }
        });

        character1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("image").setValue("kaiji");
            }
        });

        character2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("image").setValue("kazutaka_hyodo");
            }
        });

        character3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(userId).child("image").setValue("kazuya_hyodou");
            }
        });
    }
}