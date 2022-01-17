package com.cube365.asdexpensemanagement.ui.custom;

import android.app.Activity;
import android.app.AlertDialog;
import com.cube365.asdexpensemanagement.R;
import android.view.LayoutInflater;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading,null));
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void dismissLoadingDialog(){
        if(alertDialog != null){
            alertDialog.dismiss();
        }
    }
}
