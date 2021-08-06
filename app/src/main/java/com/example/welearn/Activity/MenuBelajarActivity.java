package com.example.welearn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welearn.R;
import com.example.welearn.Response.Api.ResponseSoal;
import com.example.welearn.Response.Ranking.RankingHuruf;
import com.example.welearn.Retrofit.ApiClientWelearn;
import com.example.welearn.Retrofit.ServerWelearn;
import com.example.welearn.Retrofit.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuBelajarActivity extends AppCompatActivity {

    TextView title;
    CardView btn_huruf, btn_angka, btn_aksara;
    ImageView score, akun, pembatas;
    TokenManager tokenManager;
    ApiClientWelearn api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_belajar);
        title = (TextView)findViewById(R.id.judul);
        btn_huruf = (CardView)findViewById(R.id.btn_huruf);
        btn_huruf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuBelajarActivity.this, LevelHurufActivity.class);
                startActivity(i);
            }
        });
        btn_angka = (CardView)findViewById(R.id.btn_angka);
        btn_angka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MenuBelajarActivity.this, LevelAngkaActivity.class);
                startActivity(k);
            }
        });

        score = (ImageView)findViewById(R.id.score);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));
                api = ServerWelearn.createService(ApiClientWelearn.class);
                Call<ResponseSoal<ArrayList<RankingHuruf>>> rank = api.getRankingHuruf("Bearer " + tokenManager.getToken());

                rank.enqueue(new Callback<ResponseSoal<ArrayList<RankingHuruf>>>() {
                    @Override
                    public void onResponse(Call<ResponseSoal<ArrayList<RankingHuruf>>> call, Response<ResponseSoal<ArrayList<RankingHuruf>>> response) {
                        if (response.code() == 200) {
                            Log.e("response get score", response.body().getMessage().toString());
//
//                            Intent i = new Intent(LevelHurufActivity.this, Level0SoalActivity.class);
//                            i.putExtra("idSoal", );
//                            startActivity(i);

                            Intent i = new Intent(MenuBelajarActivity.this, SkorActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("mylist", response.body().getMessage());
                            i.putExtras(bundle);
                            startActivity(i);

                        } else {
                            Log.e("gagal get score", response.raw().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSoal<ArrayList<RankingHuruf>>> call, Throwable t) {
                        Log.e("Gagal", t.toString());
                    }

                });
//                Intent j = new Intent(MenuBelajarActivity.this, SkorActivity.class);
//                startActivity(j);
            }
        });

//        btn_aksara = (CardView)findViewById(R.id.btn_aksara);
//        btn_aksara.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent j = new Intent(MenuBelajarActivity.this, SoalAksaraActivity.class);
//                startActivity(j);
//            }
//        });

        akun = (ImageView)findViewById(R.id.akun);
        akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MenuBelajarActivity.this, ProfilActivity.class);
                startActivity(a);
            }
        });


    }
}