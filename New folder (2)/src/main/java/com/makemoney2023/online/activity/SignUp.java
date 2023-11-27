package com.makemoney2023.online.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.makemoney2023.online.R;
import com.makemoney2023.online.api.Urls;
import com.makemoney2023.online.helper.Pref;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {


    EditText email, password, username;
    ProgressBar progressBar;
    TextView login;
    Pref pref = new Pref(SignUp.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_pass);
        username = findViewById(R.id.signup_username);
        progressBar = findViewById(R.id.signup_loader);
        login = findViewById(R.id.goLogin);

        Button btn = findViewById(R.id.btn_signup);
        btn.setOnClickListener(v -> {


            String emails = email.getText().toString();
            String pass = password.getText().toString();
            String userName = username.getText().toString();

            String emailss = email.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (pass.length() >= 4) {

                if (userName.length() > 0) {

                    if (emailss.matches(emailPattern)) {

                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        createAc(emails, pass, userName);

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(SignUp.this, "Please Enter username", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(SignUp.this, "Password Must be greater than 4 digits", Toast.LENGTH_SHORT).show();
            }


        });


        login.setOnClickListener(v -> {

                    startActivity(new Intent(SignUp.this, LoginActivity.class));
                    finish();

                }
        );

    }

    private void createAc(String mail, String pass, String uname) {

        StringRequest request = new StringRequest(Request.Method.POST, Urls.signup, response -> {

            progressBar.setVisibility(View.GONE);

            if (response.contains("created")) {


                pref.setData(mail, "email");
                pref.setData(pass, "password");
                pref.setData(uname, "username");

                pref.setData("no", "login");

                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();

            } else {
                Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            pref.setData("no", "login");
            progressBar.setVisibility(View.GONE);
            Log.d(Urls.TAG, "onErrorResponse: " + error.toString());


        }) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> lg = new HashMap<>();
                lg.put("email", mail);
                lg.put("password", pass);
                lg.put("username", uname);

                return lg;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        queue.add(request);


    }
}