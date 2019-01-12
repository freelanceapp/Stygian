package infobite.technology.stygian.retrofit;

import infobite.technology.stygian.retrofit.response.PostOrder;
import infobite.technology.stygian.util.WebApi;
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
