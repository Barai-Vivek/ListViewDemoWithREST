package com.app.listviewdemowithrest.api;

import com.app.listviewdemowithrest.models.MainResponse;

public interface ApiCallResponse {

    public void onSuccess(MainResponse mainResponse);

    public void onError(String err);
}