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

import com.applications.a306app.model.UserActivity;

import com.applications.a306app.utils.DateTimeUtils;

import java.util.List;


public class GarbageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<UserActivity> mUserActivityList;


    private static final int MESSAGE_GARBAGE=1;


    public GarbageViewAdapter(Context context, List<UserActivity> mUserList) {
        this.mContext = context;
        this.mUserActivityList = mUserList;
    }

    @Override
    public int getItemCount() {
        return mUserActivityList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {

        UserActivity myActivity=mUserActivityList.get(position);

       if(myActivity.getTYPE()== HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE) {
            return MESSAGE_GARBAGE;
        }
        return -1;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        Log.d("New view holder","holder");

        if (viewType == MESSAGE_GARBAGE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_garbage, parent, false);
            return new GarbageViewAdapter.GarbageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserActivity myActivity = mUserActivityList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_GARBAGE: {
                ((GarbageViewAdapter.GarbageHolder) holder).bind(myActivity);
                break;
            }
        }
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.


    private class GarbageHolder extends RecyclerView.ViewHolder {
        TextView userText, dateText;

        GarbageHolder(View itemView) {
            super(itemView);

            userText = (TextView) itemView.findViewById(R.id.text_message_body);
            dateText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(UserActivity myActivity)
        {
            userText.setText(myActivity.toString());
            dateText.setText(DateTimeUtils.convertDateTimeToString(myActivity.getDate()));
        }
    }
}
