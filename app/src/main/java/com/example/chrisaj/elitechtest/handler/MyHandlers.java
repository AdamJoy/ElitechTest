package com.example.chrisaj.elitechtest.handler;

import android.content.Context;
import android.view.View;

import com.example.chrisaj.elitechtest.model.UserModel;

import androidx.appcompat.app.AppCompatActivity;

public class MyHandlers {

    private View.OnClickListener mOnClickListener;

    Context context;

    public MyHandlers(Context context) {
        this.context = context;
    }

   public void onCloseClick(View view) {
       ((AppCompatActivity)context).finish();
   }

}
