package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
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
    private CheckBox rememberSignInBox;


    private static final String LOGIN="LOGIN";
    private static final String PASSWORD="PASSWORD";
    private static final String BOX_STATE="BOX_STATE";




    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginView=(EditText)findViewById(R.id.editText7);
        passwordView=(EditText)findViewById(R.id.editText8);
        rememberSignInBox=findViewById(R.id.checkBoxSignIn);

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

        registrationButton=(Button)findViewById(R.id.registration);

        registrationButton.setOnClickListener((View view)->{
                Intent registrationIntent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(registrationIntent);
        });

        if(isRememberBoxChecked())
        {
            validateUser();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //we must truncate DB in case user wants to change his account
        UsersDB.truncate();
        UsersDB.truncateHost();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

     //my onClickListener(it is bounded in the UI Editor)

    public void checkInputLoginPassword(View view)
    {

        String loginSt=loginView.getText().toString();
        String passwordSt=passwordView.getText().toString();

        if(areWarningsExist())
            return;

        if(rememberSignInBox.isChecked())
        {

            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(LOGIN,loginSt);
            editor.putString(PASSWORD,passwordSt);
            editor.putBoolean(BOX_STATE,true);
            editor.apply();

        }

        validateUser();

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

    private boolean isRememberBoxChecked()
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean checkBoxState = sharedPref.getBoolean(BOX_STATE, false);
        rememberSignInBox.setChecked(checkBoxState);

        if(checkBoxState)
        {
            loginView.setText(sharedPref.getString(LOGIN,null));
            passwordView.setText(sharedPref.getString(PASSWORD,null));
            return true;
        }
        return false;
    }

    private void validateUser()
    {
        String loginSt=loginView.getText().toString();
        String passwordSt=passwordView.getText().toString();

        List<String> dataToSend=new ArrayList<>();
        dataToSend.add(loginSt);
        dataToSend.add(passwordSt);

        Log.d("Password",passwordSt);

        NetworkService service=new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

        Thread request=new Thread(new UserValidationRequestRunnable(service.getMySocket(),myHandler,dataToSend));
        Thread response=new Thread(new UserValidationResponseRunnable(service.getMySocket(),myHandler));
        request.start();
        response.start();
    }



}
