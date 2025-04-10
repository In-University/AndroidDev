package com.example.shortvideo_firebase;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface APIService {
    @GET("getvideos.php")
    Call<MessageVideoModel> getVideos();

    // Factory class để tạo instance của APIService
    class Factory {
        private static final String BASE_URL = "http://app.iotstar.vn:8081/appfoods/";
        private static APIService service;

        public static APIService getInstance() {
            if (service == null) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                service = retrofit.create(APIService.class);
            }
            return service;
        }
    }
}
