package com.makemoney2023.online.helper;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.makemoney2023.online.api.Urls;

import java.util.HashMap;
import java.util.Map;

public class Vly {


    public static void addCoins(String coins, Activity activity) {

        Pref pref = new Pref(activity);

        StringRequest request = new StringRequest(Request.Method.POST, Urls.update, response -> Log.d("updateddd", "addCoins: "), error -> Log.d(Urls.TAG, "onErrorResponse: " + error.toString())) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> cn = new HashMap<>();
                cn.put("type", "coins");
                cn.put("id", pref.getData("id"));
                cn.put("coins", coins);

                return cn;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);


    }

    public static void getCoins(String id, Activity activity) {

        Pref pref = new Pref(activity);

        StringRequest request = new StringRequest(Request.Method.POST, Urls.coins, response -> {

            if (response.contains("done")) {

                String cns = response.replaceAll("done", "");
                pref.setData(cns, "coins");

            }

        }, error -> Log.d(Urls.TAG, "onErrorResponse: " + error.toString())) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> cn = new HashMap<>();
                cn.put("id", id);
                return cn;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);


    }
}
