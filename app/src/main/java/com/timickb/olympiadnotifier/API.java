package com.timickb.olympiadnotifier;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
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
    Call<JSONObject> updateUser(@Body UserUpdatePayload payload);
}
