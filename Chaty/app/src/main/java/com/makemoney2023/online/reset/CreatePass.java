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

import com.makemoney2023.online.R;
import com.makemoney2023.online.activity.LoginActivity;
import com.makemoney2023.online.api.Const;

public class CreatePass extends AppCompatActivity {

    Button update;
    EditText pass, confirmPass;
    ProgressBar loading;
    String email;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");


        update = findViewById(R.id.update_btn);
        pass = findViewById(R.id.new_pass);
        confirmPass = findViewById(R.id.confirm_pass);
        loading = findViewById(R.id.loader_update_verifying);

        loading.setVisibility(View.GONE);


        update.setOnClickListener(v -> {

            if (pass.getText().toString().equals(confirmPass.getText().toString())) {

                if (pass.getText().toString().length() < 4) {

                    Toast.makeText(CreatePass.this, "Minimum 4 digits Password", Toast.LENGTH_SHORT).show();

                } else {
                    if (check) {
                        check = false;
                        loading.setVisibility(View.VISIBLE);
                        pass.setEnabled(false);
                        confirmPass.setEnabled(false);

                        String ps = pass.getText().toString();
                        String cps = confirmPass.getText().toString();
                        String mail = email;

                        updatePass(ps, cps, mail);


                    } else {
                        Toast.makeText(CreatePass.this, "Already Verifying Please Wait....", Toast.LENGTH_SHORT).show();
                    }

                }

            } else {
                Toast.makeText(CreatePass.this, "Password Not Matched...", Toast.LENGTH_SHORT).show();
            }

        });


        ImageView back = findViewById(R.id.tool_back);
        back.setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.tool_title);
        title.setText("Update Password");

    }

    private void updatePass(String ps, String cps, String mail) {

        Log.d("checkkkkkkkk", "email : " + mail);
        Log.d("checkkkkkkkk", "otp : " + ps + " confirm : " + cps);

        StringRequest request = new StringRequest(Request.Method.POST, Const.changePass, response -> {

            loading.setVisibility(View.GONE);
            check = true;
            pass.setEnabled(true);
            confirmPass.setEnabled(true);

            Toast.makeText(CreatePass.this, response, Toast.LENGTH_SHORT).show();

            if (response.contains("created")) {
                Toast.makeText(CreatePass.this, "Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreatePass.this, LoginActivity.class));
                finish();
            }


        }, error -> {
            Log.d("errrrrrrr", "resetPass: " + error.toString());
            check = true;
            pass.setEnabled(true);
            confirmPass.setEnabled(true);
            loading.setVisibility(View.GONE);

        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> mp = new HashMap<>();
                mp.put("email", mail);
                mp.put("ps", ps);
                mp.put("cps", cps);
                return mp;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(CreatePass.this);
        queue.add(request);


    }
}