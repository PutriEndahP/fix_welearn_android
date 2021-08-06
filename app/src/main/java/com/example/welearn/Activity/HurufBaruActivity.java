package com.example.welearn.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.welearn.R;
import com.example.welearn.Response.Api.ResponseApi;
import com.example.welearn.Response.Api.ResponsePredict;
import com.example.welearn.Response.Api.ResponseSoal;
import com.example.welearn.Response.Api.ResponseType.ListSoalHuruf;
import com.example.welearn.Retrofit.ApiClientWelearn;
import com.example.welearn.Retrofit.ServerWelearn;
import com.example.welearn.Retrofit.TokenManager;
import com.williamww.silkysignature.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import darren.googlecloudtts.BuildConfig;
import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HurufBaruActivity extends AppCompatActivity {

    ImageView btn_back, btn_sound, reset, send;
    TextView text_level, text_soalke, text_soalnya;
    CardView card_soal, mBtnReset, mBtnSend;
    SignaturePad mHurufPad;
    TokenManager tokenManager;
    ApiClientWelearn api;

    private static final String TAG = "HurufBaruActivity";
    public MainViewModel mMainViewModel;

    int id, id_soal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huruf_baru);

        Bundle extras = getIntent().getExtras();
        ArrayList<ListSoalHuruf> arraylist = extras.getParcelableArrayList("mylist");
//        ArrayList<ListSoalHuruf> arraylist = extras.

        id = extras.getInt("id");

        btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HurufBaruActivity.this, Level0SoalActivity.class);
                startActivity(i);
            }
        });

        GoogleCloudTTS googleCloudTTS = GoogleCloudTTSFactory.create(BuildConfig.API_KEY);
        mMainViewModel = new MainViewModel(getApplication(), googleCloudTTS);

        btn_sound = (ImageView)findViewById(R.id.btn_sound);
        btn_sound.setOnClickListener(e ->{
            mMainViewModel.speak(arraylist.get(id).getKeterangan())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(t -> initTTSVoice())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            makeToast("Speak success", false);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            makeToast("Speak failed " + e.getMessage(), true);
                            Log.e(TAG, "Speak failed", e);
                        }
                    });
        });


        reset = (ImageView)findViewById(R.id.reset);
        send = (ImageView)findViewById(R.id.send);
        text_level = (TextView)findViewById(R.id.judul_level);
        text_soalke = (TextView)findViewById(R.id.soal);
        text_soalnya = (TextView)findViewById(R.id.soalnya);
        text_soalnya.setText(arraylist.get(id).getSoal());
        id_soal = arraylist.get(id).getIdSoal();

        text_soalke.setText("Soal ke : "+String.valueOf(id+1));


        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs",
                Context.MODE_PRIVATE));
                ApiClientWelearn api = ServerWelearn.createServiceWithAuth(ApiClientWelearn.class, tokenManager);
//        api = ServerWelearn.createService(ApiClientWelearn.class);
//        Call<ResponseSoal<ArrayList<ListSoalHuruf>>> soal = api.getSoalHuruf(arraylist.get(id).getSoal(), "Bearer " + tokenManager.getToken());

