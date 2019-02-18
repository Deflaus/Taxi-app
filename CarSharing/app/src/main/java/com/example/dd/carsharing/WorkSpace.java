package com.example.dd.carsharing;

import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class WorkSpace {

    static Random random = new Random();

    static RadioGroup Answerss;

    static RadioButton answer1;
    static RadioButton answer2;
    static RadioButton answer3;
    static RadioButton answer4;


    public static int Buf;

    static TextView Questionn;

    static ArrayList<String> arOfQueAndAnsInGet = new ArrayList<>();
    static ArrayList<Integer> arQBuf = new ArrayList<>();
    static ArrayList<Integer> arABuf = new ArrayList<>();

    static int countQuestion = 1;

    static boolean isAnswer1Right = false;
    static boolean isAnswer2Right = false;
    static boolean isAnswer3Right = false;
    static boolean isAnswer4Right = false;







    public WorkSpace(){


    }


    public static void CaseWithAnswers() {


        Answerss = Answerss.findViewById(R.id.Answers);

        answer1 = answer1.findViewById(R.id.Answer1);
        answer2 = answer2.findViewById(R.id.Answer2);
        answer3 = answer3.findViewById(R.id.Answer3);
        answer4 = answer4.findViewById(R.id.Answer4);

        Questionn = Questionn.findViewById(R.id.Question);

        Answerss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public  void onCheckedChanged(RadioGroup group, int checkedId) {
                if (countQuestion <= 4) {
                    switch (checkedId) {
                        case R.id.Answer1:

                           MainActivity.addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            /*try {
                                Thread.sleep(1000); //Приостанавливает поток на 1 секунду
                            } catch (InterruptedException e) {

                            }*/
                            randomQuestion();

                            break;

                        case R.id.Answer2:
                            MainActivity.addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                        case R.id.Answer3:
                            MainActivity.addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                        case R.id.Answer4:
                          MainActivity.addItemToSheet();

                            countQuestion++;

                            chooseRightAnswer();

                            randomQuestion();
                            break;

                    }
                }
            }
        });





    }

    public static void randomQuestion() {
        //int Buf;
        do{
            Buf = random.nextInt(16);
        } while (isCheckQ(Buf) && isCheckInIndexOfQue(Buf));

        arQBuf.add(Buf);

        Questionn.setText(arOfQueAndAnsInGet.get(Buf));
        randomAnswers(Buf);
    }

    public static void randomAnswers(int Buf){
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

    public static boolean isCheckQ(int QBuf){
        for(int i = 0; i < arQBuf.size(); i++) if (arQBuf.get(i) == QBuf) return true;
        return false;
    }

    public static boolean isCheckA(int ABuf){
        for(int i = 0; i < arABuf.size(); i++) {
            if (arABuf.get(i) == ABuf) return true;
        }
        return false;
    }

    public static boolean isCheckInIndexOfQue(int Index){
        for (int i = 2; i < arOfQueAndAnsInGet.size()/5; i+=5) if (Index == i) return false;
        return true;
    }

    public static void chooseRightAnswer(){
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

    public void clearAnswers(){
        Answerss.clearCheck();
        answer1.setTextColor(Color.BLACK);
        answer2.setTextColor(Color.BLACK);
        answer3.setTextColor(Color.BLACK);
        answer4.setTextColor(Color.BLACK);
    }



}
