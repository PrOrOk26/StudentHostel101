package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.applications.a306app.R;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.UserValidationRequestRunnable;
import com.applications.a306app.model.UserValidationResponseRunnable;
import com.applications.a306app.model.UsersDB;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG="LoginActivity";
    private EditText loginView;
    private EditText passwordView;
    private Handler myHandler;
    private Button registrationButton;
    private CheckBox rememberMeCheckBox;


    private static final String CHECK_BOX_VALIDATION_ERROR="Error validating checkbox!";
    private static final String CHECK_BOX="Check_box";
    private static final String LOGIN="Login";
    private static final String PASSWORD="Password";
    private boolean isValidated = false;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrationButton=(Button)findViewById(R.id.registration);
        loginView=(EditText)findViewById(R.id.editText7);
        passwordView=(EditText)findViewById(R.id.editText8);
        rememberMeCheckBox=findViewById(R.id.checkBoxRememberMe);

        String[] loginPassword = getLoginPasswordPreference();

        if(loginPassword[0]!=null&&loginPassword[1]!=null) {
            loginView.setText(loginPassword[0]);
            passwordView.setText(loginPassword[1]);
        }

        registrationButton.setOnClickListener((View view)->{
            Intent registrationIntent=new Intent(getApplicationContext(),RegistrationActivity.class);
            startActivity(registrationIntent);
        });

        rememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                updateCheckBoxStateInPreference(b);

            }
        });

        myHandler=new HandleServer()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case HandleServerResponseConstants.SETVALIDATIONMESSAGE:
                        {
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString("validation");

                        if(testString.equals(HandleServerResponseConstants.TAGS_LOGIN_VALIDATION_RESPONCE_ALLOWED)) {

                            Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();

                            putLoginPasswordPreference(loginView.getText().toString(),passwordView.getText().toString());

                            Intent intent =new Intent(getApplicationContext(),TasksActivity.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        };

        checkRememberPreference();

        //TODO
        //BULLSHIT With intent when new activity starts.If I reopen an application,it passes over the login
        //but does not display any information about bread/garbage!!! Let's test it!

    }

    @Override
    protected void onStart() {
        super.onStart();

        //we must truncate DB in case user wants to change his account
        UsersDB.truncate();
        UsersDB.truncateHost();

        String[] loginPassword = getLoginPasswordPreference();

        if(loginPassword[0]!=null&&loginPassword[1]!=null) {
            loginView.setText(loginPassword[0]);
            passwordView.setText(loginPassword[1]);
        }


    }

     //my onClickListener(it is bounded in UI Editor)

    public void checkInputLoginPassword(View view)
    {

        if(areWarningsExist())
            return;

        String loginSt = loginView.getText().toString();
        String passwordSt = passwordView.getText().toString();

        List<String> dataToSend = new ArrayList<>();
        dataToSend.add(loginSt);
        dataToSend.add(passwordSt);

        Log.d("Password", passwordSt);

        NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

        Thread request = new Thread(new UserValidationRequestRunnable(service.getMySocket(), myHandler, dataToSend));
        Thread response = new Thread(new UserValidationResponseRunnable(service.getMySocket(), myHandler));
        request.start();
        response.start();

    }

    private boolean areWarningsExist()
    {
        if(loginView.getText().toString().length()==0||passwordView.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Please,fill in the lines!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //method used to check if box was checked
    private void checkRememberPreference()
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean isChecked = sharedPref.getBoolean(CHECK_BOX, false);
        rememberMeCheckBox.setChecked(isChecked);

        if(isChecked)
        {
            String loginSt = loginView.getText().toString();
            String passwordSt = passwordView.getText().toString();

            List<String> dataToSend = new ArrayList<>();
            dataToSend.add(loginSt);
            dataToSend.add(passwordSt);

            NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

            Thread request = new Thread(new UserValidationRequestRunnable(service.getMySocket(), myHandler, dataToSend));
            Thread response = new Thread(new UserValidationResponseRunnable(service.getMySocket(), myHandler));
            request.start();
            response.start();
        }

    }


    //this method is intended to write new preference when user is validated
    private void putLoginPasswordPreference(String loginSt, String passwordSt)
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN, loginSt);
        editor.putString(PASSWORD, passwordSt);
        editor.apply();
    }

    private String[] getLoginPasswordPreference()
    {
        String[] valuesToReturn=new String[2];

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        valuesToReturn[0] = sharedPref.getString(LOGIN,null);
        valuesToReturn[1] = sharedPref.getString(PASSWORD, null);

        return valuesToReturn;
    }


    //this is just to save checkbox state
    private void updateCheckBoxStateInPreference(boolean bState)
    {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(CHECK_BOX, bState);
        editor.apply();

    }



}
