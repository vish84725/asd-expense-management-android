package com.cube365.asdexpensemanagement.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.ui.transactions.TransactionsActivity;

public class LoginActivity extends AppCompatActivity {
//    private UserViewModel viewModel;
    EditText editTextUsername,editTextPassword;
    Button buttonLogin,buttonRegister;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            updateUI();

            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startRegisterActivity();
                }
            });

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    loginUser();
                    startMenuActivity();
                }
            });
        }
    }

    private void updateUI(){
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();

            setContentView(R.layout.activity_login);
        }catch (Exception ex){

        }
    }

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
//            viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//            viewModel.init();
//
            editTextUsername = findViewById(R.id.editTextUsername);
            editTextPassword = findViewById(R.id.editTextPassword);
            buttonLogin = findViewById(R.id.buttonLogin);
            buttonRegister = findViewById(R.id.buttonRegister);
            loadingDialog = new LoadingDialog(LoginActivity.this);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private void startMenuActivity(){
        try{
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }

    private void startRegisterActivity(){
        try{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }
}
