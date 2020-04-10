package com.app.listviewdemowithrest.api;

import com.app.listviewdemowithrest.models.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterfaceClass {

    @GET("{path}")
    Call<MainResponse> getResponseFromAPI(@Path(value = "path", encoded = true) String path);
}
