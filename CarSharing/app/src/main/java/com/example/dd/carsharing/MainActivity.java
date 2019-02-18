package com.example.dd.carsharing;

import android.graphics.Color;
import android.media.session.MediaSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    /*RadioGroup Answerss;

    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;


    public int Buf;

    TextView Questionn;

    ArrayList<String> arOfQueAndAnsInGet = new ArrayList<>();
    ArrayList<Integer> arQBuf = new ArrayList<>();
    ArrayList<Integer> arABuf = new ArrayList<>();

    int countQuestion = 1;

    boolean isAnswer1Right = false;
    boolean isAnswer2Right = false;
    boolean isAnswer3Right = false;
    boolean isAnswer4Right = false;

    Random random = new Random();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public MainActivity()  {
        getItems();
       // WorkSpace.randomQuestion();
        WorkSpace.CaseWithAnswers();

    }
    public void addItemToSheet(){
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

        RequestQueue queue = new RequestQueue(this);

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

    }
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

        GoogleTabs.sortQuestion(list);
    }


  /*      Answerss = findViewById(R.id.Answers);

        answer1 = findViewById(R.id.Answer1);
        answer2 = findViewById(R.id.Answer2);
        answer3 = findViewById(R.id.Answer3);
        answer4 = findViewById(R.id.Answer4);

        Questionn = findViewById(R.id.Question);

        getItems();
        //randomQuestion();
       // randomAnswers(Buf);

        Answerss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (countQuestion <= 4) {
                    switch (checkedId) {
                        case R.id.Answer1:

                            addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();


                            randomQuestion();

                            break;

                        case R.id.Answer2:
                            addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                        case R.id.Answer3:
                            addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                        case R.id.Answer4:
                            addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                    }
                }
            }
        });
    }   */



  /*  private void addItemToSheet(){
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

                parmas.put("action","addItem");
                parmas.put("answer",answer);

                return parmas;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }

    private void getItems() {

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

    }

    public void parseItems(String jsonResposnce) {

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

    public void sortQuestion(ArrayList<HashMap<String, String>> listochek){
        StringBuilder sum = new StringBuilder();
        for (HashMap<String, String> hash : listochek) {
            for (String current : hash.values()) {
                sum.append(current).append("<#>");

            }
        }
        String[] arr = sum.toString().split("<#>");
        arOfQueAndAnsInGet.addAll(Arrays.asList(arr));
    }

    private void randomQuestion() {
        //int Buf;
        do{
            Buf = random.nextInt(16);
        } while (isCheckQ(Buf) && isCheckInIndexOfQue(Buf));

        arQBuf.add(Buf);

        Questionn.setText(arOfQueAndAnsInGet.get(Buf));
        randomAnswers(Buf);
    }

    private void randomAnswers(int Buf){
        int ABuf;
        do {
            ABuf = rnd(-2, 2);
        } while ( ABuf != 0);

        if (ABuf == 1) isAnswer1Right = true;

        arABuf.add(ABuf);
        answer1.setText(arOfQueAndAnsInGet.get(Buf + ABuf));

        do{
            ABuf = rnd(-2, 2);
        } while (isCheckA(ABuf) && (ABuf != 0));

        if (ABuf == 1) isAnswer2Right = true;

        arABuf.add(ABuf);
        answer2.setText(arOfQueAndAnsInGet.get(Buf + ABuf));

        do{
            ABuf = rnd(-2, 2);
        } while (isCheckA(ABuf) && (ABuf != 0));

        if (ABuf == 1) isAnswer3Right = true;

        arABuf.add(ABuf);
        answer3.setText(arOfQueAndAnsInGet.get(Buf + ABuf));

        do{
            ABuf = rnd(-2, 2);
        } while (isCheckA(ABuf) && (ABuf != 0));

        if (ABuf == 1) isAnswer4Right = true;

        answer4.setText(arOfQueAndAnsInGet.get(Buf + ABuf));

        arABuf.clear();
    }

    public static int rnd(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private boolean isCheckQ(int QBuf){
        for(int i = 0; i < arQBuf.size(); i++) if (arQBuf.get(i) == QBuf) return true;
        return false;
    }

    private boolean isCheckA(int ABuf){
        for(int i = 0; i < arABuf.size(); i++) {
            if (arABuf.get(i) == ABuf) return true;
        }
        return false;
    }

    private boolean isCheckInIndexOfQue(int Index){
        for (int i = 2; i < arOfQueAndAnsInGet.size()/5; i+=5) if (Index == i) return false;
        return true;
    }

    private void chooseRightAnswer(){
        if (isAnswer1Right) {
            answer1.setTextColor(Color.GREEN);
            answer2.setTextColor(Color.RED);
            answer3.setTextColor(Color.RED);
            answer4.setTextColor(Color.RED);
            isAnswer1Right = false;
        }else if(isAnswer2Right){
            answer1.setTextColor(Color.RED);
            answer2.setTextColor(Color.GREEN);
            answer3.setTextColor(Color.RED);
            answer4.setTextColor(Color.RED);
            isAnswer1Right = false;
        }else if (isAnswer3Right){
            answer1.setTextColor(Color.RED);
            answer2.setTextColor(Color.RED);
            answer3.setTextColor(Color.GREEN);
            answer4.setTextColor(Color.RED);
            isAnswer1Right = false;
        }else if (isAnswer4Right){
            answer1.setTextColor(Color.RED);
            answer2.setTextColor(Color.RED);
            answer3.setTextColor(Color.RED);
            answer4.setTextColor(Color.GREEN);
            isAnswer1Right = false;
        }
    }

    private void clearAnswers(){
        Answerss.clearCheck();
        answer1.setTextColor(Color.BLACK);
        answer2.setTextColor(Color.BLACK);
        answer3.setTextColor(Color.BLACK);
        answer4.setTextColor(Color.BLACK);
    }    */

}
