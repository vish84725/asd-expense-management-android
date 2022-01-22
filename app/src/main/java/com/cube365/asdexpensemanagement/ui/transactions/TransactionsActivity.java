package com.cube365.asdexpensemanagement.ui.transactions;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.adaptors.TransactionsAdaptor;
import com.cube365.asdexpensemanagement.enums.TransactionType;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.cube365.asdexpensemanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity implements TransactionsAdaptor.ItemClickListener {
    private TransactionsViewModel viewModel;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    List<TransactionResponse> transationsData = new ArrayList<TransactionResponse>();

    FrameLayout mFrameIncomeFilter,mFrameExpenseFilter;
    protected RecyclerView mRecyclerView;
    protected TransactionsAdaptor mAdapter;
    private TransactionType mTransactionTypeFlag = TransactionType.Income;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            loadData();
            setEventListeners();
        }

    }

    @Override
    public void onRestart() {
        super.onRestart();
        setTransactionsData();
    }

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
            viewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);
            viewModel.init(this);
            loadingDialog = new LoadingDialog(TransactionsActivity.this);
            mFrameIncomeFilter = (FrameLayout) findViewById(R.id.frameLayout_transactions_incomeFilter);
            mFrameExpenseFilter = (FrameLayout) findViewById(R.id.frameLayout_transactions_expenseFilter);
            mRecyclerView = (RecyclerView)findViewById(R.id.recycleView_transactions);
            fab = findViewById(R.id.fab);
            return true;
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }



    private void setEventListeners(){
        mFrameIncomeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTransactionTypeFlag == TransactionType.Expense){
                    toggleFilter(true);
                }
            }
        });

        mFrameExpenseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTransactionTypeFlag == TransactionType.Income){
                    toggleFilter(false);
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toggleFilter(boolean isIncome){
        if(isIncome){
            mTransactionTypeFlag = TransactionType.Income;
            mFrameExpenseFilter.setBackground(new ColorDrawable(Color.parseColor(Constants.Colors.ACTIONBAR_COLOR)));
            mFrameIncomeFilter.setBackground(new ColorDrawable(Color.parseColor(Constants.Colors.ASD_BLUE_COLOR)));
        }else{
            mTransactionTypeFlag = TransactionType.Expense;
            mFrameIncomeFilter.setBackground(new ColorDrawable(Color.parseColor(Constants.Colors.ACTIONBAR_COLOR)));
            mFrameExpenseFilter.setBackground(new ColorDrawable(Color.parseColor(Constants.Colors.ASD_BLUE_COLOR)));
        }
        setTransactionsData();
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
            setData();
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }

    private void setTransactionsData(){
        String transactionType = "INCOME";
        if(mTransactionTypeFlag == TransactionType.Income){
            transactionType = "INCOME";
        }else if(mTransactionTypeFlag == TransactionType.Expense){
            transactionType = "EXPENSE";
        }
        viewModel.getAllTransactions(tokenService.getLoggedInUser().getId(),transactionType).observe(this, new Observer<APIResponse<TransactionResponse>>() {
            @Override
            public void onChanged(APIResponse<TransactionResponse> apiResponse) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (apiResponse == null) {
                            mAlertDialog.showMessage(Constants.ErrorMessage.GENERAL_MESSAGE);
                            return;
                        }
                        if (apiResponse.getError() == null) {
                            if(apiResponse.getData() != null){
                                transationsData.clear();
                                transationsData.addAll(apiResponse.getData());
                                mAdapter.notifyDataSetChanged();
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

    private void setData(){
        setTransactionsData();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new TransactionsAdaptor(transationsData);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter.setClickListener(this);
            mRecyclerView.setNestedScrollingEnabled(true);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.TRANSACTIONS_ACTIVITY,true);
            setContentView(R.layout.activity_transactions);
        }catch (Exception ex){
        }
    }

    @Override
    public void onItemClick(View view, int position) {
//        PicklistResponse picklist = mAdapter.getPicklistItem(position);
//        if(picklist != null){
//            Intent intent = new Intent(this, BinSelectionActivity.class);
//            intent.putExtra("picklist",picklist);
//            startActivity(intent);
//        }
    }

}
