package com.infobite.stygian.retrofit_provider;

import android.app.Dialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.model.all_product_modal.AllProductMainModal;
import com.infobite.stygian.model.banner_responce.BannerModel;
import com.infobite.stygian.model.category_modal.ProductCategoryList;
import com.infobite.stygian.model.token_responce.TokenModel;
import com.infobite.stygian.util.AppProgressDialog;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static RetrofitApiClient client;
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        client = retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRxClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        return retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRetrofit() {
        if (client == null)
            new RetrofitService();
        return client;
    }

    public static void getResponseBody(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);
        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getCategoryList(final Dialog dialog, final Call<List<ProductCategoryList>> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);
        method.enqueue(new Callback<List<ProductCategoryList>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryList>> call, Response<List<ProductCategoryList>> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<List<ProductCategoryList>> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getAllProduct(final Dialog dialog, final Call<List<AllProductMainModal>> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);
        method.enqueue(new Callback<List<AllProductMainModal>>() {
            @Override
            public void onResponse(Call<List<AllProductMainModal>> call, Response<List<AllProductMainModal>> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<List<AllProductMainModal>> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getBannerData(final Dialog dialog, final Call<BannerModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);
        method.enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }


    public static void getToken(final Dialog dialog, final Call<TokenModel> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);
        method.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }
}