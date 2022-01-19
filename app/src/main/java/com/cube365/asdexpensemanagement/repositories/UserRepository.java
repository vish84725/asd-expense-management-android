package com.cube365.asdexpensemanagement.repositories;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.models.users.CreateUserPostRequest;
import com.cube365.asdexpensemanagement.models.users.LoginUserPostRequest;
import com.cube365.asdexpensemanagement.network.RetrofitClient;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.ITransactionService;
import com.cube365.asdexpensemanagement.services.IUserService;
import com.cube365.asdexpensemanagement.services.TokenService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {
    private static UserRepository instance;
    Retrofit retrofit = null;
    private IUserService userService;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    ITokenService mTokenService;

    public UserRepository(Activity activity) {
        try{
            this.retrofit = RetrofitClient.getClient();
            userService = this.retrofit.create(IUserService.class);
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

    public LiveData<APIObjectResponse<CommonResponse>> loginUser(LoginUserPostRequest request) {
        onStartCall();
        final MutableLiveData<APIObjectResponse<CommonResponse>> resp = new MutableLiveData<>();
        Call<CommonResponse> call = userService.login(request);
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

    public LiveData<APIObjectResponse<CommonResponse>> addUser(CreateUserPostRequest request) {
        onStartCall();
        final MutableLiveData<APIObjectResponse<CommonResponse>> resp = new MutableLiveData<>();
        Call<CommonResponse> call = userService.addUser(request);
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
