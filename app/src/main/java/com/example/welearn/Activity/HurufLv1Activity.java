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

public class HurufLv1Activity extends AppCompatActivity {

    ImageView btn_back, btn_sound, reset, send;
    TextView title, soal, soalnya;
    CardView card_soal, btn_reset, btn_send;
    SignaturePad huruf_pad, huruf_pad2, huruf_pad3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huruf_lv1);
        btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_sound = (ImageView)findViewById(R.id.btn_sound);
        reset = (ImageView)findViewById(R.id.reset);
        send = (ImageView)findViewById(R.id.send);
        title = (TextView)findViewById(R.id.judul_level);
        soal = (TextView)findViewById(R.id.soal);
        soalnya = (TextView)findViewById(R.id.soalnya);
        card_soal = (CardView)findViewById(R.id.card_soal);
        btn_reset = (CardView)findViewById(R.id.button_reset);
        btn_send = (CardView)findViewById(R.id.button_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HurufLv1Activity.this, HurufLv2Activity.class);
                startActivity(i);
            }
        });
        huruf_pad = (SignaturePad)findViewById(R.id.padsoalangka);
        huruf_pad2 = (SignaturePad)findViewById(R.id.huruf_pad2);
        huruf_pad3 = (SignaturePad)findViewById(R.id.huruf_pad3);
    }
}