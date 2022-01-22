package com.cube365.asdexpensemanagement.ui.user;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.users.CreateUserPostRequest;
import com.cube365.asdexpensemanagement.models.users.LoginUserPostRequest;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.ui.transactions.TransactionsActivity;
import com.cube365.asdexpensemanagement.utils.Constants;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername,editTextPassword;
    private UserViewModel viewModel;
    Button buttonLogin,buttonRegister;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            setActivityLoader();
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
                    if(validateInputFields()){
                        loginUser();
                    }else{
                        mAlertDialog.showMessage("username and password is required");
                    }
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
            viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            viewModel.init(this);
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

    private Boolean validateInputFields(){
        if(editTextUsername.getText() == null || editTextUsername.getText().toString().equals("")){
            return false;
        }
        if(editTextPassword.getText() == null || editTextPassword.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private LoginUserPostRequest getCreateRequest(){
        try{
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            LoginUserPostRequest user = new LoginUserPostRequest(username,password);
            return user;
        }catch (Exception ex){
            return null;
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

    private void loginUser(){
        LoginUserPostRequest request = getCreateRequest();
        if(request != null){
            viewModel.loginUser(request).observe(this, new Observer<APIObjectResponse<CommonResponse>>() {
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
                                    mAlertDialog.setCallback(() -> {
                                        tokenService.setLoggedInUser(apiResponse.getData().getMessage());
                                        startMenuActivity();
                                    });
                                    mAlertDialog.showMessage("Successfully logged in");
                                }else{
                                    mAlertDialog.showMessage("Invalid username or password");
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
