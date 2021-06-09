package com.example.welearn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welearn.R;
import com.williamww.silkysignature.views.SignaturePad;

public class AngkaLv13Activity extends AppCompatActivity {

    ImageView back, speaker, resetangka, submit;
    TextView levelangka, soalangka, soalnya, samadengan, kurang;
    CardView card_soalangka, card_reset, card_submit, card_soalangka1, card_soalangka2;
    SignaturePad  padjawabangka1;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angka_lv13);
        id = getIntent().getIntExtra("id",0);
        back = (ImageView)findViewById(R.id.back);
        speaker = (ImageView)findViewById(R.id.speaker);
        resetangka = (ImageView)findViewById(R.id.resetangka);
        submit = (ImageView)findViewById(R.id.submit);
        levelangka = (TextView)findViewById(R.id.levelangka);
        soalangka = (TextView)findViewById(R.id.soalangka);
        soalnya = (TextView)findViewById(R.id.soalnya);
        samadengan = (TextView)findViewById(R.id.samadengan);
        kurang = (TextView)findViewById(R.id.kurang);
        card_soalangka = (CardView)findViewById(R.id.card_soalangka);
        card_reset = (CardView)findViewById(R.id.card_reset);
        card_submit = (CardView)findViewById(R.id.card_submit);
        card_soalangka1 = (CardView)findViewById(R.id.card_soalangka1);
        card_soalangka2 = (CardView)findViewById(R.id.card_soalangka2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AngkaLv13Activity.this, AngkaLv4aActivity.class);
                startActivity(i);
            }
        });
        padjawabangka1 = (SignaturePad)findViewById(R.id.padjawabangka1);

        padjawabangka1.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                card_submit.setEnabled(true);
                card_reset.setEnabled(true);
            }

            @Override
            public void onClear() {
                card_submit.setEnabled(false);
                card_reset.setEnabled(false);
            }
        });

        card_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                padjawabangka1.clear();
            }
        });
    }
}