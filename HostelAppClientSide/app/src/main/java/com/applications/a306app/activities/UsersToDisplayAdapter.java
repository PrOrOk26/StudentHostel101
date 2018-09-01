package com.applications.a306app.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applications.a306app.R;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.User;
import com.applications.a306app.model.UserActivity;
import com.applications.a306app.utils.DateTimeUtils;

import java.util.List;

public class UsersToDisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<User> mUsersList;


    private OnViewHolderClickListener myListenerToChatUser;


    private static final int MESSAGE_USER_TO_DISPLAY = 1;


    public UsersToDisplayAdapter(Context context, List<User> mUserList,OnViewHolderClickListener myListenerToChatUser) {
        this.mContext = context;
        this.mUsersList = mUserList;
        this.myListenerToChatUser = myListenerToChatUser;
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {

        User myUserToDisplay = mUsersList.get(position);

        return MESSAGE_USER_TO_DISPLAY;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        Log.d("New view holder","holder");

        if (viewType == MESSAGE_USER_TO_DISPLAY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_choise, parent, false);
            return new UsersToDisplayAdapter.UserToShowHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User myUser = mUsersList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_USER_TO_DISPLAY: {
                ((UsersToDisplayAdapter.UserToShowHolder) holder).bind(myUser);
                break;
            }
        }
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.


    private class UserToShowHolder extends RecyclerView.ViewHolder {
        TextView userText;

        UserToShowHolder(View itemView) {
            super(itemView);

            userText = (TextView) itemView.findViewById(R.id.text_message_body);
        }

        void bind(User myUser)
        {
            userText.setText(myUser.getLogin());


            //when our ViewHolder is clicked,we must move to another activity to chat user
            userText.setOnClickListener((View v) -> myListenerToChatUser.onViewClick(myUser.getConversationsIds().get(0)));

        }
    }

}
