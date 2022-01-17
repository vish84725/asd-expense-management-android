package com.cube365.asdexpensemanagement.ui.user;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.cube365.asdexpensemanagement.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
//        if(init()){
//            loadData();
//            updateUI();
//
//            autoLogin();
//
//            buttonLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    loginUser();
//                }
//            });
//        }
    }

    private void updateUI(){
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();

            setContentView(R.layout.activity_login);
        }catch (Exception ex){

        }
    }
}
