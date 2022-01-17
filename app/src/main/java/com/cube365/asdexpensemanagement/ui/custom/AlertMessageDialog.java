package com.cube365.asdexpensemanagement.ui.custom;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.cube365.asdexpensemanagement.R;

public class AlertMessageDialog {
    Activity activity;
    AlertDialog alertDialog;
    Button buttonOk;
    TextView textViewMessage;
    IAlertMessageDialog mCallback;

    public AlertMessageDialog(Activity activity) {
        this.activity = activity;
        setView();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLoadingDialog();
                if(mCallback != null){
                    mCallback.invokeSuccessCallback();
                    mCallback = null;
                }
            }
        });
    }

    private void setView(){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.activity_alert_dialog,null);
            builder.setView(dialogView);

            textViewMessage = (TextView)dialogView.findViewById(R.id.textView_Ok);
            buttonOk = (Button)dialogView.findViewById(R.id.buttonOk);

            alertDialog = builder.create();
            alertDialog.setCancelable(false);

        }catch (Exception ex){

        }

    }

    private void dismissLoadingDialog(){
        alertDialog.dismiss();
    }

    public void showMessage(String message){
        textViewMessage.setText(message);
        alertDialog.show();
    }

    public void setCallback(IAlertMessageDialog callback){
        this.mCallback = callback;
    }
}
