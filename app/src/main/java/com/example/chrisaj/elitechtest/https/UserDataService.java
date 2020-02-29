package com.example.chrisaj.elitechtest.https;


import com.example.chrisaj.elitechtest.model.UserDetailModel;
import com.example.chrisaj.elitechtest.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserDataService {

    @GET ("users?")
    Call<List<UserModel>> getUsers(@Query(value = "since") String UserId); // 直接使用Query
    //Call<UserApiResponse> getUsers();  // 報Expected BEGIN_OBJECT but was BEGIN_ARRAY

    @GET ("users/{user_name}")
    Call<UserDetailModel> getUserDetail(@Path(value = "user_name") String userName);
}
