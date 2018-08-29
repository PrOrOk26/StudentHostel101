package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.applications.a306app.R;
import com.applications.a306app.model.GetAllUsersToMessageRequestRunnable;
import com.applications.a306app.model.GetAllUsersToMessageResponceRunnable;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.MessageDB;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.RetrieveAllMessagesRequestRunnable;
import com.applications.a306app.model.RetrieveAllMessagesResponceRunnable;
import com.applications.a306app.model.User;
import com.applications.a306app.model.UsersDB;
import com.applications.a306app.xml.XMLConstants;

import java.util.ArrayList;
import java.util.List;

public class ChooseUserToChatActivity extends AppCompatActivity implements OnViewHolderClickListener{

    private RecyclerView myUsersView;
    private Handler myHandler;
    private UsersToDisplayAdapter myAdapterToDisplay;

    private List<User> usersToDisplay;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_to_chat);

        usersToDisplay=new ArrayList<>();

        myUsersView=findViewById(R.id.recyclerview_choose_user);
        myAdapterToDisplay=new UsersToDisplayAdapter(this,usersToDisplay,this);

        myUsersView.setLayoutManager(new LinearLayoutManager(this));
        myUsersView.setAdapter(myAdapterToDisplay);

        myHandler=new HandleServer()
        {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HandleServerResponseConstants.SETGETALLUSERSINFO: {
                    Bundle bundle = msg.getData();

                    String testString = bundle.getString(XMLConstants.TAGS_GET_ALL_USERS_INFO);
                    if(testString.equals("success"))
                    {
                        usersToDisplay.clear();
                        usersToDisplay.addAll(UsersDB.getUsersData());
                        myAdapterToDisplay.notifyDataSetChanged();
                    }

                    break;

                }

            }
        }
    };

        getUsersFromServer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UsersDB.truncate();
    }

    private void getUsersFromServer()
    {
        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

        Thread request=new Thread(new GetAllUsersToMessageRequestRunnable(service.getMySocket()));
        Thread response=new Thread(new GetAllUsersToMessageResponceRunnable(service.getMySocket(),myHandler));
        request.start();
        response.start();
    }

    @Override
    public void onViewClick(int item)
    {
        Intent showUserChat=new Intent(getApplicationContext(),ChatUserActivity.class);
        showUserChat.putExtra(XMLConstants.TAGS_CONVERSATION,item);
        startActivity(showUserChat);
    }
}
