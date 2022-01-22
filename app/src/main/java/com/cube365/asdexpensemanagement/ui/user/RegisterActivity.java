package com.cube365.asdexpensemanagement.ui.user;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.models.common.APIObjectResponse;
import com.cube365.asdexpensemanagement.models.common.CommonResponse;
import com.cube365.asdexpensemanagement.models.transactions.CreateTransactionPostRequest;
import com.cube365.asdexpensemanagement.models.users.CreateUserPostRequest;
import com.cube365.asdexpensemanagement.models.users.GetUserResponse;
import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.custom.AlertMessageDialog;
import com.cube365.asdexpensemanagement.ui.custom.LoadingDialog;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    TextInputEditText editTextUsername,editTextPassword;
    Button buttonRegister;
    LoadingDialog loadingDialog;
    ITokenService tokenService;
    AlertMessageDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        if(init()){
            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });
        }
    }

    private void updateUI(){
        try{
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();

            setContentView(R.layout.activity_register_user);
        }catch (Exception ex){

        }
    }

    private CreateUserPostRequest getCreateRequest(){
        try{
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            CreateUserPostRequest user = new CreateUserPostRequest(username,password);
            return user;
        }catch (Exception ex){
            return null;
        }
    }

    private void registerUser(){
        CreateUserPostRequest request = getCreateRequest();
        if(request != null){
            viewModel.addUser(request).observe(this, new Observer<APIObjectResponse<CommonResponse>>() {
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
                                        finish();
                                    });
                                    mAlertDialog.showMessage("Successfully registered");
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

    private boolean init(){
        try {
            mAlertDialog = new AlertMessageDialog(this);
            tokenService = new TokenService(this);
            viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            viewModel.init(this);
            editTextUsername = findViewById(R.id.textField_userName);
            editTextPassword = findViewById(R.id.textField_password);
            buttonRegister = findViewById(R.id.buttonRegister);
            loadingDialog = new LoadingDialog(RegisterActivity.this);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private void startMenuActivity(){
        try{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }catch (Exception ex){
            mAlertDialog.showMessage(ex.getMessage());
        }

    }
}
