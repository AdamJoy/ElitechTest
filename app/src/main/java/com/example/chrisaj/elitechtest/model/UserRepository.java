package com.example.chrisaj.elitechtest.model;

import android.util.Log;

import com.example.chrisaj.elitechtest.https.RetrofitClient;
import com.example.chrisaj.elitechtest.https.UserDataService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 *   Retrofit 呼叫API
 */
public class UserRepository {
    private static final String TAG = "UserRepository";
    private ArrayList<UserModel> mUserList = new ArrayList<>();
    private MutableLiveData<List<UserModel>> mMutableLiveData = new MutableLiveData<>();

    public UserRepository() {

    }
    public MutableLiveData<List<UserModel>> getMutableLiveData(String userId) {

        final UserDataService userDataService = RetrofitClient.getService();

        Call<List<UserModel>> call = userDataService.getUsers(userId);

        call.enqueue(new Callback<List<UserModel>>() {  // enqueue 異步
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                Log.d("TAG","Api__結果" + new Gson().toJson(response));

                List<UserModel> userApiResponse = response.body();
                if (userApiResponse != null) {
                    mUserList = (ArrayList<UserModel>) userApiResponse;
                    Log.d("TAG","Api__結果_資料長度 = " + mUserList.size());
                    mMutableLiveData.setValue(mUserList);
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.d("TAG","API__結果B = " +  t.getMessage());

            }
        });
        return mMutableLiveData;
    }
}
