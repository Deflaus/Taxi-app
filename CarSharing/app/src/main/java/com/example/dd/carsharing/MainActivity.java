package com.example.dd.carsharing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioGroup Answerss;

    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;

    TextView Questionn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Answerss = (RadioGroup)findViewById(R.id.Answers);

        answer1 = (RadioButton)findViewById(R.id.Answer1);
        answer2 = (RadioButton)findViewById(R.id.Answer2);
        answer3 = (RadioButton)findViewById(R.id.Answer3);
        answer4 = (RadioButton)findViewById(R.id.Answer4);

        Questionn = (TextView)findViewById(R.id.Question);

        Answerss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.Answer1:
                        Questionn.setTextColor(Color.RED);
                        addItemToSheet();
                        getItems();
                        break;
                    case R.id.Answer2:
                        Questionn.setTextColor(Color.GREEN);
                        addItemToSheet();
                        getItems();
                        break;
                    case R.id.Answer3:
                        Questionn.setTextColor(Color.BLUE);
                        addItemToSheet();
                        getItems();
                        break;
                    case R.id.Answer4:
                        Questionn.setTextColor(Color.YELLOW);
                        addItemToSheet();
                        getItems();
                        break;

                }
            }
        });
    }

    private void addItemToSheet(){
        final String answer = Questionn.getText().toString().trim();

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

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("answer",answer);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

    private void getItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbwytTCLG9ITpUb8gZ2gP3V9zZPh371vvnooZakX4VO_F2uGbYD-/exec",
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

    }

    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String answer_1 = jo.getString("Answer1");
                String answer_2 = jo.getString("Answer2");
                String answer_3 = jo.getString("Answer3");
                String answer_4 = jo.getString("Answer4");


                HashMap<String, String> item = new HashMap<>();
                item.put("Answer1", answer_1);
                item.put("Answer2", answer_2);
                item.put("Answer3", answer_3);
                item.put("Answer4", answer_4);

                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuilder sum = new StringBuilder();
        for (HashMap<String, String> hash : list) {
            for (String current : hash.values()) {
                sum.append(current).append("<#>");

            }
        }
        String[] arr = sum.toString().split("<#>");

        Questionn.setText(arr[2]);
    }

}
