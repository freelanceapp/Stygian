package com.infobite.stygian.retrofit;

import com.infobite.stygian.retrofit.response.PostOrder;
import com.infobite.stygian.util.WebApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApInterface {

    String URL_BASE = "https://stygianstore.com/";

    @Headers("Content-Type: application/json")
    @POST(WebApi.API_SUBMIT_ORDER)
    Call<PostOrder> postOrderData(@Body String body);
}
