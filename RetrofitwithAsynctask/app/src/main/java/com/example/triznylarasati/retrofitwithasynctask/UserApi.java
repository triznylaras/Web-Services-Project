package com.example.triznylarasati.retrofitwithasynctask;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import static android.R.attr.id;

//this is http method for access API server
public interface UserApi {
    @GET("59ed67e23300004e00b5c596")
    Call<Users> getUsers();

    @GET("59ed67e23300004e00b5c596")
    Call<User> getUser(@Path("id") String user_id);

    @GET("59ed67e23300004e00b5c596")
    Call<ResponseBody> getResultAsJSON();

    @PUT("59ed67e23300004e00b5c596")
    Call<User> updateUser(@Path("id") int user_id, @Body User user);

    @POST("59ed67e23300004e00b5c596")
    Call<User> saveUser(@Body User user);

    @DELETE("59ed67e23300004e00b5c596")
    Call<User> deleteUser(@Path("id") String user_id);
}
