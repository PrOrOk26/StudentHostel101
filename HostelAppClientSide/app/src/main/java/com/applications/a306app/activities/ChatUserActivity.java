package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.applications.a306app.R;
import com.applications.a306app.model.CheckNewMessagesRequestRunnable;
import com.applications.a306app.model.CheckNewMessagesResponceRunnable;
import com.applications.a306app.model.GetAllConversationMessagesRequestRunnable;
import com.applications.a306app.model.GetAllConversationMessagesResponceRunnable;
import com.applications.a306app.model.GetAllUsersToMessageResponceRunnable;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.InsertConversationMessageRequestRunnable;
import com.applications.a306app.model.InsertConversationMessageResponceRunnable;
import com.applications.a306app.model.ListenForNewMessagesConversationRequestRunnable;
import com.applications.a306app.model.ListenForNewMessagesConversationResponceRunnable;
import com.applications.a306app.model.Message;
import com.applications.a306app.model.MessageDB;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.PrivateMessage;
import com.applications.a306app.model.RetrieveAllMessagesRequestRunnable;
import com.applications.a306app.model.RetrieveAllMessagesResponceRunnable;
import com.applications.a306app.model.SendMessageRequestRunnable;
import com.applications.a306app.model.SendMessageResponceRunnable;
import com.applications.a306app.model.UsersDB;
import com.applications.a306app.xml.XMLConstants;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatUserActivity extends AppCompatActivity {

    private RecyclerView myUserChatView;
    private Handler myUserChatViewHandler;
    private ChatUserAdapter myChatUserAdapter;
    private EditText insertMessage;

    private Button toSend;

    private Thread responce;
    private ListenForNewMessagesConversationResponceRunnable responceRunnable;
    private Socket myListeningSocket;

    private int conversationId;


    private List<PrivateMessage> messagesToShow;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        messagesToShow = new ArrayList<>();

        myUserChatView = findViewById(R.id.recyclerview_message_list);
        myUserChatView.setLayoutManager(new LinearLayoutManager(this));
        myChatUserAdapter = new ChatUserAdapter(this, messagesToShow);
        myUserChatView.setAdapter(myChatUserAdapter);

        toSend = findViewById(R.id.button_chatbox_send);
        insertMessage = findViewById(R.id.edittext_chatbox);

        conversationId = getIntent().getIntExtra(XMLConstants.TAGS_CONVERSATION,0);

        myUserChatViewHandler = new HandleServer() {
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case HandleServerResponseConstants.SETINSERTCONVERSATIONMESSAGE:{
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString(XMLConstants.TAGS_INSERT_MESSAGE_TO_CONVERSATION);
                        break;

                    }
                    case HandleServerResponseConstants.SETGETALLCONVERSATIONMESSAGES: {

                        messagesToShow.addAll(MessageDB.getAllConversationMessages(conversationId));
                        myChatUserAdapter.notifyDataSetChanged();
                        break;

                    }

                    case HandleServerResponseConstants.SETGETRECENTCONVERSATIONMESSAGES: {

                        messagesToShow.addAll(MessageDB.getAllConversationMessages(conversationId));
                        myChatUserAdapter.notifyDataSetChanged();
                        break;
                    }

                }
            }
        };

        //this method retrieves all messages from server and puts it into our local storage
        this.initializeMessageDB();

        //method listening to the new received messages
        this.sendUpdateRecentMessagesRequest();


        toSend.setOnClickListener((View view)->{
                List<PrivateMessage> messageToSend = new ArrayList<>();

                PrivateMessage buff = new PrivateMessage(insertMessage.getText().toString(), UsersDB.getHostUser(),conversationId);
                messageToSend.add(buff);

                MessageDB.addPrivateMessage(buff);
                messagesToShow.add(buff);
                myChatUserAdapter.notifyItemRangeInserted(myChatUserAdapter.getItemCount(), 1);

                NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                Thread request = new Thread(new InsertConversationMessageRequestRunnable(service.getMySocket(), messageToSend));
                Thread response = new Thread(new InsertConversationMessageResponceRunnable(service.getMySocket(), myUserChatViewHandler));
                request.start();
                response.start();

        });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            myListeningSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //we use it to send initial request to server in order to be received with new messages sent to chat
    private void sendUpdateRecentMessagesRequest()
    {
        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

        myListeningSocket=service.getMySocket();

        responceRunnable=new ListenForNewMessagesConversationResponceRunnable(myListeningSocket,myUserChatViewHandler);

        Thread request=new Thread(new ListenForNewMessagesConversationRequestRunnable(myListeningSocket,conversationId));

        responce=new Thread(responceRunnable);

        request.start();
        responce.start();
    }

    private void initializeMessageDB()
    {
        if(!MessageDB.isConversationInitialized(conversationId))
        {
            MessageDB.addInitializedConversation(conversationId);
            NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

            Thread request=new Thread(new GetAllConversationMessagesRequestRunnable(service.getMySocket(),conversationId));
            Thread response=new Thread(new GetAllConversationMessagesResponceRunnable(service.getMySocket(),myUserChatViewHandler));
            request.start();
            response.start();
        }
        else
        {
            messagesToShow.addAll(MessageDB.getAllConversationMessages(conversationId));
        }
    }
}
