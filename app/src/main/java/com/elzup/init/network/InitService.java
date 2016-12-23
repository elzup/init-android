package com.elzup.init.network;

import com.elzup.init.models.CompleteEntity;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface InitService {
    @FormUrlEncoded
    @POST("/v1/login")
    Observable<SessionEntity> login(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/v1/users")
    Call<SessionEntity> createUser(
            @Field("username") String username,
            @Field("password") String password);

    @GET("/v1/users/{id}")
    Observable<SessionEntity> getUser(
            @Path("id") String id);

    @GET("/v1/missions")
    Observable<List<MissionEntity>> getMissions();

    @GET("/v1/missions/{id}")
    Observable<MissionEntity> getMission(@Path("id") int id);

    @FormUrlEncoded
    @POST("/v1/missions")
    Observable<MissionEntity> postMission(
            @Field("title") String title,
            @Field("description") String description,
            @Field("category_id") int categoryId);

    @POST("/v1/missions/{id}/complete")
    Observable<CompleteEntity> postMissionComplete(@Path("id") int id);

    @POST("/v1/missions/{id}/uncomplete")
    Observable<CompleteEntity> postMissionUncomplete(@Path("id") int id);

    @DELETE("/v1/missions/{id}")
    Observable<MissionEntity> deleteMission(@Path("id") int id);

    @FormUrlEncoded
    @PUT("/v1/missions/{id}")
    Observable<MissionEntity> updateMission(
            @Path("id") int id,
            @Part("title") String title,
            @Part("description") String description,
            @Part("category_id") int categoryId);
}
