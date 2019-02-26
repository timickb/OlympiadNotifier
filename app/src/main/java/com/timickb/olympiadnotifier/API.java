package com.timickb.olympiadnotifier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("/get")
    public Call<List<Olympiad>> getOlympiads(@Query("class") String class_, @Query("subject") String subject, @Query("date") String date);
}
