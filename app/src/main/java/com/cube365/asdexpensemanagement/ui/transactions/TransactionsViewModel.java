package com.cube365.asdexpensemanagement.ui.transactions;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.repositories.TransactionsRepository;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TransactionsViewModel  extends AndroidViewModel {
    private TransactionsRepository transactionsRepository;
    private LiveData<Boolean> loadingStatusLiveData;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        try{
            transactionsRepository = new TransactionsRepository(activity);
            loadingStatusLiveData = transactionsRepository.getLoadingStatusLiveData();
        }catch (Exception ex){
            Log.d("login",ex.getMessage());
        }
    }

    public LiveData<Boolean> getLoadingStatusLiveData() {
        final LiveData<Boolean> apiObjectResponseLiveData = transactionsRepository.getLoadingStatusLiveData();
        apiObjectResponseLiveData.observeForever(new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

    public LiveData<APIResponse<TransactionResponse>> getAllTransactions(Integer userId, String transactionType) {
        final LiveData<APIResponse<TransactionResponse>> apiObjectResponseLiveData = transactionsRepository.getAllTransactions(userId,transactionType);
        apiObjectResponseLiveData.observeForever(new Observer<APIResponse<TransactionResponse>>(){
            @Override
            public void onChanged(APIResponse<TransactionResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

    public LiveData<APIObjectResponse<CommonResponse>> createTransaction(CreateTransactionPostRequest request) {
        final LiveData<APIObjectResponse<CommonResponse>> apiObjectResponseLiveData = transactionsRepository.saveTransaction(request);
        apiObjectResponseLiveData.observeForever(new Observer<APIObjectResponse<CommonResponse>>(){
            @Override
            public void onChanged(APIObjectResponse<CommonResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

}
