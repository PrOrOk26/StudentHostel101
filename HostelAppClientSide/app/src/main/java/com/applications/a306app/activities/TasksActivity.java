package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Message;
import android.widget.Toast;

import com.applications.a306app.R;
import com.applications.a306app.model.CheckIfUserInGroupRequestRunnable;
import com.applications.a306app.model.CheckIfUserInGroupResponceRunnable;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.InsertBreadRequestRunnable;
import com.applications.a306app.model.InsertBreadResponceRunnable;
import com.applications.a306app.model.InsertGarbageRequestRunnable;
import com.applications.a306app.model.InsertGarbageResponceRunnable;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.RetrieveBreadHistoryRequestRunnable;
import com.applications.a306app.model.RetrieveBreadHistoryResponceRunnable;
import com.applications.a306app.model.RetrieveGarbageHistoryRequestRunnable;
import com.applications.a306app.model.RetrieveGarbageHistoryResponceRunnable;
import com.applications.a306app.model.UserActivity;
import com.applications.a306app.model.UserActivityDB;
import com.applications.a306app.model.UsersDB;
import com.applications.a306app.xml.XMLConstants;

import java.util.Date;
import java.util.LinkedList;

public class TasksActivity extends AppCompatActivity{

    private TextView breadCurrent;
    private TextView garbageCurrent;
    private TextView isInGroup;
    private HandleServer myHandler;
    private DrawerLayout mdrawerLayout;

    private RecyclerView breadRecylerView;
    private RecyclerView garbageRecyclerView;

    private BreadViewAdapter myBreadAdapter;
    private GarbageViewAdapter myGarbageAdapter;

    private Button insertBreadButton;
    private Button insertGarbageButton;
    private Button showBreadHistoryButton;
    private Button showGarbageHistoryButton;

    private AlertDialog.Builder myDialogBuider;

