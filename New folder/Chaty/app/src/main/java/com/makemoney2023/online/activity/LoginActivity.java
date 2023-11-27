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
import com.makemoney2023.online.MainActivity;
import com.makemoney2023.online.R;
import com.makemoney2023.online.api.Urls;
import com.makemoney2023.online.helper.Pref;
import com.makemoney2023.online.reset.ForgetPass;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    ProgressBar progressBar;
    TextView register,reset;

    Pref pref = new Pref(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        progressBar = findViewById(R.id.login_loader);
        register = findViewById(R.id.goSignIn);
        reset = findViewById(R.id.forgetPass);


        if (pref.getData("login").contains("yes")) {

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();

        } else {

        }


        Button btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(v -> {

            String num = email.getText().toString();
            String pass = password.getText().toString();

            String emails = email.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (emails.matches(emailPattern)) {
                progressBar.setVisibility(View.VISIBLE);
                login(num, pass);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUp.class)));

        reset.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPass.class)));


    }

    private void login(String email, String pass) {

        StringRequest request = new StringRequest(Request.Method.POST, Urls.login, response -> {

            progressBar.setVisibility(View.GONE);
            Log.d("chssssseeeee", "login: "+response);

            if (response.contains("user")) {


                pref.setData(email, "email");
                pref.setData(pass, "password");
                pref.setData(response.split("~~~")[1], "id");
                pref.setData(response.split("~~~")[2], "coins");
                pref.setData("yes", "login");

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();

            } else {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
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
                lg.put("email", email);
                lg.put("password", pass);

                return lg;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(request);

    }
}