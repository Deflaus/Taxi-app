package com.example.dd.carsharing;

import android.graphics.Color;
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


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RadioGroup Answerss;

    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;

    TextView Questionn;

    ArrayList<String> arrr = new ArrayList<>();

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Answerss = findViewById(R.id.Answers);

        answer1 = findViewById(R.id.Answer1);
        answer2 = findViewById(R.id.Answer2);
        answer3 = findViewById(R.id.Answer3);
        answer4 = findViewById(R.id.Answer4);

        Questionn = findViewById(R.id.Question);

        try {
            parserXMLQuestion();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        randomQuestion();

        Answerss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.Answer1:
                        addItemToSheet();
                        randomQuestion();
                        answer1.setTextColor(Color.GREEN);
                        break;
                    case R.id.Answer2:
                        addItemToSheet();
                        randomQuestion();
                        answer2.setTextColor(Color.RED);
                        break;
                    case R.id.Answer3:
                        addItemToSheet();
                        randomQuestion();
                        answer3.setTextColor(Color.RED);
                        break;
                    case R.id.Answer4:
                        addItemToSheet();
                        randomQuestion();
                        answer4.setTextColor(Color.RED);
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

    public void parserXMLQuestion() throws XmlPullParserException, IOException {
        XmlPullParser parser = getResources().getXml(R.xml.questions);

        ArrayList<String> list = new ArrayList<>();

        while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG
                    && parser.getName().equals("question")) {
                list.add(parser.getAttributeValue(4) + "<#>"
                        + parser.getAttributeValue(0) + "<#>"
                        + parser.getAttributeValue(1) + "<#>"
                        + parser.getAttributeValue(2) + "<#>"
                        + parser.getAttributeValue(3));
            }
            parser.next();
        }



        for (int i = 0, a = 0; i < list.size()*5; i += 5, a++){
            String[] arr = list.get(a).split("<#>");
            arrr.add(arr[0]);
            arrr.add(arr[1]);
            arrr.add(arr[2]);
            arrr.add(arr[3]);
            arrr.add(arr[4]);
        }
    }

    public void randomQuestion() {

        int a = 1;
        while ((a % 5)!= 0) {
            a = random.nextInt(16);
        }

        Questionn.setText(arrr.get(a));
        answer1.setText(arrr.get(a+1));
        answer2.setText(arrr.get(a+2));
        answer3.setText(arrr.get(a+3));
        answer4.setText(arrr.get(a+4));
    }
}
