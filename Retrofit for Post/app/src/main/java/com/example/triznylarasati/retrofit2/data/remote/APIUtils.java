package com.example.triznylarasati.retrofit2.data.remote;

public class APIUtils {
    private APIUtils() {}
    public static final String BASE_URL = "https://demo7970623.mockable.io/";
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