//        soal.enqueue(new Callback<ResponseSoal<ArrayList<ListSoalHuruf>>>() {
//            @Override
//            public void onResponse(Call<ResponseSoal<ArrayList<ListSoalHuruf>>> call, Response<ResponseSoal<ArrayList<ListSoalHuruf>>> response) {
//                if (response.code() == 200) {
//                    Log.e("response get soal", response.body().getMessage().toString());
//
//                } else {
//                    Log.e("gagal get soal", response.raw().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseSoal<ArrayList<ListSoalHuruf>>> call, Throwable t) {
//                Log.e("Gagal", t.toString());
//            }
//        });



        card_soal = (CardView)findViewById(R.id.card_soal);
        mBtnReset = (CardView)findViewById(R.id.button_reset);
        mBtnSend = (CardView)findViewById(R.id.button_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HurufBaruActivity.this, HurufLv1Activity.class);
                startActivity(i);
            }
        });
        mHurufPad = (SignaturePad)findViewById(R.id.huruf_pad);

        mHurufPad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                mBtnSend.setEnabled(true);
                mBtnReset.setEnabled(true);
            }

            @Override
            public void onClear() {
                mBtnSend.setEnabled(false);
                mBtnReset.setEnabled(false);
            }
        });

        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHurufPad.clear();
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getApplicationContext().getCacheDir(), "huruf");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Bitmap hurufBitmap = mHurufPad.getSignatureBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                hurufBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bitmap_data = byteArrayOutputStream.toByteArray();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmap_data);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String value = Base64.encodeToString(bitmap_data, Base64.DEFAULT);
                ArrayList<String> valueList = new ArrayList<>();
                valueList.add(value);

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

//                Call<ResponsePredict> upload = api.predict(valueList,"11", "Bearer " + tokenManager.getToken());
                Call<ResponsePredict> upload = api.predict(valueList,String.valueOf(id_soal), "Bearer " + tokenManager.getToken());
//                Log.e("response", tokenManager.getToken());
//                Log.e("response", valueList.toArray().toString());

                final SweetAlertDialog pDialog = new SweetAlertDialog(HurufBaruActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                upload.enqueue(new Callback<ResponsePredict>() {
                    @Override
                    public void onResponse(Call<ResponsePredict> call, Response<ResponsePredict> response) {
                        if (response.code() == 200 && response.body().getMessage().equals("Benar")) {
                            Log.e("response", response.body().getMessage());
                            pDialog.dismiss();
                            new SweetAlertDialog(HurufBaruActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                                    .setTitleText(response.body().getMessage())
                                    .setTitleText("Jawabanmu : Benar")
//                                    .setContentText("Jawaban Berhasil Disimpan")
                                    .setContentText(response.body().getText())
                                    .setCustomImage(R.drawable.happy)
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();

                                            mHurufPad.clear();
                                            Intent intent = new Intent(HurufBaruActivity.this, Level0SoalActivity.class);
                //                          intent.putExtra("id", id);
                //                          intent.putExtra("id", String.valueOf(id));
                                            intent.putExtra("id", String.valueOf(id_soal));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else if (response.code() == 200 && response.body().getMessage().equals("Salah")) {
                            Log.e("response", response.body().getMessage());
                            pDialog.dismiss();
                            new SweetAlertDialog(HurufBaruActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                                    .setTitleText(response.body().getMessage())
                                    .setTitleText("Jawabanmu : Salah")
//                                    .setContentText("Jawaban Berhasil Disimpan")
                                    .setContentText(response.body().getText())
                                    .setCustomImage(R.drawable.sad)
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();

                                            mHurufPad.clear();
                                            Intent intent = new Intent(HurufBaruActivity.this, Level0SoalActivity.class);
                                            //                          intent.putExtra("id", id);
                                            //                          intent.putExtra("id", String.valueOf(id));
                                            intent.putExtra("id", String.valueOf(id_soal));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else {
                            pDialog.dismiss();
                            Log.e("testes", response.raw().toString());
                            new SweetAlertDialog(HurufBaruActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Terjadi kesalahan, mohon ulangi lagi.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePredict> call, Throwable t) {
                        Log.e("response", t.toString());
                        pDialog.dismiss();
                        new SweetAlertDialog(HurufBaruActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Hasil")
                                .setContentText("Internet Anda Bermasalah")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                    }
                });
            }
        });
    }

    private void makeToast(String text, boolean longShow) {
        Toast.makeText(this, text, (longShow) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    private void initTTSVoice() {
        String languageCode = "id-ID";
        String voiceName = "id-ID-Wavenet-A";
        float pitch = ((float) (10) / 100);
        float speakRate = ((float) (100) / 100);

        mMainViewModel.initTTSVoice(languageCode, voiceName, pitch, speakRate);
    }
}


