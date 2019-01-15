package infobite.technology.stygian.retrofit_provider;

import infobite.technology.stygian.constant.Constant;
import infobite.technology.stygian.model.banner_responce.BannerModel;
import infobite.technology.stygian.model.product_responce.ProductModel;
import infobite.technology.stygian.model.wallet_responce.WalletModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApiClient {

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> allCategory();


    @GET(Constant.WALLET_API)
    Call<WalletModel> getWallet(@Part String user_id);

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> bannerImage();

    @GET(Constant.ALL_PRODUCT)
    Call<ResponseBody> allProductList();
}