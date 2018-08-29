package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.applications.a306app.R;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.RetrieveBreadHistoryRequestRunnable;
import com.applications.a306app.model.RetrieveBreadHistoryResponceRunnable;
import com.applications.a306app.model.UserActivity;
import com.applications.a306app.model.UserActivityDB;
import com.applications.a306app.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

public class BreadHistoryActivity extends AppCompatActivity {

    private RecyclerView fullBreadHistory;
    private BreadViewAdapter myBreadAdapter;

    private List<UserActivity> myBreadBuff=new ArrayList<>();

    private NetworkService service;
    private Handler myHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bread_history);

        myHandler=new HandleServer();

        myBreadBuff=new ArrayList<>();

        fullBreadHistory=findViewById(R.id.breadHistoryRecyclerView);

        fullBreadHistory.setLayoutManager(new LinearLayoutManager(this));

        myBreadAdapter=new BreadViewAdapter(this,myBreadBuff);

        fullBreadHistory.setAdapter(myBreadAdapter);

        if(UsersDB.getHostUser().getGroup().length()>0&& UserActivityDB.getSize()==0) {
            service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);
            Thread requestBread = new Thread(new RetrieveBreadHistoryRequestRunnable(service.getMySocket(), HandleServer.HandleServerResponseConstants.BREAD_GARBAGE_REQUEST_TYPE_ALL));
            Thread responseBread = new Thread(new RetrieveBreadHistoryResponceRunnable(service.getMySocket(), myHandler));
            requestBread.start();
            responseBread.start();


            try {
                responseBread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            myBreadBuff.addAll(UserActivityDB.getAllActivities(HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE));
            myBreadAdapter.notifyDataSetChanged();




    }
}
