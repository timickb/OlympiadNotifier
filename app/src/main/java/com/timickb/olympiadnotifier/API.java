package com.timickb.olympiadnotifier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("/getNext")
    public Call<List<Olympiad>> getNextEvents(@Query("class") String class_, @Query("subject") String subject);

    @GET("/getCurrent")
    public Call<List<Olympiad>> getCurrentEvents(@Query("class") String class_, @Query("subject") String subject);
}
