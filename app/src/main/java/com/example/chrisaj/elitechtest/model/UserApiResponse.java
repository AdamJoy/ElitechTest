package com.example.chrisaj.elitechtest.model;

import com.example.chrisaj.elitechtest.model.UserModel;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 *   API 資料主Model
 */
public class UserApiResponse {

    private List<UserModel> userModelList = null;

    public List<UserModel> getUserDataList() {
        return userModelList;
    }

    public void setUserDataList(List<UserModel> userDataList) {
        userModelList = userDataList;
    }
}
