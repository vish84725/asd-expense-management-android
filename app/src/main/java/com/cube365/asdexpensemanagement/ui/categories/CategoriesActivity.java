package com.cube365.asdexpensemanagement.ui.categories;

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

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.adaptors.CategoriesAdaptor;
import com.cube365.asdexpensemanagement.adaptors.CategoriesAdaptor;
import com.cube365.asdexpensemanagement.enums.TransactionType;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.ui.transactions.AddTransactionsActivity;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdaptor.ItemClickListener {
    private CategoriesViewModel viewModel;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    List<GetCategoryResponse> categoriesData = new ArrayList<GetCategoryResponse>();

    protected RecyclerView mRecyclerView;
    protected CategoriesAdaptor mAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            loadData();
//            setEventListeners();
        }

    }

    @Override
    public void onRestart() {
        super.onRestart();
//        setPicklistsData();
    }

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
            viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
            viewModel.init(this);
            loadingDialog = new LoadingDialog(CategoriesActivity.this);
            mRecyclerView = (RecyclerView)findViewById(R.id.recycleView_categories);
            fab = (FloatingActionButton) findViewById(R.id.fab_addCategories);
            return true;
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }



    private void setEventListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
                startActivity(intent);
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
            setData();
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }

    private void setCategoriesData(){
        viewModel.getAllCategories(1).observe(this, new Observer<APIResponse<GetCategoryResponse>>() {
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
        setCategoriesData();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new CategoriesAdaptor(categoriesData);
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
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.CATEGORIES_ACTIVITY,false);
            setContentView(R.layout.activity_categories);
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

