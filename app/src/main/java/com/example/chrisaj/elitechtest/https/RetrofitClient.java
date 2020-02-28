package com.example.chrisaj.elitechtest.https;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit mRetrofit;
    private static final String BASE_URL = "https://api.github.com/";


    public static UserDataService getService() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit.create(UserDataService.class);
    }
}
