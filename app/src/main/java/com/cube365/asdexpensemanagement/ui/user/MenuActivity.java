package com.cube365.asdexpensemanagement.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.categories.CategoriesActivity;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.transactions.TransactionsActivity;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.cube365.asdexpensemanagement.utils.Helper;
import com.cube365.asdexpensemanagement.R;

public class MenuActivity extends AppCompatActivity {
    AlertMessageDialog mAlertDialog;
    FrameLayout frameLayoutViewStores,frameLayoutPicklists,frameLayoutPutaway,frameLayoutReceiving;
    ITokenService tokenService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            setEventListeners();
        }

    }

    @Override
    public void onBackPressed() {
//        if(tokenService.getAccessToken() == null){
//            finish();
//        }
    }

    private void setEventListeners(){
        frameLayoutPicklists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                startActivity(intent);
            }
        });

        frameLayoutViewStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });

        frameLayoutPutaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), PutawayActivity.class);
//                startActivity(intent);
            }
        });

        frameLayoutReceiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ReceivingActivity.class);
//                startActivity(intent);
            }
        });
    }

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
            frameLayoutViewStores = (FrameLayout) findViewById(R.id.frameLayout_stockview);
            frameLayoutPicklists = (FrameLayout) findViewById(R.id.frameLayout_picklists);
            frameLayoutPutaway = (FrameLayout) findViewById(R.id.frameLayout_putaway);
            frameLayoutReceiving = (FrameLayout) findViewById(R.id.frameLayout_recieving);

            return true;
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }
        return  false;
    }

    private void updateUI(){
        try{
            Helper.setAppBar(getSupportActionBar(), Constants.ActivityTitles.MAIN_MENU,true);
            setContentView(R.layout.activity_menu);
        }catch (Exception ex){
        }
    }
}
