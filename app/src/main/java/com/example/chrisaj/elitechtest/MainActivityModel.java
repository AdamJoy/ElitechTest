package com.example.chrisaj.elitechtest;

import android.app.Application;

import com.example.chrisaj.elitechtest.model.UserModel;
import com.example.chrisaj.elitechtest.model.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Subclass of androidViewModel for MainActivity
 */
public class MainActivityModel extends AndroidViewModel {

    private UserRepository mUserRepository;

    public MainActivityModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository();
    }

    public LiveData<List<UserModel>> getUserData(String userId) {
        return mUserRepository.getMutableLiveData(userId);
    }
}
