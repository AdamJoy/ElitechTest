package com.example.chrisaj.elitechtest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.chrisaj.elitechtest.databinding.ActivityUserdetailBinding;
import com.example.chrisaj.elitechtest.handler.MyHandlers;
import com.example.chrisaj.elitechtest.model.UserDetailModel;
import com.example.chrisaj.elitechtest.model.UserModel;
import com.example.chrisaj.elitechtest.tool.key;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 *  個人詳細頁主頁
 */
public class UserDetailActivity extends AppCompatActivity {

    private ActivityUserdetailBinding mActivityUserdetailBinding;
    private UserDetailActivityModel mUserDetailActivityModel;
    private String mUserName;
    private MyHandlers mMyHandlers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityUserdetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_userdetail);
        mUserDetailActivityModel = ViewModelProviders.of(this).get(UserDetailActivityModel.class);
        mMyHandlers = new MyHandlers(this);
        mActivityUserdetailBinding.setMyHandler(mMyHandlers);

        Log.d("TAG","個人詳細頁取得___userName = " + getIntent().getStringExtra(key.KEY_BUNDLE_USER_NAME));

        mUserName = getIntent().getStringExtra(key.KEY_BUNDLE_USER_NAME);
        if(mUserName != null) {
          getUserDetailFromServer(mUserName);  // 呼叫API
        }
    }

    private void getUserDetailFromServer(final String userName) {
        final Observer<UserDetailModel> observer = new Observer<UserDetailModel>() {
            @Override
            public void onChanged(UserDetailModel userModels) {
                Log.d("TAG","個人資料__撈取成功");
                mActivityUserdetailBinding.setUserdetail(userModels);
            }
        };
        mUserDetailActivityModel.getUserDetail(userName).observe(this, observer);    // 開始呼叫API
    }



}
