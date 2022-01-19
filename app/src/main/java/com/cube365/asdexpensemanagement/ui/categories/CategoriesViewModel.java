package com.cube365.asdexpensemanagement.ui.categories;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.repositories.CategoriesRepository;
import com.cube365.asdexpensemanagement.repositories.TransactionsRepository;

public class CategoriesViewModel  extends AndroidViewModel {
    private CategoriesRepository categoriesRepository;
    private LiveData<Boolean> loadingStatusLiveData;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        try{
            categoriesRepository = new CategoriesRepository(activity);
            loadingStatusLiveData = categoriesRepository.getLoadingStatusLiveData();
        }catch (Exception ex){
            Log.d("login",ex.getMessage());
        }
    }

    public LiveData<Boolean> getLoadingStatusLiveData() {
        final LiveData<Boolean> apiObjectResponseLiveData = categoriesRepository.getLoadingStatusLiveData();
        apiObjectResponseLiveData.observeForever(new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

    public LiveData<APIResponse<GetCategoryResponse>> getAllCategories(Integer userId) {
        final LiveData<APIResponse<GetCategoryResponse>> apiObjectResponseLiveData = categoriesRepository.getAllCategories(userId);
        apiObjectResponseLiveData.observeForever(new Observer<APIResponse<GetCategoryResponse>>(){
            @Override
            public void onChanged(APIResponse<GetCategoryResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

    public LiveData<APIObjectResponse<CommonResponse>> createCategory(CreateCategoryPostRequest request) {
        final LiveData<APIObjectResponse<CommonResponse>> apiObjectResponseLiveData = categoriesRepository.saveCategory(request);
        apiObjectResponseLiveData.observeForever(new Observer<APIObjectResponse<CommonResponse>>(){
            @Override
            public void onChanged(APIObjectResponse<CommonResponse> apiResponse) {
                apiObjectResponseLiveData.removeObserver(this);
            }
        });
        return apiObjectResponseLiveData;
    }

}
