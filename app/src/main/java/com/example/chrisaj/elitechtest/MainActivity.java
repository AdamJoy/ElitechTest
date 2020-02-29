package com.example.chrisaj.elitechtest;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.chrisaj.elitechtest.adapter.UserListAdapter;
import com.example.chrisaj.elitechtest.databinding.ActivityMainBinding;
import com.example.chrisaj.elitechtest.model.UserModel;
import com.example.chrisaj.elitechtest.tool.key;
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

    private ActivityMainBinding mActivityMainBinding;
    private MainActivityModel mMainActivityModel;
    private UserListAdapter mUserListAdapter;
    private RecyclerView mRecyclerView;

    // 第一次撈API狀態
    private boolean mIsFirst = true;

    // 讀取API中
    private boolean mIsReading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 使用Binding 無須再setContentView
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // bind recyclerView
        mRecyclerView = mActivityMainBinding.rvUserList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mMainActivityModel = ViewModelProviders.of(this).get(MainActivityModel.class);

        // Item點擊事件
        mUserListAdapter = new UserListAdapter(new UserListAdapter.ItemClick() {
            @Override
            public void onClicked(View view, UserModel user) {
                Log.d("TAG","列表Item點擊事件 = " + user.getLogin());
                Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(key.KEY_BUNDLE_USER_NAME, user.getLogin());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mUserListAdapter);

        setListener();
        getUserListFromServer("0"); // 呼叫API
    }

    // 呼叫API through Retrofit
    private void getUserListFromServer(final String userId) {

        Log.d("TAG","呼叫取得資料API___userId = " + userId);

        mIsReading = true;

        final Observer<List<UserModel>> observer = new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                Log.d("TAG","撈取成功");
                mMainActivityModel.getUserData(userId).removeObserver(this);
                if(mIsFirst) {
                    mIsFirst = false;
                    mUserListAdapter.setUserList((ArrayList<UserModel>) userModels);
                } else {
                    // 資料插入列表中
                    Log.d("TAG","剛撈完___資料數量 = " + userModels.size());
                    if(userModels.get(userModels.size() - 1).getId() != mUserListAdapter.getLastUserId()) {   // API資料跟現有資料最後一筆做比較，不同才插入

                        // 100筆資料處理
                        if(mUserListAdapter.getItemCount() + userModels.size() > 100) {
                            for(UserModel userItem : userModels) {
                                if(mUserListAdapter.getItemCount() >= 100) {
                                    break;
                                }
                                mUserListAdapter.addSingleUserItem(userItem);
                            }
                        } else {
                        // 直接插入
                            mUserListAdapter.addUserList((ArrayList<UserModel>) userModels);
                        }
                    }
                }
                mIsReading = false;
            }
        };
        mMainActivityModel.getUserData(userId).observe(this, observer);    // 開始呼叫API
    }


    private void setListener() {
        // RecyclerView 列表滾動事件偵測
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mUserListAdapter.getItemCount() >= 100) {  // 超過100筆資料 不再撈資料
                    return;
                }
                if (!recyclerView.canScrollVertically(4) && !mIsReading ) {
                    Log.d("TAG","下一頁__id = " + ((UserListAdapter)recyclerView.getAdapter()).getLastUserId());
                    getUserListFromServer(String.valueOf( ((UserListAdapter)recyclerView.getAdapter()).getLastUserId()));
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

}
