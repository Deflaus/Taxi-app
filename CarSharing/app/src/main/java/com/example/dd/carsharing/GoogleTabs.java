package com.example.dd.carsharing;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class GoogleTabs extends Context {

    public GoogleTabs(){

    }


    /*public static void addItemToSheet(){
        final String answer = WorkSpace.Questionn.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxSc4KPVqHtI9rkqXEkui8syGdxYMNM9GS1tvi9Dgy1N7JEb3AO/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("action","addItem");
                parmas.put("answer",answer);

                return parmas;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = RequestQueue(this);

       queue.add(stringRequest);


    }

    public  void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzBqkNr--4sNYa-dT-IsKGMDbeiA293Rjo5BJKVxSP3gyxxqjc/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
       queue.add(stringRequest);

    }*/

    public static void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String question = jo.getString("Question");
                String answer_1 = jo.getString("Answer1");
                String answer_2 = jo.getString("Answer2");
                String answer_3 = jo.getString("Answer3");
                String answer_4 = jo.getString("Answer4");


                HashMap<String, String> item = new HashMap<>();
                item.put("Question", question);
                item.put("Answer1", answer_1);
                item.put("Answer2", answer_2);
                item.put("Answer3", answer_3);
                item.put("Answer4", answer_4);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sortQuestion(list);
    }
    public static void sortQuestion(ArrayList<HashMap<String, String>> listochek){
        StringBuilder sum = new StringBuilder();
        for (HashMap<String, String> hash : listochek) {
            for (String current : hash.values()) {
                sum.append(current).append("<#>");

            }
        }
        String[] arr = sum.toString().split("<#>");
        WorkSpace.arOfQueAndAnsInGet.addAll(Arrays.asList(arr));
    }
}
