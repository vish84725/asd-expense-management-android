package com.cube365.asdexpensemanagement.ui.categories;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.adaptors.CategoriesAdaptor;
import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.ui.transactions.AddTransactionsActivity;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {
    private CategoriesViewModel viewModel;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    Button mSaveCategoryButton;
    TextInputEditText mCategoryTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            loadData();
            setEventListeners();
        }

    }

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
            viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
            viewModel.init(this);
            loadingDialog = new LoadingDialog(AddCategoryActivity.this);
            mSaveCategoryButton = (Button) findViewById(R.id.button_saveCategory);
            mCategoryTextField = (TextInputEditText) findViewById(R.id.textField_addCategory);
            return true;
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }

    private Boolean validateInputFields(){
        if(mCategoryTextField.getText() == null || mCategoryTextField.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void setEventListeners(){
        mSaveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputFields()){
                    saveCategory();
                }else{
                    mAlertDialog.showMessage("Title is required");
                }
            }
        });
    }

    private void setActivityLoader(){
        viewModel.getLoadingStatusLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading != null){
                    if(isLoading){
                        loadingDialog.startLoadingDialog();
                    }else{
                        loadingDialog.dismissLoadingDialog();
                    }
                }else{
                    loadingDialog.dismissLoadingDialog();
                }
            }
        });
    }

    private void loadData(){
        try{
            setActivityLoader();
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.CATEGORIES_ACTIVITY,false);
            setContentView(R.layout.activity_add_category);
        }catch (Exception ex){
        }
    }

    private CreateCategoryPostRequest getCreateRequest(){
        try{
            GetUserResponse user = new GetUserResponse();
            user.setId(tokenService.getLoggedInUser().getId());
            return new CreateCategoryPostRequest(mCategoryTextField.getText().toString(),"", user);
        }catch (Exception ex){
            return null;
        }
    }

    private void saveCategory(){
        CreateCategoryPostRequest request = getCreateRequest();
        if(request != null){
            viewModel.createCategory(request).observe(this, new Observer<APIObjectResponse<CommonResponse>>() {
                @Override
                public void onChanged(APIObjectResponse<CommonResponse> apiResponse) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (apiResponse == null) {
                                mAlertDialog.showMessage(Constants.ErrorMessage.GENERAL_MESSAGE);
                                return;
                            }
                            if (apiResponse.getError() == null) {
                                if(apiResponse.getData().getSuccess().equals(true)){
                                    finish();
                                }else{
                                    mAlertDialog.showMessage(Constants.ErrorMessage.GENERAL_MESSAGE);
                                }
                                Log.i(TAG, "Data response is " + apiResponse.getData());
                            } else {
                                Throwable e = apiResponse.getError();
                                mAlertDialog.showMessage(e.getMessage());
                                Log.e(TAG, "Error is " + e.getLocalizedMessage());
                            }
                        }
                    });
                }
            });
        }

    }
}
