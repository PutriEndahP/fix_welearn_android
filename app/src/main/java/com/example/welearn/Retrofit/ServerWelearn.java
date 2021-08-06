package com.example.welearn.Retrofit;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerWelearn {
    public final static String BASE_URL = "https://1d011951e434.ngrok.io/api/v1/"; // API laptop server
//    private final static String API_BASE_URL = BASE_URL+"api/v1/";

    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection","close");
                        request = builder.build();

                        return chain.proceed(request);
                    }
                });
        return builder.connectTimeout(60, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(60, TimeUnit.MINUTES) // write timeout
                .readTimeout(60, TimeUnit.MINUTES).build();
//        return builder.build();

    }

    private static Retrofit buildRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }

    public static <T> T createServiceWithAuth(Class <T> service, final TokenManager tokenManager){
        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if(tokenManager.getToken() !=null){
                    builder.addHeader("Authorization","Bearer " + tokenManager.getToken());
                }

                request=builder.build();
                return chain.proceed(request);
            }
        }).authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(@Nullable Route route, Response response) throws IOException {
                String token = tokenManager.getToken();

                ApiClientWelearn service = ServerWelearn.createService(ApiClientWelearn.class);
                Call<AccessToken> call = service.refresh(token);
                retrofit2.Response<AccessToken> res = call.execute();

                if(res.isSuccessful()){
                    AccessToken newToken = res.body();
                    tokenManager.saveToken(res.body());

                    return response.request().newBuilder().header("Authorization","Bearer " + token).build();
                }
                else {
                    return null;
                }
            }
        }).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return newRetrofit.create(service);
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
