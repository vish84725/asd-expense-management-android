package com.cube365.asdexpensemanagement.ui.transactions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.enums.TransactionType;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddTransactionsActivity extends AppCompatActivity{
    private Button mSetDateButton,mSaveButton;
    private MaterialDatePicker mDatePicker;
    private TextView mDateTextView;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;

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
//            viewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);
//            viewModel.init(this);
            loadingDialog = new LoadingDialog(AddTransactionsActivity.this);
            mSetDateButton = findViewById(R.id.button_addTransaction_date);
            mSaveButton = findViewById(R.id.button_saveTransaction);
            mDateTextView = findViewById(R.id.textView_addTransaction_date);
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
//            setActivityLoader();
//            setData();
        }catch (Exception ex){
//            mAlertDialog.showMessage(ex.getMessage());
        }

    }

    private void setEventListeners(){
        mSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatePicker.show(getSupportFragmentManager(),"");
            }
        });

        mDatePicker.addOnPositiveButtonClickListener(selection ->{
            mAlertDialog.showMessage(selection.toString());
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });
    }

    private void saveTransaction(){
        mAlertDialog.showMessage("Save Transaction");
    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.ADD_TRANSACTIONS_ACTIVITY,false);
            setContentView(R.layout.activity_add_transaction);
        }catch (Exception ex){
        }
    }
}
