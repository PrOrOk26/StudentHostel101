package com.applications.a306app.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applications.a306app.R;
import com.applications.a306app.model.PrivateMessage;
import com.applications.a306app.model.UsersDB;
import com.applications.a306app.utils.DateTimeUtils;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

        private Context mContext;
        private List<PrivateMessage> mMessageList;


        public ChatUserAdapter(Context context, List<PrivateMessage> messageList) {
            mContext = context;
            mMessageList = messageList;
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        //Determines the appropriate ViewType according to the sender of the message.
        @Override
        public int getItemViewType(int position) {
            PrivateMessage message=mMessageList.get(position);

            if(message.getSender().getLogin().equals(UsersDB.getHostUser().getLogin()))
            {
                return VIEW_TYPE_MESSAGE_SENT;
            }
            else
                return VIEW_TYPE_MESSAGE_RECEIVED;
        }

        // Inflates the appropriate layout according to the ViewType.
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            Log.d("New view holder","holder");

            if (viewType == VIEW_TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_sent, parent, false);
                return new com.applications.a306app.activities.ChatUserAdapter.SentMessageHolder(view);
            } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_message_received, parent, false);
                return new com.applications.a306app.activities.ChatUserAdapter.ReceivedMessageHolder(view);
            }

            return null;
        }

        // Passes the message object to a ViewHolder so that the contents can be bound to UI.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PrivateMessage message = mMessageList.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((com.applications.a306app.activities.ChatUserAdapter.SentMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((com.applications.a306app.activities.ChatUserAdapter.ReceivedMessageHolder) holder).bind(message);
            }
        }

        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText;

            SentMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            }

            //this method binds view to the user's text
            void bind(PrivateMessage message) {
                messageText.setText(message.getMessage());
                timeText.setText(DateTimeUtils.getTimeString(message.getDate()));
            }
        }

        private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText;
            ImageView profileImage;

            ReceivedMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                timeText = (TextView) itemView.findViewById(R.id.text_message_time);
                nameText = (TextView) itemView.findViewById(R.id.text_message_name);
                profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
            }

            void bind(PrivateMessage message) {
                messageText.setText(message.getMessage());
                nameText.setText(message.getSender().getLogin());
                timeText.setText(DateTimeUtils.getTimeString(message.getDate()));
            }
        }

    }


