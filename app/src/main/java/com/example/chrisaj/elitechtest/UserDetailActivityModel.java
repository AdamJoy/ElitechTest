package com.example.chrisaj.elitechtest;

import android.app.Application;

import com.example.chrisaj.elitechtest.model.UserDetailModel;
import com.example.chrisaj.elitechtest.https.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class UserDetailActivityModel extends AndroidViewModel {
    private UserRepository mUserRepository;

    public UserDetailActivityModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository();
    }

    public MutableLiveData<UserDetailModel> getUserDetail(String userName) {
        return mUserRepository.getUserDetailMutableLiveData(userName);
    }
}
