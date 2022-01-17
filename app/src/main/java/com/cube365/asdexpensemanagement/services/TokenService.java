package com.cube365.asdexpensemanagement.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cube365.asdexpensemanagement.utils.Constants;

public class TokenService implements ITokenService {
    private Activity activity;

    public TokenService(Activity activity){
        try{
            this.activity = activity;
        }catch (Exception ex){

        }
    }
    public void setAccessToken(String token){
        try{
            SharedPreferences preferences = this.activity.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
            preferences.edit().putString("TOKEN",token).apply();
        }catch (Exception ex){

        }

    }

    public String getAccessToken(){
        try{
            SharedPreferences preferences = this.activity.getSharedPreferences(Constants.APP_NAME,Context.MODE_PRIVATE);
            String retrivedToken = preferences.getString(Constants.AUTHORIZATION_TOKEN,null);
            if(retrivedToken == null || retrivedToken == ""){
                return null;
            }
            return "Bearer " + retrivedToken;

        }catch (Exception ex){

        }
        return null;
    }
}
