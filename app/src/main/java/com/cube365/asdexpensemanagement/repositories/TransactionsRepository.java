package com.cube365.asdexpensemanagement.repositories;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.GetBudgetResponse;
import com.cube365.asdexpensemanagement.models.transactions.PostBudgetRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.network.RetrofitClient;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.ITransactionService;
import com.cube365.asdexpensemanagement.services.TokenService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransactionsRepository {
    private static TransactionsRepository instance;
    Retrofit retrofit = null;
    private ITransactionService transactionService;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    ITokenService mTokenService;

    public TransactionsRepository(Activity activity) {
        try{
            this.retrofit = RetrofitClient.getClient();
            transactionService = this.retrofit.create(ITransactionService.class);
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

    public LiveData<APIResponse<TransactionResponse>> getAllTransactions(Integer userId, String transactionType) {
        onStartCall();
        final MutableLiveData<APIResponse<TransactionResponse>> transactions = new MutableLiveData<>();
        Call<List<TransactionResponse>> call = transactionService.getAllTransactions(mTokenService.getAccessToken(),userId,transactionType);
        call.enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<TransactionResponse>> call, @NonNull Response<List<TransactionResponse>> response) {
                if (response.isSuccessful()) {
                    transactions.postValue(new APIResponse<TransactionResponse>(response.body()));
                }else{
                    transactions.postValue(new APIResponse<TransactionResponse>(new Exception(response.message())));
                }
                onFinishCall();
            }

            @Override
            public void onFailure(@NonNull Call<List<TransactionResponse>> call, @NonNull Throwable t) {
                transactions.postValue(new APIResponse<TransactionResponse>(t));
                onFinishCall();
            }
        });

        return transactions;
    }

    public LiveData<APIResponse<GetBudgetResponse>> getBudgets(Integer userId) {
        onStartCall();
        final MutableLiveData<APIResponse<GetBudgetResponse>> budgets = new MutableLiveData<>();
        Call<List<GetBudgetResponse>> call = transactionService.getBudgetsForCategories(mTokenService.getAccessToken(),userId);
        call.enqueue(new Callback<List<GetBudgetResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetBudgetResponse>> call, @NonNull Response<List<GetBudgetResponse>> response) {
                if (response.isSuccessful()) {
                    budgets.postValue(new APIResponse<GetBudgetResponse>(response.body()));
                }else{
                    budgets.postValue(new APIResponse<GetBudgetResponse>(new Exception(response.message())));
                }
                onFinishCall();
            }

            @Override
            public void onFailure(@NonNull Call<List<GetBudgetResponse>> call, @NonNull Throwable t) {
                budgets.postValue(new APIResponse<GetBudgetResponse>(t));
                onFinishCall();
            }
        });

        return budgets;
    }

    public LiveData<APIObjectResponse<CommonResponse>> saveTransaction(CreateTransactionPostRequest request) {
        onStartCall();
        final MutableLiveData<APIObjectResponse<CommonResponse>> resp = new MutableLiveData<>();
        Call<CommonResponse> call = transactionService.saveTransaction(mTokenService.getAccessToken(),request);
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

    public LiveData<APIObjectResponse<CommonResponse>> setBudget(PostBudgetRequest request) {
        onStartCall();
        final MutableLiveData<APIObjectResponse<CommonResponse>> resp = new MutableLiveData<>();
        Call<CommonResponse> call = transactionService.saveBudget(mTokenService.getAccessToken(),request);
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

