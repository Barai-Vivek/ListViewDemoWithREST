package com.app.listviewdemowithrest.api;

import android.app.Activity;
import android.content.Context;

import com.app.listviewdemowithrest.models.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllApiCalls {

    private static AllApiCalls apiCalls;
    private Context context;
    private Call<MainResponse> mainResponseCall;
    private APIInterfaceClass apiInterfaceClass;

    public AllApiCalls(Context context) {
        this.context = context;
    }

    //singleton class for api so that we cancel all api
    public static AllApiCalls singleInstance(Context context) {
        if (apiCalls == null) {
            apiCalls = new AllApiCalls(context);
        }
        return apiCalls;
    }

    //make all api calls from here
    public void mainResonseApiCall(final Activity activity, final String path, final ApiCallResponse apiCallData) {
        if (apiInterfaceClass == null) {
            apiInterfaceClass = ApiClient.getClient().create(APIInterfaceClass.class);
        }
        mainResponseCall = apiInterfaceClass.getResponseFromAPI(path);
        mainResponseCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body() != null) {
                    apiCallData.onSuccess(response.body());
                } else {
                    apiCallData.onError(" ");
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                System.out.println("Something with api error - " + t.getMessage());
                apiCallData.onError(t.getMessage() + " ");
            }
        });
    }


    //cancel api call
    public void cancelMainResponseCall() {
        if (mainResponseCall != null)
            mainResponseCall.cancel();
    }

}
