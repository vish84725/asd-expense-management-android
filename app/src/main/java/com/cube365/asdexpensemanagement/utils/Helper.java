package com.cube365.asdexpensemanagement.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.cube365.asdexpensemanagement.services.ITokenService;
import com.cube365.asdexpensemanagement.services.TokenService;
import com.cube365.asdexpensemanagement.ui.user.LoginActivity;
import com.cube365.asdexpensemanagement.R;

public class Helper {
    ITokenService tokenService;
    public Helper(){
    }

    public static void setAppBar(ActionBar actionBar, String title, boolean isLogoutVisible){
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.layout_action_bar);
        Button logoutButton  = (Button)actionBar.getCustomView().findViewById(R.id.button_logout);
        TextView textViewAppBarTitle = (TextView)actionBar.getCustomView().findViewById(R.id.textViewAppBarTitle);
        textViewAppBarTitle.setText(title);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Constants.Colors.ACTIONBAR_COLOR)));
        actionBar.show();
        if(isLogoutVisible){
            logoutButton.setVisibility(View.VISIBLE);
        }else{
            logoutButton.setVisibility(View.INVISIBLE);
        }


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity hostActivity = getActivity(v.getContext());
                ITokenService tokenService = new TokenService(hostActivity);
                tokenService.setAccessToken(null);
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                hostActivity.startActivity(intent);
            }
        });
    }

    private static Activity getActivity(Context context ) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

}
