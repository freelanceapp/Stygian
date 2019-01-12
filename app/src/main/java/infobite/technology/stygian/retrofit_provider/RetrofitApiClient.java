package infobite.technology.stygian.retrofit_provider;

import infobite.technology.stygian.constant.Constant;
import infobite.technology.stygian.model.banner_responce.BannerModel;
import infobite.technology.stygian.model.product_responce.ProductModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApiClient {

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> allCategory();





}