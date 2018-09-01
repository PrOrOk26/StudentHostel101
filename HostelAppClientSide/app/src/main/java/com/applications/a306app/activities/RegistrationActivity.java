package com.applications.a306app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.applications.a306app.R;
import com.applications.a306app.model.HandleServer;
import com.applications.a306app.model.NetworkService;
import com.applications.a306app.model.UserRegisterRequestRunnable;
import com.applications.a306app.model.UserRegistrationResponceRunnable;
import com.applications.a306app.model.UserValidationRequestRunnable;
import com.applications.a306app.model.UserValidationResponseRunnable;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private Button registerButton;
    private TextInputEditText loginEdit;
    private TextInputEditText passwordEdit;
    private TextInputEditText confirmPasswordEdit;
    private TextInputEditText nameEdit;
    private TextInputEditText surnameEdit;

    private TextInputLayout loginLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private TextInputLayout nameLayout;
    private TextInputLayout surnameLayout;

    private static final int MIN_PASSWORD = 5;
    private static final int MAX_PASSWORD = 44;
    private static final int MAX_LOGIN = 15;
    private static final int MAX_NAME_SURNAME = 19;

    private Handler myHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerButton = (Button)findViewById(R.id.registerNewUser);

        loginEdit = (TextInputEditText)findViewById(R.id.Ilogin);
        passwordEdit = (TextInputEditText)findViewById(R.id.Ipassword);
        confirmPasswordEdit = (TextInputEditText)findViewById(R.id.IconfirmPassword);
        nameEdit = (TextInputEditText)findViewById(R.id.Iname);
        surnameEdit = (TextInputEditText)findViewById(R.id.ISurname);

        loginLayout = (TextInputLayout) findViewById(R.id.loginInput);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordInput);
        confirmPasswordLayout = (TextInputLayout) findViewById(R.id.confirmPassword);
        nameLayout = (TextInputLayout) findViewById(R.id.nameInput);
        surnameLayout = (TextInputLayout) findViewById(R.id.surnameInput);

        loginLayout.setHint("login");
        passwordLayout.setHint("password");
        confirmPasswordLayout.setHint("confirm password");
        nameLayout.setHint("name");
        surnameLayout.setHint("surname");


        myHandler = new HandleServer(){
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case HandleServerResponseConstants.SETREGISTRATIONMESSAGE:
                    {
                        Bundle bundle = msg.getData();
                        String testString = bundle.getString("registration");
                        Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };


        confirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password1 = passwordEdit.getText().toString();
                String password2 = confirmPasswordEdit.getText().toString();
                if(password1.length() < MIN_PASSWORD)
               {
                    passwordLayout.setError("at least 5 symbols needed");
               }
              if(password2.length() < MIN_PASSWORD)
              {
                    confirmPasswordLayout.setError("at least 5 symbols needed");
                    return;
                }
                if(password1.length() > MAX_PASSWORD)
                {
                    passwordLayout.setError("password is too long");
                }
                if(password2.length() > MAX_PASSWORD)
                {
                    confirmPasswordLayout.setError("password is too long");
                    return;
                }
                if(!password1.equals(password2))
                {
                    passwordLayout.setError("passwords do not match");
                    confirmPasswordLayout.setError("passwords do not match");
                }
                if(password1.equals(password2))
              {
                  passwordLayout.setError(null);
                  confirmPasswordLayout.setError(null);
              }
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password1 = passwordEdit.getText().toString();
                String password2 = confirmPasswordEdit.getText().toString();
                if(password1.length() < MIN_PASSWORD)
                {
                    passwordLayout.setError("at least 5 symbols needed");
                }
                if(password2.length() < MIN_PASSWORD)
                {
                    confirmPasswordLayout.setError("at least 5 symbols needed");
                    return;
                }
                if(password1.length() > MAX_PASSWORD)
                {
                    passwordLayout.setError("password is too long");
                }
                if(password2.length() > MAX_PASSWORD)
                {
                    confirmPasswordLayout.setError("password is too long");
                    return;
                }
                if(!password1.equals(password2))
                {
                    passwordLayout.setError("passwords do not match");
                    confirmPasswordLayout.setError("passwords do not match");
                }
                if(password1.equals(password2))
                {
                    passwordLayout.setError(null);
                    confirmPasswordLayout.setError(null);
                }
            }
        });

        loginEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                   String login = loginEdit.getText().toString();
                   if(login.length() > MAX_LOGIN)
                   {
                       loginLayout.setError("login is too long");
                   }
                   else
                   {
                       loginLayout.setError(null);
                   }
            }
        });

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                   String name = nameEdit.getText().toString();
                   if(name.length() > MAX_NAME_SURNAME)
                   {
                       nameLayout.setError("name is too long");
                   }
                   else
                   {
                       nameLayout.setError(null);
                   }
            }
        });

        surnameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String surname = surnameEdit.getText().toString();
                if(surname.length() > MAX_NAME_SURNAME)
                {
                    surnameLayout.setError("surname is too long");
                }
                else
                {
                    surnameLayout.setError(null);
                }
            }
        });


        registerButton.setOnClickListener((View view) -> {
                if(checkWarnings())
                    return;

                String loginSt = loginEdit.getText().toString();
                String passwordSt = passwordEdit.getText().toString();
                String name = nameEdit.getText().toString();
                String surname = surnameEdit.getText().toString();

                List<String> dataToSend = new ArrayList<>();
                dataToSend.add(name);
                dataToSend.add(surname);
                dataToSend.add(loginSt);
                dataToSend.add(passwordSt);


                NetworkService service = new NetworkService(HandleServer.HandleServerResponseConstants.DPORT, HandleServer.HandleServerResponseConstants.IP_ADDRESS);

                Thread request = new Thread(new UserRegisterRequestRunnable(service.getMySocket(),myHandler,dataToSend));
                Thread response = new Thread(new UserRegistrationResponceRunnable(service.getMySocket(),myHandler));
                request.start();
                response.start();
        });
    }

    private boolean checkWarnings()
    {
        if(nameEdit.getText().toString().length() == 0 || surnameEdit.getText().toString().length() == 0 ||
                loginEdit.getText().toString().length() == 0 || passwordEdit.getText().toString().length() == 0 || confirmPasswordEdit.getText().toString().length() == 0 )
        {
            Toast.makeText(getApplicationContext(),"Please,fill in the lines!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(loginLayout.getError() != null || passwordLayout.getError() != null || confirmPasswordLayout.getError() != null ||
                nameLayout.getError() != null || surnameLayout.getError() != null) {
            Toast.makeText(getApplicationContext(),"Check your data!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
