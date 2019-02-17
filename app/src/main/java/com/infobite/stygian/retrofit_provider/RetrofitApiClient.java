package com.infobite.stygian.retrofit_provider;

import java.util.List;

import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.model.all_product_modal.AllProductMainModal;
import com.infobite.stygian.model.banner_responce.BannerModel;
import com.infobite.stygian.model.category_modal.ProductCategoryList;
import com.infobite.stygian.model.token_responce.TokenModel;
import com.infobite.stygian.model.wallet_responce.WalletModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitApiClient {

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> allCategory();

    @GET(Constant.WALLET_API)
    Call<WalletModel> getWallet(@Part String user_id);

    @GET(Constant.ALL_BANNER)
    Call<BannerModel> bannerImage();

    @GET(Constant.ALL_PRODUCT)
    Call<List<AllProductMainModal>> allProductList();


    @FormUrlEncoded
    @POST(Constant.TOKEN_URL)
    Call<TokenModel> gettoken(@Field("user_ip") String user_ip,
                              @Field("token") String token);

    @GET(Constant.CATEGORY_LIS)
    Call<List<ProductCategoryList>> categoryList(@Query("parent") String parent);
}