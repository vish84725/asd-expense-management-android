package com.cube365.asdexpensemanagement.ui.transactions;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.adaptors.SpinnerAdaptor;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.PostBudgetRequest;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.categories.CategoriesViewModel;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ManageBudgetActivity extends AppCompatActivity {
    private TransactionsViewModel viewModel;
    private CategoriesViewModel categoriesViewModel;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    List<GetCategoryResponse> categoriesData = new ArrayList<GetCategoryResponse>();
    private SpinnerAdaptor spinnerAdaptor;
    TextInputEditText mAmountEditText;
    GetCategoryResponse mSelectedCategory;
    Button mSaveButton;
    Spinner mSpinner;
//    TextInputEditText mTitleInput,mTitleAmount,mTitleNotes;

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
            mSpinner = findViewById(R.id.spinner_categories);
            viewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);
            viewModel.init(this);
            categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
            categoriesViewModel.init(this);
            loadingDialog = new LoadingDialog(ManageBudgetActivity.this);
            spinnerAdaptor = new SpinnerAdaptor(getApplicationContext(),categoriesData);
            mSpinner.setAdapter(spinnerAdaptor);
            mAmountEditText = findViewById(R.id.textFieldBudget);
            mSaveButton = findViewById(R.id.button_saveBudget);
            return true;
        }catch (Exception ex){
//            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }

    private void loadData(){
        try{
            setActivityLoader();
            setCategoriesData();
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

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

    private void setEventListeners(){
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputFields()){
                    saveBudget();
                }else{
                    mAlertDialog.showMessage("Title is required");
                }
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCategory= (GetCategoryResponse) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCategoriesData(){
        categoriesViewModel.getAllCategories(1).observe(this, new Observer<APIResponse<GetCategoryResponse>>() {
            @Override
            public void onChanged(APIResponse<GetCategoryResponse> apiResponse) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (apiResponse == null) {
                            mAlertDialog.showMessage(Constants.ErrorMessage.GENERAL_MESSAGE);
                            return;
                        }
                        if (apiResponse.getError() == null) {
                            if(apiResponse.getData() != null){
                                categoriesData.clear();
                                categoriesData.addAll(apiResponse.getData());
                                spinnerAdaptor.notifyDataSetChanged();
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

    private void saveBudget(){
        PostBudgetRequest request = getCreateRequest();
        if(request != null){
            viewModel.setBudget(request).observe(this, new Observer<APIObjectResponse<CommonResponse>>() {
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

    private Boolean validateInputFields(){
        if(mAmountEditText.getText() == null || mAmountEditText.getText().toString().equals("")){
            return false;
        }
        if(mSelectedCategory == null){
            return false;
        }
        return true;
    }

    private PostBudgetRequest getCreateRequest(){
        try{
            Double amount = Double.parseDouble(mAmountEditText.getText().toString());
            GetUserResponse user = new GetUserResponse();
            user.setId(1);
            GetCategoryResponse category = new GetCategoryResponse();
            category.setId(mSelectedCategory.getId());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(new Date()).toString();

            PostBudgetRequest request = new PostBudgetRequest(amount,formattedDate,category,user);
            return  request;
        }catch (Exception ex){
            return null;
        }
    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.MANAGE_BUDGET_ACTIVITY,false);
            setContentView(R.layout.activity_budget);
        }catch (Exception ex){
        }
    }
}
