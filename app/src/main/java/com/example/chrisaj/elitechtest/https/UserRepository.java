package com.example.chrisaj.elitechtest.https;

import android.util.Log;

import com.example.chrisaj.elitechtest.https.RetrofitClient;
import com.example.chrisaj.elitechtest.https.UserDataService;
import com.example.chrisaj.elitechtest.model.UserDetailModel;
import com.example.chrisaj.elitechtest.model.UserModel;
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
    private UserDetailModel mUserDetailModel;
    private MutableLiveData<List<UserModel>> mMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UserDetailModel> mMutableLiveDataUserDetail = new MutableLiveData<>();

    public UserRepository() {

    }

    // API:取得使用者列表
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
                    mMutableLiveData.setValue(mUserList);
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.d("TAG","API__結果__error = " +  t.getMessage());
                call.cancel();
            }
        });
        return mMutableLiveData;
    }


    // API:取得使用者詳細資訊
    public MutableLiveData<UserDetailModel> getUserDetailMutableLiveData(String userName) {

        final UserDataService userDataService = RetrofitClient.getService();

        Call<UserDetailModel> call = userDataService.getUserDetail(userName);

        call.enqueue(new Callback<UserDetailModel>() {  // enqueue 異步
            @Override
            public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                Log.d("TAG","使用者詳細資訊Api__結果" + new Gson().toJson(response));
                UserDetailModel userDetailModel = response.body();
                if (userDetailModel != null) {
                    mUserDetailModel =   userDetailModel;
                    mMutableLiveDataUserDetail.setValue( mUserDetailModel);
                }
            }
            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {
                Log.d("TAG","使用者詳細資訊API__結果__error = " +  t.getMessage());
                call.cancel();
            }
        });
        return mMutableLiveDataUserDetail;
    }
}
