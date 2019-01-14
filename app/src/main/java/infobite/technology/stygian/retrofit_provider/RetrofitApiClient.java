package infobite.technology.stygian.retrofit_provider;

import infobite.technology.stygian.constant.Constant;
import infobite.technology.stygian.model.banner_responce.BannerModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiClient {

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> bannerImage();

    @GET(Constant.ALL_PRODUCT)
    Call<ResponseBody> allProductList();
}