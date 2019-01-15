package infobite.technology.stygian.retrofit_provider;

import java.util.List;

import infobite.technology.stygian.constant.Constant;
import infobite.technology.stygian.model.all_product_modal.AllProductMainModal;
import infobite.technology.stygian.model.banner_responce.BannerModel;
import infobite.technology.stygian.model.wallet_responce.WalletModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface RetrofitApiClient {

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> allCategory();

    @GET(Constant.WALLET_API)
    Call<WalletModel> getWallet(@Part String user_id);

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> bannerImage();

    @GET(Constant.ALL_PRODUCT)
    Call<List<AllProductMainModal>> allProductList();
}