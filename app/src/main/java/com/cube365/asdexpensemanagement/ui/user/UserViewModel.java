package com.cube365.asdexpensemanagement.ui.user;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.models.users.CreateUserPostRequest;
import com.cube365.asdexpensemanagement.models.users.LoginUserPostRequest;
import com.cube365.asdexpensemanagement.repositories.TransactionsRepository;
import com.cube365.asdexpensemanagement.repositories.UserRepository;

public class UserViewModel  extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<Boolean> loadingStatusLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        try{
            userRepository = new UserRepository(activity);
            loadingStatusLiveData = userRepository.getLoadingStatusLiveData();
        }catch (Exception ex){
            Log.d("login",ex.getMessage());
        }
    }

    public LiveData<Boolean> getLoadingStatusLiveData() {
        final LiveData<Boolean> apiObjectResponseLiveData = userRepository.getLoadingStatusLiveData();
        apiObjectResponseLiveData.observeForever(new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }


    public LiveData<APIObjectResponse<CommonResponse>> loginUser(LoginUserPostRequest request) {
        final LiveData<APIObjectResponse<CommonResponse>> apiObjectResponseLiveData = userRepository.loginUser(request);
        apiObjectResponseLiveData.observeForever(new Observer<APIObjectResponse<CommonResponse>>(){
            @Override
            public void onChanged(APIObjectResponse<CommonResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

    public LiveData<APIObjectResponse<CommonResponse>> addUser(CreateUserPostRequest request) {
        final LiveData<APIObjectResponse<CommonResponse>> apiObjectResponseLiveData = userRepository.addUser(request);
        apiObjectResponseLiveData.observeForever(new Observer<APIObjectResponse<CommonResponse>>(){
            @Override
            public void onChanged(APIObjectResponse<CommonResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

}
