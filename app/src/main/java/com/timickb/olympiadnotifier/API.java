package com.timickb.olympiadnotifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static API mInstance;
    private static final String BASE_URL = "http://94.103.83.68/";
    private Retrofit mRetrofit = null;

    private API() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static API getInstance() {
        if(mInstance == null) {
            mInstance = new API();
        }
        return mInstance;
    }
}
