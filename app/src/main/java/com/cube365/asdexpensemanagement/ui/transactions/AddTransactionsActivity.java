package com.cube365.asdexpensemanagement.ui.transactions;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.enums.TransactionType;
import com.cube365.asdexpensemanagement.models.categories.CreateCategoryPostRequest;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.categories.CategoriesViewModel;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class AddTransactionsActivity extends AppCompatActivity{
    private TransactionsViewModel viewModel;
    private Button mSetDateButton,mSaveButton;
    private MaterialDatePicker mDatePicker;
    private TextView mDateTextView;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    TextInputEditText mTitleInput,mTitleAmount,mTitleNotes;
    RadioGroup mRadioGroup;
    Date selectedDate = null;

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
            viewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);
            viewModel.init(this);
            loadingDialog = new LoadingDialog(AddTransactionsActivity.this);
            mSetDateButton = findViewById(R.id.button_addTransaction_date);
            mSaveButton = findViewById(R.id.button_saveTransaction);
            mDateTextView = findViewById(R.id.textView_addTransaction_date);
            mTitleAmount = findViewById(R.id.addTr_amount);
            mTitleInput = findViewById(R.id.addTr_title);
            mTitleNotes = findViewById(R.id.addTr_notes);
            mRadioGroup = findViewById(R.id.radioGroup);

            mDatePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select date")
                            .build();
            return true;
        }catch (Exception ex){
//            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }

    private void loadData(){
        try{
            setActivityLoader();
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
        mSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatePicker.show(getSupportFragmentManager(),"");
            }
        });

        mDatePicker.addOnPositiveButtonClickListener(selection ->{
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis((long)selection);
            mDateTextView.setText(calendar.getTime().toString());
            selectedDate = calendar.getTime();

//            Date date = new Date(selection.toString());
//            DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
//            mDateTextView.setText(df.format(date).toString());
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputFields()){
                    saveTransaction();
                }else{
                    mAlertDialog.showMessage("Title is required");
                }
            }
        });
    }

    private void saveTransaction(){
        CreateTransactionPostRequest request = getCreateRequest();
        if(request != null){
            viewModel.createTransaction(request).observe(this, new Observer<APIObjectResponse<CommonResponse>>() {
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
        if(mTitleAmount.getText() == null || mTitleAmount.getText().toString().equals("")){
            return false;
        }
        if(mTitleInput.getText() == null || mTitleInput.getText().toString().equals("")){
            return false;
        }
        if(mTitleNotes.getText() == null || mTitleNotes.getText().toString().equals("")){
            return false;
        }
        if(selectedDate == null){
            return false;
        }
        return true;
    }

    private CreateTransactionPostRequest getCreateRequest(){
        try{
            Double amount = Double.parseDouble(mTitleAmount.getText().toString());
            String title = mTitleInput.getText().toString();
            String notes = mTitleNotes.getText().toString();
            String transactionType = "INCOME";
            String recurringType = "MONTHLY";
            GetCategoryResponse category = new GetCategoryResponse();
            category.setId(1);
            GetUserResponse user = new GetUserResponse();
            user.setId(1);
            String date = "2022-01-17";

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(selectedDate).toString();

            CreateTransactionPostRequest transaction = new CreateTransactionPostRequest(title,notes,amount,recurringType,transactionType,formattedDate,category,user);

            return  transaction;
        }catch (Exception ex){
            return null;
        }
    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.ADD_TRANSACTIONS_ACTIVITY,false);
            setContentView(R.layout.activity_add_transaction);
        }catch (Exception ex){
        }
    }
}