    private LinkedList<UserActivity> myGarbageBuff;
    private LinkedList<UserActivity> myBreadBuff;



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);


        UserActivityDB.clearDB();

        garbageCurrent=findViewById(R.id.garbage);
        breadCurrent=findViewById(R.id.bread);
        isInGroup=findViewById(R.id.isInGroupTextView);

        insertBreadButton=findViewById(R.id.BuyBreadButton);
        insertGarbageButton=findViewById(R.id.TakeGarbageOutButton);

        showBreadHistoryButton=findViewById(R.id.showBreadHistoryButton);
        showGarbageHistoryButton=findViewById(R.id.ShowGarbageHistoryButton);

        mdrawerLayout = findViewById(R.id.drawer_layout);

        //dialog when user tries to insert his action

        myDialogBuider=new AlertDialog.Builder(this);
        myDialogBuider.setTitle("Confirm action");

        myDialogBuider.setNegativeButton("Decline", (dialogInterface,i) -> dialogInterface.cancel());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_clear_all_black_24dp);

        myHandler=new HandleServer()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case HandleServerResponseConstants.SETGARBAGEMESSAGE:
                    {
                        Bundle bundle=msg.getData();
                        String testString=bundle.getString("garbage");
                        garbageCurrent.setText(testString);
                        break;
                    }
                    case HandleServerResponseConstants.SETBREADMESSAGE:
                    {
                        Bundle bundle=msg.getData();
                        String testString=bundle.getString("bread");
                        breadCurrent.setText(testString);
                        break;
                    }
                    case HandleServerResponseConstants.SETGROUPVALIDATIONMESSAGE:
                    {
                        Bundle bundle=msg.getData();
                        String testString=bundle.getString("groupvalidation");
                        isInGroup.setText(testString);
                        break;
                    }
                    case HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE:
                    {
                        Bundle bundle=msg.getData();
                        String testString=bundle.getString(XMLConstants.TAGS_REQUEST_BREAD_HISTORY);
                        myBreadAdapter.notifyDataSetChanged();
                        break;
                    }
                    case HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE:
                    {
                        Bundle bundle=msg.getData();
                        String testString=bundle.getString(XMLConstants.TAGS_REQUEST_GARBAGE_HISTORY);
                        myGarbageAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        };

        final NavigationView myView=findViewById(R.id.nav_view);

        myView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);

                mdrawerLayout.closeDrawers();

                switch(item.getTitle().toString())
                {
                    case "Manage group":
                    {
                        Intent groupCreation=new Intent(getApplicationContext(),CreateUpdateGroupActivity.class);
                        startActivity(groupCreation);
                        return true;
                    }
                    case "Open chat":
                    {
                        Intent chat=new Intent(getApplicationContext(),ChatActivity.class);
                        startActivity(chat);
                        return true;
                    }
                    case "Open conversations":
                    {
                        Intent conversations=new Intent(getApplicationContext(),ChooseUserToChatActivity.class);
                        startActivity(conversations);
                        return true;
                    }
                }

                return false;
            }
        });

        insertGarbageButton.setOnClickListener((View v) ->  {

                if(UsersDB.getHostUser().getGroup().length()<1)
                {
                    Toast.makeText(getApplicationContext(),"You are not in a group yet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final UserActivity activityToInsert=new UserActivity(HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE);


                myDialogBuider.setPositiveButton("Confirm", (dialogInterface,i)-> {

                        activityToInsert.setDate(new Date());
                        activityToInsert.setPerformerLogin(UsersDB.getHostUser().getLogin());
                        UserActivityDB.insertGarbage(activityToInsert);
                        myGarbageBuff.addLast(activityToInsert);
                        myGarbageBuff.pollFirst();

                        UserActivityDB.updateGarbageOrder();

                        myGarbageAdapter.notifyDataSetChanged();

                        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                        Thread request=new Thread(new InsertGarbageRequestRunnable(service.getMySocket(),activityToInsert));
                        Thread response=new Thread(new InsertGarbageResponceRunnable(service.getMySocket(),myHandler));
                        request.start();
                        response.start();
                        dialogInterface.cancel();

                });


                if(!UserActivityDB.isUserNextInGarbageQueue(UsersDB.getHostUser()))
                myDialogBuider.setMessage("You aren't next to take garbage,do you want to confirm action?");
                else
                    myDialogBuider.setMessage("You are next to take garbage,do you want to confirm action?");


                AlertDialog dialog=myDialogBuider.create();
                dialog.show();

        });

        insertBreadButton.setOnClickListener((View v) -> {

                if(UsersDB.getHostUser().getGroup().length()<1)
                {
                    Toast.makeText(getApplicationContext(),"You are not in a group yet!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final UserActivity activityToInsert=new UserActivity(HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE);


                myDialogBuider.setPositiveButton("Confirm",(dialogInterface,i) -> {

                        activityToInsert.setDate(new Date());
                        activityToInsert.setPerformerLogin(UsersDB.getHostUser().getLogin());
                        UserActivityDB.insertBread(activityToInsert);
                        myBreadBuff.addLast(activityToInsert);

                        myBreadBuff.pollFirst();

                        UserActivityDB.updateBreadOrder();

                        myBreadAdapter.notifyDataSetChanged();

                        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                        Thread request=new Thread(new InsertBreadRequestRunnable(service.getMySocket(),activityToInsert));
                        Thread response=new Thread(new InsertBreadResponceRunnable(service.getMySocket(),myHandler));
                        request.start();
                        response.start();
                        dialogInterface.cancel();

                });


                if(!UserActivityDB.isUserNextInBreadQueue(UsersDB.getHostUser()))
                    myDialogBuider.setMessage("You aren't next to buy bread,do you want to confirm action?");
                else
                    myDialogBuider.setMessage("You are next to buy bread,do you want to confirm action?");


                AlertDialog dialog=myDialogBuider.create();
                dialog.show();

        });

        showGarbageHistoryButton.setOnClickListener((View v)-> {
                   Intent goToGarbage=new Intent(getApplicationContext(),GarbageHistoryActivity.class);
                   startActivity(goToGarbage);

        });

        showBreadHistoryButton.setOnClickListener((View v)->{
                Intent goToBread=new Intent(getApplicationContext(),BreadHistoryActivity.class);
                startActivity(goToBread);

        });

        breadRecylerView=findViewById(R.id.breadRecyclerView);
        garbageRecyclerView=findViewById(R.id.garbageRecyclerView);

        breadRecylerView.setLayoutManager(new LinearLayoutManager(this));
        garbageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myBreadBuff=new LinkedList<>();
        myGarbageBuff=new LinkedList<>();

        myBreadAdapter=new BreadViewAdapter(this, myBreadBuff);
        myGarbageAdapter=new GarbageViewAdapter(this, myGarbageBuff);

        breadRecylerView.setAdapter(myBreadAdapter);
        garbageRecyclerView.setAdapter(myGarbageAdapter);



    }

    @Override
    protected void onStart() {

        super.onStart();
        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

        Thread request=new Thread(new CheckIfUserInGroupRequestRunnable(service.getMySocket(),myHandler));
        Thread response=new Thread(new CheckIfUserInGroupResponceRunnable(service.getMySocket(),myHandler));
        request.start();
        response.start();

        //we must check if user is in group.If he is,we make request to get all bread\garbage history

        try {
            response.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(UsersDB.getHostUser().getGroup().length()>0&&UserActivityDB.getSize()==0)
        {
            service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);
            Thread requestBread = new Thread(new RetrieveBreadHistoryRequestRunnable(service.getMySocket(), HandleServer.HandleServerResponseConstants.BREAD_GARBAGE_REQUEST_TYPE_ALL));
            Thread responseBread = new Thread(new RetrieveBreadHistoryResponceRunnable(service.getMySocket(), myHandler));
            requestBread.start();
            responseBread.start();


            service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);
            Thread requestGarbage = new Thread(new RetrieveGarbageHistoryRequestRunnable(service.getMySocket(), HandleServer.HandleServerResponseConstants.BREAD_GARBAGE_REQUEST_TYPE_ALL));
            Thread responseGarbage = new Thread(new RetrieveGarbageHistoryResponceRunnable(service.getMySocket(), myHandler));
            requestGarbage.start();
            responseGarbage.start();

            try {
                responseBread.join();
                responseGarbage.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myBreadBuff.addAll(UserActivityDB.getNactivities(5,HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE));
            myGarbageBuff.addAll(UserActivityDB.getNactivities(5,HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE));

            myGarbageAdapter.notifyDataSetChanged();
            myBreadAdapter.notifyDataSetChanged();

        }


        UserActivityDB.makeGarbageOrder();
        UserActivityDB.makeBreadOrder();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
