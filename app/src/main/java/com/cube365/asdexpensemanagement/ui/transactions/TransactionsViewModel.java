package com.cube365.asdexpensemanagement.ui.transactions;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

public class TransactionsViewModel extends AndroidViewModel {
    public TransactionsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        try{
//            picklistRepository = new PicklistRepository(activity);
//            mApiResponse = new MediatorLiveData<>();
//            loadingStatusLiveData = picklistRepository.getLoadingStatusLiveData();
        }catch (Exception ex){
            Log.d("login",ex.getMessage());
        }
    }
}
