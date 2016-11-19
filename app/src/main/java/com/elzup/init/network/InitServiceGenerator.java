package com.elzup.init.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitServiceGenerator {
    public static final String TAG = "InitServiceGenerator";
    public static final String API_BASE_URL = "https://init-api.elzup.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static InitService createService() {
        return createService(null);
    }

    public static InitService createService(final String authToken) {
        if (authToken != null) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken)
                        .method(original.method(), original.body());

                return chain.proceed(requestBuilder.build());
            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(InitService.class);
    }
}
