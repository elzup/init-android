package com.elzup.init.network;

import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InitService {
    @FormUrlEncoded
    @POST("/v1/login")
    Call<SessionEntity> login(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/v1/users")
    Call<SessionEntity> createUser(
            @Field("username") String username,
            @Field("password") String password);

    @GET("/v1/users/{id}")
    Call<SessionEntity> getUser(
            @Path("id") String id);

    @GET("/v1/missions")
    Call<List<MissionEntity>> getMissions();

}
