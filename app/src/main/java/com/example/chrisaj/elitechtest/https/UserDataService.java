package com.example.chrisaj.elitechtest.https;

import com.example.chrisaj.elitechtest.model.UserApiResponse;
import com.example.chrisaj.elitechtest.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserDataService {
    //@GET ("users?since={user_id}")     // 錯誤宣告
    @GET ("users")
    Call<List<UserModel>> getUsers(@Query(value = "user_id") String UserId); // 直接使用Query
    //Call<UserApiResponse> getUsers();  // 報Expected BEGIN_OBJECT but was BEGIN_ARRAY
}
