package com.makemoney2023.online.reset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.makemoney2023.online.R;
import com.makemoney2023.online.api.Const;

public class ForgetPass extends AppCompatActivity {

    Button reset;
    EditText email;
    ProgressBar loading;

    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        reset = findViewById(R.id.reset_btn);
        email = findViewById(R.id.forget_email);
        loading = findViewById(R.id.loader_email_verifying);

        loading.setVisibility(View.GONE);

        reset.setOnClickListener(v -> {

            if (check) {
                check = false;
                loading.setVisibility(View.VISIBLE);
                email.setEnabled(false);
                String mail = email.getText().toString();
                resetPass(mail, getRand());

            } else {
                Toast.makeText(ForgetPass.this, "Already Resetting Please Wait....", Toast.LENGTH_SHORT).show();
            }


        });


        ImageView back = findViewById(R.id.tool_back);
        back.setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.tool_title);
        title.setText("Reset Password");


    }

    private void resetPass(String mail, String otp) {


        Log.d("checkkkkkkkk", "email : " + mail);
        Log.d("checkkkkkkkk", "otp : " + otp);

        StringRequest request = new StringRequest(Request.Method.POST, Const.emailVerify, response -> {
            loading.setVisibility(View.GONE);
            check = true;
            email.setEnabled(true);

            Toast.makeText(ForgetPass.this, response, Toast.LENGTH_SHORT).show();

            if (response.contains("sent")) {
                Intent intent = new Intent(ForgetPass.this, OtpVerify.class);
                intent.putExtra("email", mail);
                startActivity(intent);
            }


        }, error -> {
            Log.d("errrrrrrr", "resetPass: " + error.toString());
            check = true;
            email.setEnabled(true);
            loading.setVisibility(View.GONE);

        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> mp = new HashMap<>();
                mp.put("email", mail);
                mp.put("otp", otp);
                return mp;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ForgetPass.this);
        queue.add(request);


    }

    public static String getRand() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

}