package com.example.chrisaj.elitechtest;



import android.os.Bundle;
import android.util.Log;

import com.example.chrisaj.elitechtest.adapter.UserListAdapter;
import com.example.chrisaj.elitechtest.databinding.ActivityMainBinding;
import com.example.chrisaj.elitechtest.model.UserModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 *   Main主頁
 */
public class MainActivity extends AppCompatActivity {

    private MainActivityModel mMainActivityModel;
    private UserListAdapter mUserListAdapter;
    private RecyclerView mRecyclerView;

    //第一次撈取
    private boolean mIsFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // bind recyclerView
        mRecyclerView = activityMainBinding.rvUserList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mMainActivityModel = ViewModelProviders.of(this).get(MainActivityModel.class);

        mUserListAdapter = new UserListAdapter();
        mRecyclerView.setAdapter(mUserListAdapter);

        // 呼叫API
        getUserListFromServer("since=0");

        setListener();
    }
    // 呼叫API through Retrofit
    private void getUserListFromServer(String userId) {
        mMainActivityModel.getUserData(userId).observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                if(mIsFirst) {
                    mIsFirst = false;
                    mUserListAdapter.setUserList((ArrayList<UserModel>) userModels);
                } else {
                    // 100筆資料數量判斷處理

                    // 資料加入列表中
                    mUserListAdapter.addUserList((ArrayList<UserModel>) userModels);
                }
            }
        });
    }

    // 列表滾動事件
    private void setListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 超過100筆資料 就不再撈取
                if(mUserListAdapter.getItemCount() >= 100) {
                    return;
                }
                // 分頁資料撈取處理

            }
        });
    }

}
