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
import com.makemoney2023.online.R;
import com.makemoney2023.online.api.Const;

import java.util.HashMap;
import java.util.Map;

public class OtpVerify extends AppCompatActivity {

    Button verify;
    EditText otp;
    ProgressBar loading;
    String email;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");


        verify = findViewById(R.id.verify_btn);
        otp = findViewById(R.id.otp_verify);
        loading = findViewById(R.id.loader_otp_verifying);

        loading.setVisibility(View.GONE);

        verify.setOnClickListener(v -> {

            if (check) {
                check = false;
                loading.setVisibility(View.VISIBLE);
                otp.setEnabled(false);

                String code = otp.getText().toString();
                String mail = email;
                optVerify(code, mail);


            } else {
                Toast.makeText(OtpVerify.this, "Already Verifying Please Wait....", Toast.LENGTH_SHORT).show();
            }


        });


        ImageView back = findViewById(R.id.tool_back);
        back.setOnClickListener(v -> finish());
        TextView title = findViewById(R.id.tool_title);
        title.setText("OPT Verification");


    }

    private void optVerify(String code, String mail) {

        Log.d("checkkkkkkkk", "email : " + mail);
        Log.d("checkkkkkkkk", "otp : " + code);

        StringRequest request = new StringRequest(Request.Method.POST, Const.otpVerify, response -> {

            loading.setVisibility(View.GONE);
            check = true;
            otp.setEnabled(true);

            Toast.makeText(OtpVerify.this, response, Toast.LENGTH_SHORT).show();

            if (response.contains("verified")) {

                Intent intent = new Intent(OtpVerify.this, CreatePass.class);
                intent.putExtra("email", mail);
                startActivity(intent);
                finish();

            }


        }, error -> {
            Log.d("errrrrrrr", "resetPass: " + error.toString());
            check = true;
            otp.setEnabled(true);
            loading.setVisibility(View.GONE);

        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> mp = new HashMap<>();
                mp.put("email", mail);
                mp.put("otp", code);
                return mp;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(OtpVerify.this);
        queue.add(request);

    }
}