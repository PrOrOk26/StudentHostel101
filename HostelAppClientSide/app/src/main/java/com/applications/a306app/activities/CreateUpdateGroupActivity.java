package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applications.a306app.R;
import com.applications.a306app.model.AddUserToGroupRequestRunnable;
import com.applications.a306app.model.AddUserToGroupResponceRunnable;
import com.applications.a306app.model.CreateGroupRequestRunnable;
import com.applications.a306app.model.CreateGroupResponceRunnable;
import com.applications.a306app.model.DataAccessException;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.QuitGroupRequestRunnable;
import com.applications.a306app.model.QuitGroupResponceRunnable;
import com.applications.a306app.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateGroupActivity extends AppCompatActivity{

    private EditText createGroupEditText;
    private EditText addUserEditText;
    private Button createGroupButton;
    private Button addUserButton;
    private Button quitFromGroupButton;
    private Handler myHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_group);

        createGroupButton = (Button) findViewById(R.id.createGroupButton);
        addUserButton = (Button) findViewById(R.id.buttonAddUsersEdit);
        quitFromGroupButton = (Button) findViewById(R.id.buttonQuitFromGroup);

        createGroupEditText = (EditText) findViewById(R.id.createGroupEdit);
        addUserEditText = (EditText) findViewById(R.id.addUsersEdit);

        myHandler = new HandleServer() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HandleServerResponseConstants.SETGROUPCREATIONMESSAGE: {
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString("groupcreation");
                        Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case HandleServerResponseConstants.SETGROUPINVITATIONMESSAGE: {
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString("groupinsert");
                        Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case HandleServerResponseConstants.SETGROUPUSERQUITMESSAGE: {
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString("groupquit");
                        Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        };

        if (UsersDB.getHostUserGroup().length() > 0)
            setViewsVisible();


        createGroupButton.setOnClickListener((View view) -> {

                if(areWarningsCreateGroupExist())
                    return;

                String group = createGroupEditText.getText().toString();

                List<String> dataToSend = new ArrayList<>();

                dataToSend.add(group);

                Log.d("Group:", group);

                if (!group.equals(UsersDB.getHostUserGroup())) {
                    NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                    Thread request = new Thread(new CreateGroupRequestRunnable(service.getMySocket(), myHandler, dataToSend));
                    Thread response = new Thread(new CreateGroupResponceRunnable(service.getMySocket(), myHandler));
                    request.start();
                    response.start();
                } else
                    Toast.makeText(getApplicationContext(), "You are already in the group!", Toast.LENGTH_SHORT).show();

        });

        addUserButton.setOnClickListener((View view) -> {

                if(areWarningsAddUserExist())
                    return;

                try {

                    String userLogin = addUserEditText.getText().toString();

                    List<String> dataToSend = new ArrayList<>();
                    dataToSend.add(userLogin);

                    Log.d("User to add:", userLogin);

                    if (UsersDB.getHostUserGroup().length() > 0) {
                        NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                        Thread request = new Thread(new AddUserToGroupRequestRunnable(service.getMySocket(), myHandler, dataToSend));
                        Thread response = new Thread(new AddUserToGroupResponceRunnable(service.getMySocket(), myHandler));
                        request.start();
                        response.start();
                    } else throw new DataAccessException("You are not in a group yet!");
                } catch (DataAccessException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        });


        quitFromGroupButton.setOnClickListener((View view) -> {
                try {
                    if (UsersDB.getHostUserGroup().length() > 0) {
                        NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                        Thread request = new Thread(new QuitGroupRequestRunnable(service.getMySocket(), myHandler));
                        Thread response = new Thread(new QuitGroupResponceRunnable(service.getMySocket(), myHandler));
                        request.start();
                        response.start();
                    } else throw new DataAccessException("You are not in a group yet!");

                } catch (DataAccessException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        });
    }


    private void setViewsVisible() {
        if(UsersDB.getHostUserGroup().length() > 0) {
            TextView textView1 = findViewById(R.id.addFriendsText);
            textView1.setVisibility(View.VISIBLE);
            addUserButton.setVisibility(View.VISIBLE);
            quitFromGroupButton.setVisibility(View.VISIBLE);
            addUserEditText.setVisibility(View.VISIBLE);
        }
    }

    private boolean areWarningsCreateGroupExist()
    {
        if(createGroupEditText.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(),"Please,fill in the lines!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean areWarningsAddUserExist()
    {
        if(addUserEditText.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(),"Please,fill in the lines!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}

