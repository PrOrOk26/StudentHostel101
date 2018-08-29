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
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.Message;
import com.applications.a306app.model.MessageDB;
import com.applications.a306app.model.NetworkService;
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


public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter myAdapter;
    private Button sendMessage;
    private EditText insertMessage;
    private Handler myHandler;


    private Thread responce;
    private CheckNewMessagesResponceRunnable responceRunnable;
    private Socket myListeningSocket;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView=findViewById(R.id.recyclerview_message_list);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new ChatAdapter(this, MessageDB.getMyChatMessageList());
        chatRecyclerView.setAdapter(myAdapter);

        sendMessage=findViewById(R.id.button_chatbox_send);
        insertMessage=findViewById(R.id.edittext_chatbox);


        myHandler=new HandleServer() {
            @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HandleServerResponseConstants.SETSENDCHATMESSAGE: {
                    Bundle bundle = msg.getData();
                    String testString = bundle.getString("sendchatmessage");
                   if(testString.equals(XMLConstants.TAGS_SEND_MAIN_CHAT_SUCCESS)) {
                       //set progress bar invisible
                   }

                }
                case HandleServerResponseConstants.SETRETRIEVEALLCHATMESSAGE: {

                  //      myAdapter=new ChatAdapter(getParent(), MessageChatDB.getMyChatMessageList());
                        myAdapter.notifyDataSetChanged();
                }

                case HandleServerResponseConstants.SETRETRIEVERESENTCHATMESSAGE: {

                  //  myAdapter=new ChatAdapter(getParent(), MessageChatDB.getMyChatMessageList());
                    myAdapter.notifyDataSetChanged();
                }

            }
        }
    };

        //this method retrieves all messages from server and puts it into our local storage
        this.initializeMessageDB();

        //method listening to the new received messages
        this.sendUpdateRecentMessagesRequest();


        sendMessage.setOnClickListener(  (View view)->{

                List<Message> messageToSend=new ArrayList<>();

                Message buff=new Message(insertMessage.getText().toString(),UsersDB.getHostUser());
                messageToSend.add(buff);

                MessageDB.addChatMessage(buff);
                myAdapter.notifyItemRangeInserted(myAdapter.getItemCount(),1);

                NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                Thread request=new Thread(new SendMessageRequestRunnable(service.getMySocket(),messageToSend));
                Thread response=new Thread(new SendMessageResponceRunnable(service.getMySocket(),myHandler));
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

        responceRunnable=new CheckNewMessagesResponceRunnable(myListeningSocket,myHandler);

        Thread request=new Thread(new CheckNewMessagesRequestRunnable(myListeningSocket));

        responce=new Thread(responceRunnable);

        request.start();
        responce.start();
    }

    private void initializeMessageDB()
    {
        if(!MessageDB.isChatInitialized())
        {
            NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

            Thread request=new Thread(new RetrieveAllMessagesRequestRunnable(service.getMySocket()));
            Thread response=new Thread(new RetrieveAllMessagesResponceRunnable(service.getMySocket(),myHandler));
            request.start();
            response.start();
        }
    }

}
