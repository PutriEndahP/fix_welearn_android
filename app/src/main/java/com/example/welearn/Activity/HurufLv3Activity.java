package com.example.welearn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import com.example.welearn.R;
import com.example.welearn.Response.Api.ResponsePredict;
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
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HurufLv3Activity extends AppCompatActivity {

    ImageView btn_back, btn_sound, reset, send;
    TextView title, soal, soalnya;
    SignaturePad mHurufPad, mHurufPad2, mHurufPad3, mHurufPad4, mHurufPad5, mHurufPad6, mHurufPad7, mHurufPad8, mHurufPad9;
    CardView card_soal, mBtnReset, mBtnSend;
    int id, id_soal;
    TokenManager tokenManager;
    ApiClientWelearn api;

    private static final String TAG = "HurufLv3Activity";
    public MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huruf_lv3);
//        id = getIntent().getIntExtra("id",0);
        Bundle extras = getIntent().getExtras();
        ArrayList<ListSoalHuruf> arraylist = extras.getParcelableArrayList("mylist");

        id = extras.getInt("id");

        btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HurufLv3Activity.this, Level0SoalActivity.class);
                startActivity(i);
            }
        });

        GoogleCloudTTS googleCloudTTS = GoogleCloudTTSFactory.create(BuildConfig.API_KEY);
        mMainViewModel = new MainViewModel(getApplication(), googleCloudTTS);

        btn_sound = (ImageView)findViewById(R.id.btn_sound);
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
        title = (TextView)findViewById(R.id.judul_level);

        soal = (TextView)findViewById(R.id.soal);
        soal.setText("Soal ke : "+String.valueOf(id+1));

        soalnya = (TextView)findViewById(R.id.soalnya);
        soalnya.setText(arraylist.get(id).getSoal());
        id_soal = arraylist.get(id).getIdSoal();

        card_soal = (CardView)findViewById(R.id.card_soal);
        mBtnReset = (CardView)findViewById(R.id.button_reset);
        mBtnSend = (CardView)findViewById(R.id.button_send);

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
        mHurufPad2 = (SignaturePad)findViewById(R.id.huruf_pad2);
        mHurufPad2.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad3 = (SignaturePad)findViewById(R.id.huruf_pad3);
        mHurufPad3.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad4 = (SignaturePad)findViewById(R.id.huruf_pad4);
        mHurufPad4.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad5 = (SignaturePad)findViewById(R.id.huruf_pad5);
        mHurufPad5.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad6 = (SignaturePad)findViewById(R.id.huruf_pad6);
        mHurufPad6.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad7 = (SignaturePad)findViewById(R.id.huruf_pad7);
        mHurufPad7.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad8 = (SignaturePad)findViewById(R.id.huruf_pad8);
        mHurufPad8.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
        mHurufPad9 = (SignaturePad)findViewById(R.id.huruf_pad9);
        mHurufPad9.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
                mHurufPad2.clear();
                mHurufPad3.clear();
                mHurufPad4.clear();
                mHurufPad5.clear();
                mHurufPad6.clear();
                mHurufPad7.clear();
                mHurufPad8.clear();
                mHurufPad9.clear();
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
                Bitmap hurufBitmap2 = mHurufPad2.getSignatureBitmap();
                Bitmap hurufBitmap3 = mHurufPad3.getSignatureBitmap();
                Bitmap hurufBitmap4 = mHurufPad4.getSignatureBitmap();
                Bitmap hurufBitmap5 = mHurufPad5.getSignatureBitmap();
                Bitmap hurufBitmap6 = mHurufPad6.getSignatureBitmap();
                Bitmap hurufBitmap7 = mHurufPad7.getSignatureBitmap();
                Bitmap hurufBitmap8 = mHurufPad8.getSignatureBitmap();
                Bitmap hurufBitmap9 = mHurufPad9.getSignatureBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream6 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream7 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream8 = new ByteArrayOutputStream();
                ByteArrayOutputStream byteArrayOutputStream9 = new ByteArrayOutputStream();

                hurufBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bitmap_data = byteArrayOutputStream.toByteArray();
                hurufBitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
                byte[] bitmap_data2 = byteArrayOutputStream2.toByteArray();
                hurufBitmap3.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream3);
                byte[] bitmap_data3 = byteArrayOutputStream3.toByteArray();
                hurufBitmap4.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream4);
                byte[] bitmap_data4 = byteArrayOutputStream4.toByteArray();
                hurufBitmap5.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream5);
                byte[] bitmap_data5 = byteArrayOutputStream5.toByteArray();
                hurufBitmap6.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream6);
                byte[] bitmap_data6 = byteArrayOutputStream6.toByteArray();
                hurufBitmap7.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream7);
                byte[] bitmap_data7 = byteArrayOutputStream7.toByteArray();
                hurufBitmap8.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream8);
                byte[] bitmap_data8 = byteArrayOutputStream8.toByteArray();
                hurufBitmap9.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream9);
                byte[] bitmap_data9 = byteArrayOutputStream9.toByteArray();

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
                String value = Base64.encodeToString(bitmap_data, Base64.DEFAULT); //image to base64
                String value2 = Base64.encodeToString(bitmap_data2, Base64.DEFAULT); //image to base64
                String value3 = Base64.encodeToString(bitmap_data3, Base64.DEFAULT); //image to base64
                String value4 = Base64.encodeToString(bitmap_data4, Base64.DEFAULT); //image to base64
                String value5 = Base64.encodeToString(bitmap_data5, Base64.DEFAULT); //image to base64
                String value6 = Base64.encodeToString(bitmap_data6, Base64.DEFAULT); //image to base64
                String value7 = Base64.encodeToString(bitmap_data7, Base64.DEFAULT); //image to base64
                String value8 = Base64.encodeToString(bitmap_data8, Base64.DEFAULT); //image to base64
                String value9 = Base64.encodeToString(bitmap_data9, Base64.DEFAULT); //image to base64
                ArrayList<String> valueList = new ArrayList<>();
                valueList.add(value);
                valueList.add(value2);
                valueList.add(value3);
                valueList.add(value4);
                valueList.add(value5);
                valueList.add(value6);
                valueList.add(value7);
                valueList.add(value8);
                valueList.add(value9);

                Log.e("listfoto", String.valueOf(valueList.size()));

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

                tokenManager = TokenManager.getInstance(getSharedPreferences("prefs",
                        Context.MODE_PRIVATE));
                ApiClientWelearn api = ServerWelearn.createService(ApiClientWelearn.class);
