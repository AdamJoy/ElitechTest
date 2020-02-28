package com.example.chrisaj.elitechtest.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.chrisaj.elitechtest.R;
import com.example.chrisaj.elitechtest.databinding.ItemUserListBinding;
import com.example.chrisaj.elitechtest.model.UserModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *  User RecyclerView adapter
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private ArrayList<UserModel> mUserList;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ItemUserListBinding userListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),    // ItemUserListBinding會自動生成
                        R.layout.item_user_list, viewGroup, false);       // 根據Layout名子  item_user_list 自動產生 ItemUserListBinding
        return new UserViewHolder(userListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel currentUser = mUserList.get(position);

        if(currentUser.getType().equals("User")) {
            currentUser.setOrganize(false);
        } else {
            currentUser.setOrganize(true);
        }

        holder.userListItemBinding.setUser(currentUser);
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            return mUserList.size();
        } else {
            return 0;
        }
    }

    public void setUserList(ArrayList<UserModel> userList) {
        if(mUserList == null) {
            mUserList = new ArrayList<>();
        }
        this.mUserList = userList;
        notifyDataSetChanged();
    }

    public void addUserList(ArrayList<UserModel> userList) {
        mUserList.addAll(userList);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private  ItemUserListBinding userListItemBinding;

        public UserViewHolder(@NonNull ItemUserListBinding userListItemBinding) {
            super(userListItemBinding.getRoot());
            this.userListItemBinding = userListItemBinding;
        }
    }
}
