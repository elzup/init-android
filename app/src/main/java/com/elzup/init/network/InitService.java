package com.elzup.init.network;

import com.elzup.init.models.SessionEntity;

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
            @Field("email") String email,
            @Field("password") String password);

    @POST("/v1/users")
    Call<SessionEntity> createUser(
            @Field("email") String email,
            @Field("password") String password);

    @GET("/v1/users/{id}")
    Call<SessionEntity> getUser(
            @Path("id") String id);
}