//                Call<ResponsePredict> upload = api.predict(valueList,"9", "Bearer " + tokenManager.getToken());
                Call<ResponsePredict> upload = api.predict(valueList,String.valueOf(id_soal), "Bearer " + tokenManager.getToken());

                final SweetAlertDialog pDialog = new SweetAlertDialog(HurufLv3Activity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                            new SweetAlertDialog(HurufLv3Activity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                                            mHurufPad2.clear();
                                            mHurufPad3.clear();
                                            mHurufPad4.clear();
                                            mHurufPad5.clear();
                                            mHurufPad6.clear();
                                            mHurufPad7.clear();
                                            mHurufPad8.clear();
                                            mHurufPad9.clear();
                                            Intent intent = new Intent(HurufLv3Activity.this, Level0SoalActivity.class);
//                                          intent.putExtra("id", id);
//                                          intent.putExtra("id", String.valueOf(id));
                                            intent.putExtra("id", String.valueOf(id_soal));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else if(response.code() == 200 && response.body().getMessage().equals("Salah")) {
                            Log.e("response", response.body().getMessage());
                            pDialog.dismiss();
                            new SweetAlertDialog(HurufLv3Activity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
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
                                            mHurufPad2.clear();
                                            mHurufPad3.clear();
                                            mHurufPad4.clear();
                                            mHurufPad5.clear();
                                            mHurufPad6.clear();
                                            mHurufPad7.clear();
                                            mHurufPad8.clear();
                                            mHurufPad9.clear();
                                            Intent intent = new Intent(HurufLv3Activity.this, Level0SoalActivity.class);
//                                          intent.putExtra("id", id);
//                                          intent.putExtra("id", String.valueOf(id));
                                            intent.putExtra("id", String.valueOf(id_soal));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else {
                            pDialog.dismiss();
                            Log.e("testes", response.raw().toString());
                            new SweetAlertDialog(HurufLv3Activity.this, SweetAlertDialog.WARNING_TYPE)
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
                        new SweetAlertDialog(HurufLv3Activity.this, SweetAlertDialog.ERROR_TYPE)
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