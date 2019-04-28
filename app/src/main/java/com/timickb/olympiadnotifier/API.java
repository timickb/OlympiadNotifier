package com.timickb.olympiadnotifier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @GET("/getNext")
    Call<List<Olympiad>> getNextEvents(@Query("class") String class_, @Query("subject") String subject, @Query("stage") String stage);

    @GET("/getCurrent")
    Call<List<Olympiad>> getCurrentEvents(@Query("class") String class_, @Query("subject") String subject, @Query("stage") String stage);

    @POST("/updateUser")
    Call<Integer> updateUser(@Body String key, @Body String userToken, @Body String subject, @Body boolean flag);
}
