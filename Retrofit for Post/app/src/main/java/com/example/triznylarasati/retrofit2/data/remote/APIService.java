package com.example.triznylarasati.retrofit2.data.remote;

import com.example.triznylarasati.retrofit2.data.model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);
/*    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Body Post post);*/
}
