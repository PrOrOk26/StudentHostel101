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
import com.applications.a306app.model.RetrieveGarbageHistoryRequestRunnable;
import com.applications.a306app.model.RetrieveGarbageHistoryResponceRunnable;
import com.applications.a306app.model.UserActivity;
import com.applications.a306app.model.UserActivityDB;
import com.applications.a306app.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

public class GarbageHistoryActivity extends AppCompatActivity {

    private RecyclerView fullGarbageHistory;

    private GarbageViewAdapter myGarbageAdapter;

    private List<UserActivity> myGarbageBuff=new ArrayList<>();

    private NetworkService service;
    private Handler myHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_history);

        myHandler=new HandleServer();

        myGarbageBuff=new ArrayList<>();

        fullGarbageHistory =findViewById(R.id.garbageHistoryRecyclerView);

        fullGarbageHistory.setLayoutManager(new LinearLayoutManager(this));

        myGarbageAdapter =new GarbageViewAdapter(this,myGarbageBuff);

        fullGarbageHistory.setAdapter(myGarbageAdapter);

        if(UsersDB.getHostUser().getGroup().length()>0&& UserActivityDB.getSize()==0) {
            service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);
            Thread requestGarbage = new Thread(new RetrieveGarbageHistoryRequestRunnable(service.getMySocket(), HandleServer.HandleServerResponseConstants.BREAD_GARBAGE_REQUEST_TYPE_ALL));
            Thread responseGarbage = new Thread(new RetrieveGarbageHistoryResponceRunnable(service.getMySocket(), myHandler));
            requestGarbage.start();
            responseGarbage.start();


            try {
                responseGarbage.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        myGarbageBuff.addAll(UserActivityDB.getAllActivities(HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE));
        myGarbageAdapter.notifyDataSetChanged();




    }
}

