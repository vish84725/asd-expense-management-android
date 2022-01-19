package com.cube365.asdexpensemanagement.repositories;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.network.RetrofitClient;
import com.cube365.asdexpensemanagement.services.ICategoryService;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.ITransactionService;
import com.cube365.asdexpensemanagement.services.TokenService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoriesRepository {
    private static TransactionsRepository instance;
    Retrofit retrofit = null;
    private ICategoryService categoryService;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    ITokenService mTokenService;

    public CategoriesRepository(Activity activity) {
        try{
            this.retrofit = RetrofitClient.getClient();
            categoryService = this.retrofit.create(ICategoryService.class);
            mTokenService = new TokenService(activity);
        }catch (Exception ex){

        }
    }

    private void onFinishCall() {
        isLoading.postValue(false);
    }

    private void onStartCall() {
        isLoading.postValue(true);
    }

    public LiveData<APIResponse<GetCategoryResponse>> getAllCategories(Integer userId) {
        onStartCall();
        final MutableLiveData<APIResponse<GetCategoryResponse>> categories = new MutableLiveData<>();
        Call<List<GetCategoryResponse>> call = categoryService.getAllCategories(mTokenService.getAccessToken(),userId);
        call.enqueue(new Callback<List<GetCategoryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetCategoryResponse>> call, @NonNull Response<List<GetCategoryResponse>> response) {
                if (response.isSuccessful()) {
                    categories.postValue(new APIResponse<GetCategoryResponse>(response.body()));
                }else{
                    categories.postValue(new APIResponse<GetCategoryResponse>(new Exception(response.message())));
                }
                onFinishCall();
            }

            @Override
            public void onFailure(@NonNull Call<List<GetCategoryResponse>> call, @NonNull Throwable t) {
                categories.postValue(new APIResponse<GetCategoryResponse>(t));
                onFinishCall();
            }
        });

        return categories;
    }

    public LiveData<APIObjectResponse<CommonResponse>> saveCategory(CreateCategoryPostRequest request) {
        onStartCall();
        final MutableLiveData<APIObjectResponse<CommonResponse>> resp = new MutableLiveData<>();
        Call<CommonResponse> call = categoryService.saveCategory(mTokenService.getAccessToken(),request);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    resp.postValue(new APIObjectResponse<CommonResponse>(response.body()));
                }else{
                    resp.postValue(new APIObjectResponse<CommonResponse>(new Exception(response.message())));
                }
                onFinishCall();
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                resp.postValue(new APIObjectResponse<CommonResponse>(t));
                onFinishCall();
            }
        });

        return resp;
    }

    public LiveData<Boolean> getLoadingStatusLiveData() {
        return isLoading;
    }
}
