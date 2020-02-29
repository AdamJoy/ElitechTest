package com.example.chrisaj.elitechtest.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chrisaj.elitechtest.R;
import com.example.chrisaj.elitechtest.databinding.ItemUserListBinding;
import com.example.chrisaj.elitechtest.model.UserModel;

import java.util.ArrayList;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 *  人員列表 adapter
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private ArrayList<UserModel> mUserList;
    private ItemClick mItemClick;

    public UserListAdapter(ItemClick itemClick) {
        this.mItemClick = itemClick;   // 取得itemClick事件
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ItemUserListBinding userListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),    // ItemUserListBinding會自動生成
                        R.layout.item_user_list, viewGroup, false);       // 根據Layout名子  item_user_list 自動產生 ItemUserListBinding
        UserViewHolder holder = new UserViewHolder(userListItemBinding);


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

        holder.userListItemBinding.setItemclick(mItemClick);// 設定ItemClickListener給xml
        holder.userListItemBinding.setUser(currentUser);    // 設定user給xml
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            return mUserList.size();
        } else {
            return 0;
        }
    }
    // 新增多筆Item
    public void setUserList(ArrayList<UserModel> userList) {
        if(mUserList == null) {
            mUserList = new ArrayList<>();
        }
        this.mUserList = userList;
        notifyDataSetChanged();
    }

    // 插入多筆Item
    public void addUserList(ArrayList<UserModel> userList) {
        mUserList.addAll(userList);
        Log.d("TAG","插入資料數量 = " + userList.size());
        Log.d("TAG","插入資料數量_總數量 = " + mUserList.size());
        notifyDataSetChanged();
    }
    // 增加單一Item
    public void addSingleUserItem(UserModel userItem) {
        if(mUserList != null) {
            mUserList.add(userItem);
        }
    }
    // 取得目前最後一筆User_Id
    public int getLastUserId() {
        if(mUserList != null) {
            return mUserList.get(mUserList.size() - 1).getId();
        }
        return 0;
    }
    // ViewHolder
    class UserViewHolder extends RecyclerView.ViewHolder {
        private  ItemUserListBinding userListItemBinding;

        public UserViewHolder(@NonNull ItemUserListBinding userListItemBinding) {
            super(userListItemBinding.getRoot());
            this.userListItemBinding = userListItemBinding;
        }

        public void bind(ItemClick itemClick) {
            userListItemBinding.setItemclick(itemClick);
        }

    }


    public interface ItemClick {
        void onClicked(View view, UserModel user);
    }
}
