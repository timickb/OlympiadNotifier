package com.timickb.olympiadnotifier;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("/get")
    public Call<Olympiad> getOlympiad();
}
