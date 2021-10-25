package com.rcustodio.letreviewerprofedandgened;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneralEducationActivity extends Activity {

    GeneralDataBaseHelper mDatabaseHelper;

    Button deleteBtn,nextQ;

    TextView question, question_a_1,question_a_2,question_a_3,question_a_4,question_ca,curentQNumber;

    LinearLayout linearLayoutAnser_1,linearLayoutAnser_2,linearLayoutAnser_3,linearLayoutAnser_4;

    ImageView answerImage_1,answerImage_2,answerImage_3,answerImage_4;

    InterstitialAd mInterstitialAd;

    AdView mAdView;

    List<String> list = new ArrayList<>();

    Integer count,showNumber;
    boolean answered;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_education);

//
//        Here are my Admob codes for this LET reviewer:
//        App ID: ca-app-pub-2415030290687873~6165267492
//        Banner Ad ID: ca-app-pub-2415030290687873/3539104151
//        Interstitial Ad: ca-app-pub-2415030290687873/6362183395

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        ///  Banneer Ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //// Interstitial Ads
        mInterstitialAd = new InterstitialAd(GeneralEducationActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2415030290687873/8402873439");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });



        ////////////AdMob Ads//////////////



        ////

        SharedPreferences.Editor active_activity_editer = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE).edit();
        active_activity_editer.putInt("ACTIVE_ACTIVITY",2);
        active_activity_editer.apply();

        ///


        mDatabaseHelper = new GeneralDataBaseHelper(this);

        deleteBtn = findViewById(R.id.deleteButton);
        nextQ = findViewById(R.id.nextItemButton);

        curentQNumber = findViewById(R.id.currentNumebrTXVID);

        question = findViewById(R.id.QuestionTV);
        question_a_1 = findViewById(R.id.QuestionTV_A_1);
        question_a_2 = findViewById(R.id.QuestionTV_A_2);
        question_a_3 = findViewById(R.id.QuestionTV_A_3);
        question_a_4 = findViewById(R.id.QuestionTV_A_4);
        question_ca = findViewById(R.id.QuestionTV_CA);

        linearLayoutAnser_1 = findViewById(R.id.answer_ID_1);
        linearLayoutAnser_2 = findViewById(R.id.answer_ID_2);
        linearLayoutAnser_3 = findViewById(R.id.answer_ID_3);
        linearLayoutAnser_4 = findViewById(R.id.answer_ID_4);

        answerImage_1 = findViewById(R.id.QuestionIMV_A_1);
        answerImage_2 = findViewById(R.id.QuestionIMV_A_2);
        answerImage_3 = findViewById(R.id.QuestionIMV_A_3);
        answerImage_4 = findViewById(R.id.QuestionIMV_A_4);


        //Default Values
        count =0;
        showNumber=1;

        SharedPreferences.Editor editorcount = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
        editorcount.putInt("C_COUNT_GE",0);
        editorcount.apply();

        answered = false;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteData();
                SharedPreferences.Editor editor = getSharedPreferences("DATA_LOADED", MODE_PRIVATE).edit();
                editor.putString("LOADGE", "N");
                editor.apply();
                // ProfessionalDataBaseData gdb = new ProfessionalDataBaseData();

            }
        });



        nextQ.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

//
//                SharedPreferences prefs123 = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE);
//                Integer active_activity_number = prefs123.getInt("ACTIVE_ACTIVITY",0);//"0" is the default value.
//                Toast.makeText(GeneralEducationActivity.this,"Active Activity : "+ active_activity_number ,Toast.LENGTH_LONG).show();



                if(answered)
                {
                    answered = false;

                    linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_default_design);
                    linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_default_design);
                    linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_default_design);
                    linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_default_design);

                    linearLayoutAnser_1.setClickable(true);
                    linearLayoutAnser_2.setClickable(true);
                    linearLayoutAnser_3.setClickable(true);
                    linearLayoutAnser_4.setClickable(true);

                    answerImage_1.setVisibility(View.GONE);
                    answerImage_2.setVisibility(View.GONE);
                    answerImage_3.setVisibility(View.GONE);
                    answerImage_4.setVisibility(View.GONE);

                    count++;
                    if(count <= list.size()-1){


                        //// Admob Ads in every 10 questions
                        if(count==10 || count==20 || count==30 || count==40 || count==50 || count==60 || count==70 || count==80 || count==90 || count==100 || count==110 || count==120
                                || count==130 || count==140 || count==150 || count==160 || count==170 || count==180 || count==190)
                        {


                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                            ////////
                        }
                    //// Admob Ads in every 10 questions






                        String singleItem = list.get(count);


                        String[] splitedStrings = singleItem.split("###");

                        showNumber++;
                        curentQNumber.setText(String.valueOf(showNumber));


                        question.setText(splitedStrings[0]);
                        question_a_1.setText(splitedStrings[1]);
                        question_a_2.setText(splitedStrings[2]);
                        question_a_3.setText(splitedStrings[3]);
                        question_a_4.setText(splitedStrings[4]);
                        question_ca.setText(splitedStrings[5]);
                    }
                    else
                    {
                        SharedPreferences prefs = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                        Integer Correct_COUNT = prefs.getInt("C_COUNT_GE",0);//"0" is the default value.

                        Intent intent = new Intent(GeneralEducationActivity.this,ResultActivity.class);
                        startActivity(intent);
                        finish();

                        // Toast.makeText(ProfessionalEducationActivity.this,"Congrats You Finished All Questions!! And Number of Correct Answers : "+Correct_COUNT+" ,Out of 200 Questions!!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(GeneralEducationActivity.this,"Please Answer All The Questions, Thank you!",Toast.LENGTH_LONG).show();
                }




            }


        });
        
        ///
        ////// Linear Layout 01
        linearLayoutAnser_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answered = true;

                String pointNumber_1 = question_a_1.getText().toString();
                String pointNumber_2 = question_a_2.getText().toString();
                String pointNumber_3 = question_a_3.getText().toString();
                String pointNumber_4 = question_a_4.getText().toString();

                String numString_1 =  pointNumber_1.substring(0, 1);
                String numString_2 =  pointNumber_2.substring(0, 1);
                String numString_3 =  pointNumber_3.substring(0, 1);
                String numString_4 =  pointNumber_4.substring(0, 1);

                String CorrentAnswer = question_ca.getText().toString();

                if(CorrentAnswer.equals(numString_1))
                {
                    SharedPreferences prefs = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT = prefs.getInt("C_COUNT_GE",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT_GE",Correct_COUNT);
                    editor.apply();

                    linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_correct_design);
                    answerImage_1.setVisibility(View.VISIBLE);
                    answerImage_1.setImageResource(R.drawable.ic_check_circle_black_24dp);



                }
                else {

                    linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_wrong_desing);
                    answerImage_1.setVisibility(View.VISIBLE);
                    answerImage_1.setImageResource(R.drawable.ic_cancel_black_24dp);

                    if(CorrentAnswer.equals(numString_2)){
                        linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_2.setVisibility(View.VISIBLE);
                        answerImage_2.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_3)){
                        linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_3.setVisibility(View.VISIBLE);
                        answerImage_3.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_4)){
                        linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_4.setVisibility(View.VISIBLE);
                        answerImage_4.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }

                }

                linearLayoutAnser_1.setClickable(false);
                linearLayoutAnser_2.setClickable(false);
                linearLayoutAnser_3.setClickable(false);
                linearLayoutAnser_4.setClickable(false);


            }
        });

        ////// Linear Layout 02
        linearLayoutAnser_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answered = true;

                String pointNumber_1 = question_a_1.getText().toString();
                String pointNumber_2 = question_a_2.getText().toString();
                String pointNumber_3 = question_a_3.getText().toString();
                String pointNumber_4 = question_a_4.getText().toString();

                String numString_1 =  pointNumber_1.substring(0, 1);
                String numString_2 =  pointNumber_2.substring(0, 1);
                String numString_3 =  pointNumber_3.substring(0, 1);
                String numString_4 =  pointNumber_4.substring(0, 1);

                String CorrentAnswer = question_ca.getText().toString();

                if(CorrentAnswer.equals(numString_2))
                {

                    SharedPreferences prefs = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT = prefs.getInt("C_COUNT_GE",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT_GE",Correct_COUNT);
                    editor.apply();

                    linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_correct_design);
                    answerImage_2.setVisibility(View.VISIBLE);
                    answerImage_2.setImageResource(R.drawable.ic_check_circle_black_24dp);



                }
                else {

                    linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_wrong_desing);
                    answerImage_2.setVisibility(View.VISIBLE);
                    answerImage_2.setImageResource(R.drawable.ic_cancel_black_24dp);

                    if(CorrentAnswer.equals(numString_1)){
                        linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_1.setVisibility(View.VISIBLE);
                        answerImage_1.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_3)){
                        linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_3.setVisibility(View.VISIBLE);
                        answerImage_3.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_4)){
                        linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_4.setVisibility(View.VISIBLE);
                        answerImage_4.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }

                }

                linearLayoutAnser_1.setClickable(false);
                linearLayoutAnser_2.setClickable(false);
                linearLayoutAnser_3.setClickable(false);
                linearLayoutAnser_4.setClickable(false);


            }
        });

        ////// Linear Layout 03
        linearLayoutAnser_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answered = true;

                String pointNumber_1 = question_a_1.getText().toString();
                String pointNumber_2 = question_a_2.getText().toString();
                String pointNumber_3 = question_a_3.getText().toString();
                String pointNumber_4 = question_a_4.getText().toString();

                String numString_1 =  pointNumber_1.substring(0, 1);
                String numString_2 =  pointNumber_2.substring(0, 1);
                String numString_3 =  pointNumber_3.substring(0, 1);
                String numString_4 =  pointNumber_4.substring(0, 1);

                String CorrentAnswer = question_ca.getText().toString();

                if(CorrentAnswer.equals(numString_3))
                {
                    SharedPreferences prefs = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT = prefs.getInt("C_COUNT_GE",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT_GE",Correct_COUNT);
                    editor.apply();

                    linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_correct_design);
                    answerImage_3.setVisibility(View.VISIBLE);
                    answerImage_3.setImageResource(R.drawable.ic_check_circle_black_24dp);



                }
                else {

                    linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_wrong_desing);
                    answerImage_3.setVisibility(View.VISIBLE);
                    answerImage_3.setImageResource(R.drawable.ic_cancel_black_24dp);

                    if(CorrentAnswer.equals(numString_1)){
                        linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_1.setVisibility(View.VISIBLE);
                        answerImage_1.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_2)){
                        linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_2.setVisibility(View.VISIBLE);
                        answerImage_2.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_4)){
                        linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_4.setVisibility(View.VISIBLE);
                        answerImage_4.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }

                }

                linearLayoutAnser_1.setClickable(false);
                linearLayoutAnser_2.setClickable(false);
                linearLayoutAnser_3.setClickable(false);
                linearLayoutAnser_4.setClickable(false);


            }
        });

        ////// Linear Layout 04
        linearLayoutAnser_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answered = true;

                String pointNumber_1 = question_a_1.getText().toString();
                String pointNumber_2 = question_a_2.getText().toString();
                String pointNumber_3 = question_a_3.getText().toString();
                String pointNumber_4 = question_a_4.getText().toString();

                String numString_1 =  pointNumber_1.substring(0, 1);
                String numString_2 =  pointNumber_2.substring(0, 1);
                String numString_3 =  pointNumber_3.substring(0, 1);
                String numString_4 =  pointNumber_4.substring(0, 1);

                String CorrentAnswer = question_ca.getText().toString();

                if(CorrentAnswer.equals(numString_4))
                {
                    SharedPreferences prefs = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT = prefs.getInt("C_COUNT_GE",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT_GE",Correct_COUNT);
                    editor.apply();

                    linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_correct_design);
                    answerImage_4.setVisibility(View.VISIBLE);
                    answerImage_4.setImageResource(R.drawable.ic_check_circle_black_24dp);



                }
                else {

                    linearLayoutAnser_4.setBackgroundResource(R.drawable.ansfers_wrong_desing);
                    answerImage_4.setVisibility(View.VISIBLE);
                    answerImage_4.setImageResource(R.drawable.ic_cancel_black_24dp);

                    if(CorrentAnswer.equals(numString_1)){
                        linearLayoutAnser_1.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_1.setVisibility(View.VISIBLE);
                        answerImage_1.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_2)){
                        linearLayoutAnser_2.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_2.setVisibility(View.VISIBLE);
                        answerImage_2.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                    else if(CorrentAnswer.equals(numString_3)){
                        linearLayoutAnser_3.setBackgroundResource(R.drawable.ansfers_correct_design);
                        answerImage_3.setVisibility(View.VISIBLE);
                        answerImage_3.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }

                }

                linearLayoutAnser_1.setClickable(false);
                linearLayoutAnser_2.setClickable(false);
                linearLayoutAnser_3.setClickable(false);
                linearLayoutAnser_4.setClickable(false);



            }
        });



        ////check for data is installed at first open
        SharedPreferences prefs = getSharedPreferences("DATA_LOADED", MODE_PRIVATE);
        String STATUS = prefs.getString("LOADGE","N");//"N" is the default value.

        if(STATUS.equals("N")){


            AddData();


            SharedPreferences.Editor editor = getSharedPreferences("DATA_LOADED", MODE_PRIVATE).edit();
            editor.putString("LOADGE", "Y");
            editor.apply();

            getData();


        }
        else {

            getData();
        }
        /////////////////
        

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void getData() {


        list.clear();

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();


        String sql1 = "SELECT count(*) FROM generalEducation";
        Cursor cursor2 = db.rawQuery(sql1,null);
        cursor2.moveToFirst();
        int coun = cursor2.getInt(0);
        //Query date


        if(coun!=0){

            String sql = "SELECT * FROM generalEducation";

            Cursor cursor = db.rawQuery(sql,null);

            cursor.moveToFirst();

            String Qeustion="",Qeustion_A_1="",Qeustion_A_2="",Qeustion_A_3="",Qeustion_A_4="",Qeustion_CA="";

            while (cursor.isAfterLast() == false)
            {
                Qeustion = cursor.getString(1);
                Qeustion_A_1 = cursor.getString(2);
                Qeustion_A_2 = cursor.getString(3);
                Qeustion_A_3 = cursor.getString(4);
                Qeustion_A_4 = cursor.getString(5);
                Qeustion_CA = cursor.getString(6);

                list.add(Qeustion+"###"+Qeustion_A_1+"###"+Qeustion_A_2+"###"+Qeustion_A_3+"###"+Qeustion_A_4+"###"+Qeustion_CA);

                cursor.moveToNext();

            }


            Collections.shuffle(list);

            //Toast.makeText(GeneralEducationActivity.this,"Total Of : "+list.size(),Toast.LENGTH_LONG).show();


            /// First Time Load activity
            String singleItem = list.get(0);

            String[] splitedStrings = singleItem.split("###");

            curentQNumber.setText(String.valueOf(showNumber));

            question.setText(splitedStrings[0]);
            question_a_1.setText(splitedStrings[1]);
            question_a_2.setText(splitedStrings[2]);
            question_a_3.setText(splitedStrings[3]);
            question_a_4.setText(splitedStrings[4]);
            question_ca.setText(splitedStrings[5]);

        }
        else
            Toast.makeText(GeneralEducationActivity.this,"No data saved!",Toast.LENGTH_LONG).show();



    }


    public void deleteData() {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String sql = "DELETE FROM generalEducation";

        db.execSQL(sql);

    }

    ////// ADDING DATA
    ////// ADDING DATA
    ////// ADDING DATA
    ////// ADDING DATA
    ////// ADDING DATA
    // //// ADDING DATA

    public void AddData() {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        GeneralDataBaseData gdb  = new GeneralDataBaseData();



        String sql = "INSERT INTO generalEducation (question,answer1,answer2,answer3,answer4,answercol)"+
                "VALUES " +
                "('"+gdb.question_1+"','"+gdb.question_1_a_1+"','"+gdb.question_1_a_2+"','"+gdb.question_1_a_3+"','"+gdb.question_1_a_4+"','"+gdb.question_1_ca+"'),"+
                "('"+gdb.question_2+"','"+gdb.question_2_a_1+"','"+gdb.question_2_a_2+"','"+gdb.question_2_a_3+"','"+gdb.question_2_a_4+"','"+gdb.question_2_ca+"'),"+
                "('"+gdb.question_3+"','"+gdb.question_3_a_1+"','"+gdb.question_3_a_2+"','"+gdb.question_3_a_3+"','"+gdb.question_3_a_4+"','"+gdb.question_3_ca+"'),"+
                "('"+gdb.question_4+"','"+gdb.question_4_a_1+"','"+gdb.question_4_a_2+"','"+gdb.question_4_a_3+"','"+gdb.question_4_a_4+"','"+gdb.question_4_ca+"'),"+
                "('"+gdb.question_5+"','"+gdb.question_5_a_1+"','"+gdb.question_5_a_2+"','"+gdb.question_5_a_3+"','"+gdb.question_5_a_4+"','"+gdb.question_5_ca+"'),"+
                "('"+gdb.question_6+"','"+gdb.question_6_a_1+"','"+gdb.question_6_a_2+"','"+gdb.question_6_a_3+"','"+gdb.question_6_a_4+"','"+gdb.question_6_ca+"'),"+
                "('"+gdb.question_7+"','"+gdb.question_7_a_1+"','"+gdb.question_7_a_2+"','"+gdb.question_7_a_3+"','"+gdb.question_7_a_4+"','"+gdb.question_7_ca+"'),"+
                "('"+gdb.question_8+"','"+gdb.question_8_a_1+"','"+gdb.question_8_a_2+"','"+gdb.question_8_a_3+"','"+gdb.question_8_a_4+"','"+gdb.question_8_ca+"'),"+
                "('"+gdb.question_9+"','"+gdb.question_9_a_1+"','"+gdb.question_9_a_2+"','"+gdb.question_9_a_3+"','"+gdb.question_9_a_4+"','"+gdb.question_9_ca+"'),"+

                "('"+gdb.question_10+"','"+gdb.question_10_a_1+"','"+gdb.question_10_a_2+"','"+gdb.question_10_a_3+"','"+gdb.question_10_a_4+"','"+gdb.question_10_ca+"'),"+
                "('"+gdb.question_11+"','"+gdb.question_11_a_1+"','"+gdb.question_11_a_2+"','"+gdb.question_11_a_3+"','"+gdb.question_11_a_4+"','"+gdb.question_11_ca+"'),"+
                "('"+gdb.question_12+"','"+gdb.question_12_a_1+"','"+gdb.question_12_a_2+"','"+gdb.question_12_a_3+"','"+gdb.question_12_a_4+"','"+gdb.question_12_ca+"'),"+
                "('"+gdb.question_13+"','"+gdb.question_13_a_1+"','"+gdb.question_13_a_2+"','"+gdb.question_13_a_3+"','"+gdb.question_13_a_4+"','"+gdb.question_13_ca+"'),"+
                "('"+gdb.question_14+"','"+gdb.question_14_a_1+"','"+gdb.question_14_a_2+"','"+gdb.question_14_a_3+"','"+gdb.question_14_a_4+"','"+gdb.question_14_ca+"'),"+
                "('"+gdb.question_15+"','"+gdb.question_15_a_1+"','"+gdb.question_15_a_2+"','"+gdb.question_15_a_3+"','"+gdb.question_15_a_4+"','"+gdb.question_15_ca+"'),"+
                "('"+gdb.question_16+"','"+gdb.question_16_a_1+"','"+gdb.question_16_a_2+"','"+gdb.question_16_a_3+"','"+gdb.question_16_a_4+"','"+gdb.question_16_ca+"'),"+
                "('"+gdb.question_17+"','"+gdb.question_17_a_1+"','"+gdb.question_17_a_2+"','"+gdb.question_17_a_3+"','"+gdb.question_17_a_4+"','"+gdb.question_17_ca+"'),"+
                "('"+gdb.question_18+"','"+gdb.question_18_a_1+"','"+gdb.question_18_a_2+"','"+gdb.question_18_a_3+"','"+gdb.question_18_a_4+"','"+gdb.question_18_ca+"'),"+
                "('"+gdb.question_19+"','"+gdb.question_19_a_1+"','"+gdb.question_19_a_2+"','"+gdb.question_19_a_3+"','"+gdb.question_19_a_4+"','"+gdb.question_19_ca+"'),"+
                "('"+gdb.question_20+"','"+gdb.question_20_a_1+"','"+gdb.question_20_a_2+"','"+gdb.question_20_a_3+"','"+gdb.question_20_a_4+"','"+gdb.question_20_ca+"'),"+

                "('"+gdb.question_21+"','"+gdb.question_21_a_1+"','"+gdb.question_21_a_2+"','"+gdb.question_21_a_3+"','"+gdb.question_21_a_4+"','"+gdb.question_21_ca+"'),"+
                "('"+gdb.question_22+"','"+gdb.question_22_a_1+"','"+gdb.question_22_a_2+"','"+gdb.question_22_a_3+"','"+gdb.question_22_a_4+"','"+gdb.question_22_ca+"'),"+
                "('"+gdb.question_23+"','"+gdb.question_23_a_1+"','"+gdb.question_23_a_2+"','"+gdb.question_23_a_3+"','"+gdb.question_23_a_4+"','"+gdb.question_23_ca+"'),"+
                "('"+gdb.question_24+"','"+gdb.question_24_a_1+"','"+gdb.question_24_a_2+"','"+gdb.question_24_a_3+"','"+gdb.question_24_a_4+"','"+gdb.question_24_ca+"'),"+
                "('"+gdb.question_25+"','"+gdb.question_25_a_1+"','"+gdb.question_25_a_2+"','"+gdb.question_25_a_3+"','"+gdb.question_25_a_4+"','"+gdb.question_25_ca+"'),"+
                "('"+gdb.question_26+"','"+gdb.question_26_a_1+"','"+gdb.question_26_a_2+"','"+gdb.question_26_a_3+"','"+gdb.question_26_a_4+"','"+gdb.question_26_ca+"'),"+
                "('"+gdb.question_27+"','"+gdb.question_27_a_1+"','"+gdb.question_27_a_2+"','"+gdb.question_27_a_3+"','"+gdb.question_27_a_4+"','"+gdb.question_27_ca+"'),"+
                "('"+gdb.question_28+"','"+gdb.question_28_a_1+"','"+gdb.question_28_a_2+"','"+gdb.question_28_a_3+"','"+gdb.question_28_a_4+"','"+gdb.question_28_ca+"'),"+
                "('"+gdb.question_29+"','"+gdb.question_29_a_1+"','"+gdb.question_29_a_2+"','"+gdb.question_29_a_3+"','"+gdb.question_29_a_4+"','"+gdb.question_29_ca+"'),"+
                "('"+gdb.question_30+"','"+gdb.question_30_a_1+"','"+gdb.question_30_a_2+"','"+gdb.question_30_a_3+"','"+gdb.question_30_a_4+"','"+gdb.question_30_ca+"'),"+

                "('"+gdb.question_31+"','"+gdb.question_31_a_1+"','"+gdb.question_31_a_2+"','"+gdb.question_31_a_3+"','"+gdb.question_31_a_4+"','"+gdb.question_31_ca+"'),"+
                "('"+gdb.question_32+"','"+gdb.question_32_a_1+"','"+gdb.question_32_a_2+"','"+gdb.question_32_a_3+"','"+gdb.question_32_a_4+"','"+gdb.question_32_ca+"'),"+
                "('"+gdb.question_33+"','"+gdb.question_33_a_1+"','"+gdb.question_33_a_2+"','"+gdb.question_33_a_3+"','"+gdb.question_33_a_4+"','"+gdb.question_33_ca+"'),"+
                "('"+gdb.question_34+"','"+gdb.question_34_a_1+"','"+gdb.question_34_a_2+"','"+gdb.question_34_a_3+"','"+gdb.question_34_a_4+"','"+gdb.question_34_ca+"'),"+
                "('"+gdb.question_35+"','"+gdb.question_35_a_1+"','"+gdb.question_35_a_2+"','"+gdb.question_35_a_3+"','"+gdb.question_35_a_4+"','"+gdb.question_35_ca+"'),"+
                "('"+gdb.question_36+"','"+gdb.question_36_a_1+"','"+gdb.question_36_a_2+"','"+gdb.question_36_a_3+"','"+gdb.question_36_a_4+"','"+gdb.question_36_ca+"'),"+
                "('"+gdb.question_37+"','"+gdb.question_37_a_1+"','"+gdb.question_37_a_2+"','"+gdb.question_37_a_3+"','"+gdb.question_37_a_4+"','"+gdb.question_37_ca+"'),"+
                "('"+gdb.question_38+"','"+gdb.question_38_a_1+"','"+gdb.question_38_a_2+"','"+gdb.question_38_a_3+"','"+gdb.question_38_a_4+"','"+gdb.question_38_ca+"'),"+
                "('"+gdb.question_39+"','"+gdb.question_39_a_1+"','"+gdb.question_39_a_2+"','"+gdb.question_39_a_3+"','"+gdb.question_39_a_4+"','"+gdb.question_39_ca+"'),"+


                "('"+gdb.question_40+"','"+gdb.question_40_a_1+"','"+gdb.question_40_a_2+"','"+gdb.question_40_a_3+"','"+gdb.question_40_a_4+"','"+gdb.question_40_ca+"'),"+
                "('"+gdb.question_41+"','"+gdb.question_41_a_1+"','"+gdb.question_41_a_2+"','"+gdb.question_41_a_3+"','"+gdb.question_41_a_4+"','"+gdb.question_41_ca+"'),"+
                "('"+gdb.question_42+"','"+gdb.question_42_a_1+"','"+gdb.question_42_a_2+"','"+gdb.question_42_a_3+"','"+gdb.question_42_a_4+"','"+gdb.question_42_ca+"'),"+
                "('"+gdb.question_43+"','"+gdb.question_43_a_1+"','"+gdb.question_43_a_2+"','"+gdb.question_43_a_3+"','"+gdb.question_43_a_4+"','"+gdb.question_43_ca+"'),"+
                "('"+gdb.question_44+"','"+gdb.question_44_a_1+"','"+gdb.question_44_a_2+"','"+gdb.question_44_a_3+"','"+gdb.question_44_a_4+"','"+gdb.question_44_ca+"'),"+
                "('"+gdb.question_45+"','"+gdb.question_45_a_1+"','"+gdb.question_45_a_2+"','"+gdb.question_45_a_3+"','"+gdb.question_45_a_4+"','"+gdb.question_45_ca+"'),"+
                "('"+gdb.question_46+"','"+gdb.question_46_a_1+"','"+gdb.question_46_a_2+"','"+gdb.question_46_a_3+"','"+gdb.question_46_a_4+"','"+gdb.question_46_ca+"'),"+
                "('"+gdb.question_47+"','"+gdb.question_47_a_1+"','"+gdb.question_47_a_2+"','"+gdb.question_47_a_3+"','"+gdb.question_47_a_4+"','"+gdb.question_47_ca+"'),"+
                "('"+gdb.question_48+"','"+gdb.question_48_a_1+"','"+gdb.question_48_a_2+"','"+gdb.question_48_a_3+"','"+gdb.question_48_a_4+"','"+gdb.question_48_ca+"'),"+
                "('"+gdb.question_49+"','"+gdb.question_49_a_1+"','"+gdb.question_49_a_2+"','"+gdb.question_49_a_3+"','"+gdb.question_49_a_4+"','"+gdb.question_49_ca+"'),"+
                "('"+gdb.question_50+"','"+gdb.question_50_a_1+"','"+gdb.question_50_a_2+"','"+gdb.question_50_a_3+"','"+gdb.question_50_a_4+"','"+gdb.question_50_ca+"'),"+

                "('"+gdb.question_51+"','"+gdb.question_51_a_1+"','"+gdb.question_51_a_2+"','"+gdb.question_51_a_3+"','"+gdb.question_51_a_4+"','"+gdb.question_51_ca+"'),"+
                "('"+gdb.question_52+"','"+gdb.question_52_a_1+"','"+gdb.question_52_a_2+"','"+gdb.question_52_a_3+"','"+gdb.question_52_a_4+"','"+gdb.question_52_ca+"'),"+
                "('"+gdb.question_53+"','"+gdb.question_53_a_1+"','"+gdb.question_53_a_2+"','"+gdb.question_53_a_3+"','"+gdb.question_53_a_4+"','"+gdb.question_53_ca+"'),"+
                "('"+gdb.question_54+"','"+gdb.question_54_a_1+"','"+gdb.question_54_a_2+"','"+gdb.question_54_a_3+"','"+gdb.question_54_a_4+"','"+gdb.question_54_ca+"'),"+
                "('"+gdb.question_55+"','"+gdb.question_55_a_1+"','"+gdb.question_55_a_2+"','"+gdb.question_55_a_3+"','"+gdb.question_55_a_4+"','"+gdb.question_55_ca+"'),"+
                "('"+gdb.question_56+"','"+gdb.question_56_a_1+"','"+gdb.question_56_a_2+"','"+gdb.question_56_a_3+"','"+gdb.question_56_a_4+"','"+gdb.question_56_ca+"'),"+
                "('"+gdb.question_57+"','"+gdb.question_57_a_1+"','"+gdb.question_57_a_2+"','"+gdb.question_57_a_3+"','"+gdb.question_57_a_4+"','"+gdb.question_57_ca+"'),"+
                "('"+gdb.question_58+"','"+gdb.question_58_a_1+"','"+gdb.question_58_a_2+"','"+gdb.question_58_a_3+"','"+gdb.question_58_a_4+"','"+gdb.question_58_ca+"'),"+
                "('"+gdb.question_59+"','"+gdb.question_59_a_1+"','"+gdb.question_59_a_2+"','"+gdb.question_59_a_3+"','"+gdb.question_59_a_4+"','"+gdb.question_59_ca+"'),"+
                "('"+gdb.question_60+"','"+gdb.question_60_a_1+"','"+gdb.question_60_a_2+"','"+gdb.question_60_a_3+"','"+gdb.question_60_a_4+"','"+gdb.question_60_ca+"'),"+

                "('"+gdb.question_61+"','"+gdb.question_61_a_1+"','"+gdb.question_61_a_2+"','"+gdb.question_61_a_3+"','"+gdb.question_61_a_4+"','"+gdb.question_61_ca+"'),"+
                "('"+gdb.question_62+"','"+gdb.question_62_a_1+"','"+gdb.question_62_a_2+"','"+gdb.question_62_a_3+"','"+gdb.question_62_a_4+"','"+gdb.question_62_ca+"'),"+
                "('"+gdb.question_63+"','"+gdb.question_63_a_1+"','"+gdb.question_63_a_2+"','"+gdb.question_63_a_3+"','"+gdb.question_63_a_4+"','"+gdb.question_63_ca+"'),"+
                "('"+gdb.question_64+"','"+gdb.question_64_a_1+"','"+gdb.question_64_a_2+"','"+gdb.question_64_a_3+"','"+gdb.question_64_a_4+"','"+gdb.question_64_ca+"'),"+
                "('"+gdb.question_65+"','"+gdb.question_65_a_1+"','"+gdb.question_65_a_2+"','"+gdb.question_65_a_3+"','"+gdb.question_65_a_4+"','"+gdb.question_65_ca+"'),"+
                "('"+gdb.question_66+"','"+gdb.question_66_a_1+"','"+gdb.question_66_a_2+"','"+gdb.question_66_a_3+"','"+gdb.question_66_a_4+"','"+gdb.question_66_ca+"'),"+
                "('"+gdb.question_67+"','"+gdb.question_67_a_1+"','"+gdb.question_67_a_2+"','"+gdb.question_67_a_3+"','"+gdb.question_67_a_4+"','"+gdb.question_67_ca+"'),"+
                "('"+gdb.question_68+"','"+gdb.question_68_a_1+"','"+gdb.question_68_a_2+"','"+gdb.question_68_a_3+"','"+gdb.question_68_a_4+"','"+gdb.question_68_ca+"'),"+
                "('"+gdb.question_69+"','"+gdb.question_69_a_1+"','"+gdb.question_69_a_2+"','"+gdb.question_69_a_3+"','"+gdb.question_69_a_4+"','"+gdb.question_69_ca+"'),"+
                "('"+gdb.question_70+"','"+gdb.question_70_a_1+"','"+gdb.question_70_a_2+"','"+gdb.question_70_a_3+"','"+gdb.question_70_a_4+"','"+gdb.question_70_ca+"'),"+

                "('"+gdb.question_71+"','"+gdb.question_71_a_1+"','"+gdb.question_71_a_2+"','"+gdb.question_71_a_3+"','"+gdb.question_71_a_4+"','"+gdb.question_71_ca+"'),"+
                "('"+gdb.question_72+"','"+gdb.question_72_a_1+"','"+gdb.question_72_a_2+"','"+gdb.question_72_a_3+"','"+gdb.question_72_a_4+"','"+gdb.question_72_ca+"'),"+
                "('"+gdb.question_73+"','"+gdb.question_73_a_1+"','"+gdb.question_73_a_2+"','"+gdb.question_73_a_3+"','"+gdb.question_73_a_4+"','"+gdb.question_73_ca+"'),"+
                "('"+gdb.question_74+"','"+gdb.question_74_a_1+"','"+gdb.question_74_a_2+"','"+gdb.question_74_a_3+"','"+gdb.question_74_a_4+"','"+gdb.question_74_ca+"'),"+
                "('"+gdb.question_75+"','"+gdb.question_75_a_1+"','"+gdb.question_75_a_2+"','"+gdb.question_75_a_3+"','"+gdb.question_75_a_4+"','"+gdb.question_75_ca+"'),"+
                "('"+gdb.question_76+"','"+gdb.question_76_a_1+"','"+gdb.question_76_a_2+"','"+gdb.question_76_a_3+"','"+gdb.question_76_a_4+"','"+gdb.question_76_ca+"'),"+
                "('"+gdb.question_77+"','"+gdb.question_77_a_1+"','"+gdb.question_77_a_2+"','"+gdb.question_77_a_3+"','"+gdb.question_77_a_4+"','"+gdb.question_77_ca+"'),"+
                "('"+gdb.question_78+"','"+gdb.question_78_a_1+"','"+gdb.question_78_a_2+"','"+gdb.question_78_a_3+"','"+gdb.question_78_a_4+"','"+gdb.question_78_ca+"'),"+
                "('"+gdb.question_79+"','"+gdb.question_79_a_1+"','"+gdb.question_79_a_2+"','"+gdb.question_79_a_3+"','"+gdb.question_79_a_4+"','"+gdb.question_79_ca+"'),"+



                "('"+gdb.question_80+"','"+gdb.question_80_a_1+"','"+gdb.question_80_a_2+"','"+gdb.question_80_a_3+"','"+gdb.question_80_a_4+"','"+gdb.question_80_ca+"'),"+
                "('"+gdb.question_81+"','"+gdb.question_81_a_1+"','"+gdb.question_81_a_2+"','"+gdb.question_81_a_3+"','"+gdb.question_81_a_4+"','"+gdb.question_81_ca+"'),"+
                "('"+gdb.question_82+"','"+gdb.question_82_a_1+"','"+gdb.question_82_a_2+"','"+gdb.question_82_a_3+"','"+gdb.question_82_a_4+"','"+gdb.question_82_ca+"'),"+
                "('"+gdb.question_83+"','"+gdb.question_83_a_1+"','"+gdb.question_83_a_2+"','"+gdb.question_83_a_3+"','"+gdb.question_83_a_4+"','"+gdb.question_83_ca+"'),"+
                "('"+gdb.question_84+"','"+gdb.question_84_a_1+"','"+gdb.question_84_a_2+"','"+gdb.question_84_a_3+"','"+gdb.question_84_a_4+"','"+gdb.question_84_ca+"'),"+
                "('"+gdb.question_85+"','"+gdb.question_85_a_1+"','"+gdb.question_85_a_2+"','"+gdb.question_85_a_3+"','"+gdb.question_85_a_4+"','"+gdb.question_85_ca+"'),"+
                "('"+gdb.question_86+"','"+gdb.question_86_a_1+"','"+gdb.question_86_a_2+"','"+gdb.question_86_a_3+"','"+gdb.question_86_a_4+"','"+gdb.question_86_ca+"'),"+
                "('"+gdb.question_87+"','"+gdb.question_87_a_1+"','"+gdb.question_87_a_2+"','"+gdb.question_87_a_3+"','"+gdb.question_87_a_4+"','"+gdb.question_87_ca+"'),"+
                "('"+gdb.question_88+"','"+gdb.question_88_a_1+"','"+gdb.question_88_a_2+"','"+gdb.question_88_a_3+"','"+gdb.question_88_a_4+"','"+gdb.question_88_ca+"'),"+
                "('"+gdb.question_89+"','"+gdb.question_89_a_1+"','"+gdb.question_89_a_2+"','"+gdb.question_89_a_3+"','"+gdb.question_89_a_4+"','"+gdb.question_89_ca+"'),"+
                "('"+gdb.question_90+"','"+gdb.question_90_a_1+"','"+gdb.question_90_a_2+"','"+gdb.question_90_a_3+"','"+gdb.question_90_a_4+"','"+gdb.question_90_ca+"'),"+

                "('"+gdb.question_91+"','"+gdb.question_91_a_1+"','"+gdb.question_91_a_2+"','"+gdb.question_91_a_3+"','"+gdb.question_91_a_4+"','"+gdb.question_91_ca+"'),"+
                "('"+gdb.question_92+"','"+gdb.question_92_a_1+"','"+gdb.question_92_a_2+"','"+gdb.question_92_a_3+"','"+gdb.question_92_a_4+"','"+gdb.question_92_ca+"'),"+
                "('"+gdb.question_93+"','"+gdb.question_93_a_1+"','"+gdb.question_93_a_2+"','"+gdb.question_93_a_3+"','"+gdb.question_93_a_4+"','"+gdb.question_93_ca+"'),"+
                "('"+gdb.question_94+"','"+gdb.question_94_a_1+"','"+gdb.question_94_a_2+"','"+gdb.question_94_a_3+"','"+gdb.question_94_a_4+"','"+gdb.question_94_ca+"'),"+
                "('"+gdb.question_95+"','"+gdb.question_95_a_1+"','"+gdb.question_95_a_2+"','"+gdb.question_95_a_3+"','"+gdb.question_95_a_4+"','"+gdb.question_95_ca+"'),"+
                "('"+gdb.question_96+"','"+gdb.question_96_a_1+"','"+gdb.question_96_a_2+"','"+gdb.question_96_a_3+"','"+gdb.question_96_a_4+"','"+gdb.question_96_ca+"'),"+
                "('"+gdb.question_97+"','"+gdb.question_97_a_1+"','"+gdb.question_97_a_2+"','"+gdb.question_97_a_3+"','"+gdb.question_97_a_4+"','"+gdb.question_97_ca+"'),"+
                "('"+gdb.question_98+"','"+gdb.question_98_a_1+"','"+gdb.question_98_a_2+"','"+gdb.question_98_a_3+"','"+gdb.question_98_a_4+"','"+gdb.question_98_ca+"'),"+
                "('"+gdb.question_99+"','"+gdb.question_99_a_1+"','"+gdb.question_99_a_2+"','"+gdb.question_99_a_3+"','"+gdb.question_99_a_4+"','"+gdb.question_99_ca+"'),"+



                "('"+gdb.question_100+"','"+gdb.question_100_a_1+"','"+gdb.question_100_a_2+"','"+gdb.question_100_a_3+"','"+gdb.question_100_a_4+"','"+gdb.question_100_ca+"'),"+
                "('"+gdb.question_101+"','"+gdb.question_101_a_1+"','"+gdb.question_101_a_2+"','"+gdb.question_101_a_3+"','"+gdb.question_101_a_4+"','"+gdb.question_101_ca+"'),"+
                "('"+gdb.question_102+"','"+gdb.question_102_a_1+"','"+gdb.question_102_a_2+"','"+gdb.question_102_a_3+"','"+gdb.question_102_a_4+"','"+gdb.question_102_ca+"'),"+
                "('"+gdb.question_103+"','"+gdb.question_103_a_1+"','"+gdb.question_103_a_2+"','"+gdb.question_103_a_3+"','"+gdb.question_103_a_4+"','"+gdb.question_103_ca+"'),"+
                "('"+gdb.question_104+"','"+gdb.question_104_a_1+"','"+gdb.question_104_a_2+"','"+gdb.question_104_a_3+"','"+gdb.question_104_a_4+"','"+gdb.question_104_ca+"'),"+
                "('"+gdb.question_105+"','"+gdb.question_105_a_1+"','"+gdb.question_105_a_2+"','"+gdb.question_105_a_3+"','"+gdb.question_105_a_4+"','"+gdb.question_105_ca+"'),"+
                "('"+gdb.question_106+"','"+gdb.question_106_a_1+"','"+gdb.question_106_a_2+"','"+gdb.question_106_a_3+"','"+gdb.question_106_a_4+"','"+gdb.question_106_ca+"'),"+
                "('"+gdb.question_107+"','"+gdb.question_107_a_1+"','"+gdb.question_107_a_2+"','"+gdb.question_107_a_3+"','"+gdb.question_107_a_4+"','"+gdb.question_107_ca+"'),"+
                "('"+gdb.question_108+"','"+gdb.question_108_a_1+"','"+gdb.question_108_a_2+"','"+gdb.question_108_a_3+"','"+gdb.question_108_a_4+"','"+gdb.question_108_ca+"'),"+
                "('"+gdb.question_109+"','"+gdb.question_109_a_1+"','"+gdb.question_109_a_2+"','"+gdb.question_109_a_3+"','"+gdb.question_109_a_4+"','"+gdb.question_109_ca+"'),"+
                "('"+gdb.question_110+"','"+gdb.question_110_a_1+"','"+gdb.question_110_a_2+"','"+gdb.question_110_a_3+"','"+gdb.question_110_a_4+"','"+gdb.question_110_ca+"'),"+

                "('"+gdb.question_111+"','"+gdb.question_111_a_1+"','"+gdb.question_111_a_2+"','"+gdb.question_111_a_3+"','"+gdb.question_111_a_4+"','"+gdb.question_111_ca+"'),"+
                "('"+gdb.question_112+"','"+gdb.question_112_a_1+"','"+gdb.question_112_a_2+"','"+gdb.question_112_a_3+"','"+gdb.question_112_a_4+"','"+gdb.question_112_ca+"'),"+
                "('"+gdb.question_113+"','"+gdb.question_113_a_1+"','"+gdb.question_113_a_2+"','"+gdb.question_113_a_3+"','"+gdb.question_113_a_4+"','"+gdb.question_113_ca+"'),"+
                "('"+gdb.question_114+"','"+gdb.question_114_a_1+"','"+gdb.question_114_a_2+"','"+gdb.question_114_a_3+"','"+gdb.question_114_a_4+"','"+gdb.question_114_ca+"'),"+
                "('"+gdb.question_115+"','"+gdb.question_115_a_1+"','"+gdb.question_115_a_2+"','"+gdb.question_115_a_3+"','"+gdb.question_115_a_4+"','"+gdb.question_115_ca+"'),"+
                "('"+gdb.question_116+"','"+gdb.question_116_a_1+"','"+gdb.question_116_a_2+"','"+gdb.question_116_a_3+"','"+gdb.question_116_a_4+"','"+gdb.question_116_ca+"'),"+
                "('"+gdb.question_117+"','"+gdb.question_117_a_1+"','"+gdb.question_117_a_2+"','"+gdb.question_117_a_3+"','"+gdb.question_117_a_4+"','"+gdb.question_117_ca+"'),"+
                "('"+gdb.question_118+"','"+gdb.question_118_a_1+"','"+gdb.question_118_a_2+"','"+gdb.question_118_a_3+"','"+gdb.question_118_a_4+"','"+gdb.question_118_ca+"'),"+
                "('"+gdb.question_119+"','"+gdb.question_119_a_1+"','"+gdb.question_119_a_2+"','"+gdb.question_119_a_3+"','"+gdb.question_119_a_4+"','"+gdb.question_119_ca+"'),"+

                "('"+gdb.question_120+"','"+gdb.question_120_a_1+"','"+gdb.question_120_a_2+"','"+gdb.question_120_a_3+"','"+gdb.question_120_a_4+"','"+gdb.question_120_ca+"'),"+
                "('"+gdb.question_121+"','"+gdb.question_121_a_1+"','"+gdb.question_121_a_2+"','"+gdb.question_121_a_3+"','"+gdb.question_121_a_4+"','"+gdb.question_121_ca+"'),"+
                "('"+gdb.question_122+"','"+gdb.question_122_a_1+"','"+gdb.question_122_a_2+"','"+gdb.question_122_a_3+"','"+gdb.question_122_a_4+"','"+gdb.question_122_ca+"'),"+
                "('"+gdb.question_123+"','"+gdb.question_123_a_1+"','"+gdb.question_123_a_2+"','"+gdb.question_123_a_3+"','"+gdb.question_123_a_4+"','"+gdb.question_123_ca+"'),"+
                "('"+gdb.question_124+"','"+gdb.question_124_a_1+"','"+gdb.question_124_a_2+"','"+gdb.question_124_a_3+"','"+gdb.question_124_a_4+"','"+gdb.question_124_ca+"'),"+
                "('"+gdb.question_125+"','"+gdb.question_125_a_1+"','"+gdb.question_125_a_2+"','"+gdb.question_125_a_3+"','"+gdb.question_125_a_4+"','"+gdb.question_125_ca+"'),"+
                "('"+gdb.question_126+"','"+gdb.question_126_a_1+"','"+gdb.question_126_a_2+"','"+gdb.question_126_a_3+"','"+gdb.question_126_a_4+"','"+gdb.question_126_ca+"'),"+
                "('"+gdb.question_127+"','"+gdb.question_127_a_1+"','"+gdb.question_127_a_2+"','"+gdb.question_127_a_3+"','"+gdb.question_127_a_4+"','"+gdb.question_127_ca+"'),"+
                "('"+gdb.question_128+"','"+gdb.question_128_a_1+"','"+gdb.question_128_a_2+"','"+gdb.question_128_a_3+"','"+gdb.question_128_a_4+"','"+gdb.question_128_ca+"'),"+
                "('"+gdb.question_129+"','"+gdb.question_129_a_1+"','"+gdb.question_129_a_2+"','"+gdb.question_129_a_3+"','"+gdb.question_129_a_4+"','"+gdb.question_129_ca+"'),"+
                "('"+gdb.question_130+"','"+gdb.question_130_a_1+"','"+gdb.question_130_a_2+"','"+gdb.question_130_a_3+"','"+gdb.question_130_a_4+"','"+gdb.question_130_ca+"'),"+

                "('"+gdb.question_131+"','"+gdb.question_131_a_1+"','"+gdb.question_131_a_2+"','"+gdb.question_131_a_3+"','"+gdb.question_131_a_4+"','"+gdb.question_131_ca+"'),"+
                "('"+gdb.question_132+"','"+gdb.question_132_a_1+"','"+gdb.question_132_a_2+"','"+gdb.question_132_a_3+"','"+gdb.question_132_a_4+"','"+gdb.question_132_ca+"'),"+
                "('"+gdb.question_133+"','"+gdb.question_133_a_1+"','"+gdb.question_133_a_2+"','"+gdb.question_133_a_3+"','"+gdb.question_133_a_4+"','"+gdb.question_133_ca+"'),"+
                "('"+gdb.question_134+"','"+gdb.question_134_a_1+"','"+gdb.question_134_a_2+"','"+gdb.question_134_a_3+"','"+gdb.question_134_a_4+"','"+gdb.question_134_ca+"'),"+
                "('"+gdb.question_135+"','"+gdb.question_135_a_1+"','"+gdb.question_135_a_2+"','"+gdb.question_135_a_3+"','"+gdb.question_135_a_4+"','"+gdb.question_135_ca+"'),"+
                "('"+gdb.question_136+"','"+gdb.question_136_a_1+"','"+gdb.question_136_a_2+"','"+gdb.question_136_a_3+"','"+gdb.question_136_a_4+"','"+gdb.question_136_ca+"'),"+
                "('"+gdb.question_137+"','"+gdb.question_137_a_1+"','"+gdb.question_137_a_2+"','"+gdb.question_137_a_3+"','"+gdb.question_137_a_4+"','"+gdb.question_137_ca+"'),"+
                "('"+gdb.question_138+"','"+gdb.question_138_a_1+"','"+gdb.question_138_a_2+"','"+gdb.question_138_a_3+"','"+gdb.question_138_a_4+"','"+gdb.question_138_ca+"'),"+
                "('"+gdb.question_139+"','"+gdb.question_139_a_1+"','"+gdb.question_139_a_2+"','"+gdb.question_139_a_3+"','"+gdb.question_139_a_4+"','"+gdb.question_139_ca+"'),"+

                "('"+gdb.question_140+"','"+gdb.question_140_a_1+"','"+gdb.question_140_a_2+"','"+gdb.question_140_a_3+"','"+gdb.question_140_a_4+"','"+gdb.question_140_ca+"'),"+
                "('"+gdb.question_141+"','"+gdb.question_141_a_1+"','"+gdb.question_141_a_2+"','"+gdb.question_141_a_3+"','"+gdb.question_141_a_4+"','"+gdb.question_141_ca+"'),"+
                "('"+gdb.question_142+"','"+gdb.question_142_a_1+"','"+gdb.question_142_a_2+"','"+gdb.question_142_a_3+"','"+gdb.question_142_a_4+"','"+gdb.question_142_ca+"'),"+
                "('"+gdb.question_143+"','"+gdb.question_143_a_1+"','"+gdb.question_143_a_2+"','"+gdb.question_143_a_3+"','"+gdb.question_143_a_4+"','"+gdb.question_143_ca+"'),"+
                "('"+gdb.question_144+"','"+gdb.question_144_a_1+"','"+gdb.question_144_a_2+"','"+gdb.question_144_a_3+"','"+gdb.question_144_a_4+"','"+gdb.question_144_ca+"'),"+
                "('"+gdb.question_145+"','"+gdb.question_145_a_1+"','"+gdb.question_145_a_2+"','"+gdb.question_145_a_3+"','"+gdb.question_145_a_4+"','"+gdb.question_145_ca+"'),"+
                "('"+gdb.question_146+"','"+gdb.question_146_a_1+"','"+gdb.question_146_a_2+"','"+gdb.question_146_a_3+"','"+gdb.question_146_a_4+"','"+gdb.question_146_ca+"'),"+
                "('"+gdb.question_147+"','"+gdb.question_147_a_1+"','"+gdb.question_147_a_2+"','"+gdb.question_147_a_3+"','"+gdb.question_147_a_4+"','"+gdb.question_147_ca+"'),"+
                "('"+gdb.question_148+"','"+gdb.question_148_a_1+"','"+gdb.question_148_a_2+"','"+gdb.question_148_a_3+"','"+gdb.question_148_a_4+"','"+gdb.question_148_ca+"'),"+
                "('"+gdb.question_149+"','"+gdb.question_149_a_1+"','"+gdb.question_149_a_2+"','"+gdb.question_149_a_3+"','"+gdb.question_149_a_4+"','"+gdb.question_149_ca+"'),"+
                "('"+gdb.question_150+"','"+gdb.question_150_a_1+"','"+gdb.question_150_a_2+"','"+gdb.question_150_a_3+"','"+gdb.question_150_a_4+"','"+gdb.question_150_ca+"'),"+

                "('"+gdb.question_151+"','"+gdb.question_151_a_1+"','"+gdb.question_151_a_2+"','"+gdb.question_151_a_3+"','"+gdb.question_151_a_4+"','"+gdb.question_151_ca+"'),"+
                "('"+gdb.question_152+"','"+gdb.question_152_a_1+"','"+gdb.question_152_a_2+"','"+gdb.question_152_a_3+"','"+gdb.question_152_a_4+"','"+gdb.question_152_ca+"'),"+
                "('"+gdb.question_153+"','"+gdb.question_153_a_1+"','"+gdb.question_153_a_2+"','"+gdb.question_153_a_3+"','"+gdb.question_153_a_4+"','"+gdb.question_153_ca+"'),"+
                "('"+gdb.question_154+"','"+gdb.question_154_a_1+"','"+gdb.question_154_a_2+"','"+gdb.question_154_a_3+"','"+gdb.question_154_a_4+"','"+gdb.question_154_ca+"'),"+
                "('"+gdb.question_155+"','"+gdb.question_155_a_1+"','"+gdb.question_155_a_2+"','"+gdb.question_155_a_3+"','"+gdb.question_155_a_4+"','"+gdb.question_155_ca+"'),"+
                "('"+gdb.question_156+"','"+gdb.question_156_a_1+"','"+gdb.question_156_a_2+"','"+gdb.question_156_a_3+"','"+gdb.question_156_a_4+"','"+gdb.question_156_ca+"'),"+
                "('"+gdb.question_157+"','"+gdb.question_157_a_1+"','"+gdb.question_157_a_2+"','"+gdb.question_157_a_3+"','"+gdb.question_157_a_4+"','"+gdb.question_157_ca+"'),"+
                "('"+gdb.question_158+"','"+gdb.question_158_a_1+"','"+gdb.question_158_a_2+"','"+gdb.question_158_a_3+"','"+gdb.question_158_a_4+"','"+gdb.question_158_ca+"'),"+
                "('"+gdb.question_159+"','"+gdb.question_159_a_1+"','"+gdb.question_159_a_2+"','"+gdb.question_159_a_3+"','"+gdb.question_159_a_4+"','"+gdb.question_159_ca+"'),"+

                "('"+gdb.question_160+"','"+gdb.question_160_a_1+"','"+gdb.question_160_a_2+"','"+gdb.question_160_a_3+"','"+gdb.question_160_a_4+"','"+gdb.question_160_ca+"'),"+
                "('"+gdb.question_161+"','"+gdb.question_161_a_1+"','"+gdb.question_161_a_2+"','"+gdb.question_161_a_3+"','"+gdb.question_161_a_4+"','"+gdb.question_161_ca+"'),"+
                "('"+gdb.question_162+"','"+gdb.question_162_a_1+"','"+gdb.question_162_a_2+"','"+gdb.question_162_a_3+"','"+gdb.question_162_a_4+"','"+gdb.question_162_ca+"'),"+
                "('"+gdb.question_163+"','"+gdb.question_163_a_1+"','"+gdb.question_163_a_2+"','"+gdb.question_163_a_3+"','"+gdb.question_163_a_4+"','"+gdb.question_163_ca+"'),"+
                "('"+gdb.question_164+"','"+gdb.question_164_a_1+"','"+gdb.question_164_a_2+"','"+gdb.question_164_a_3+"','"+gdb.question_164_a_4+"','"+gdb.question_164_ca+"'),"+
                "('"+gdb.question_165+"','"+gdb.question_165_a_1+"','"+gdb.question_165_a_2+"','"+gdb.question_165_a_3+"','"+gdb.question_165_a_4+"','"+gdb.question_165_ca+"'),"+
                "('"+gdb.question_166+"','"+gdb.question_166_a_1+"','"+gdb.question_166_a_2+"','"+gdb.question_166_a_3+"','"+gdb.question_166_a_4+"','"+gdb.question_166_ca+"'),"+
                "('"+gdb.question_167+"','"+gdb.question_167_a_1+"','"+gdb.question_167_a_2+"','"+gdb.question_167_a_3+"','"+gdb.question_167_a_4+"','"+gdb.question_167_ca+"'),"+
                "('"+gdb.question_168+"','"+gdb.question_168_a_1+"','"+gdb.question_168_a_2+"','"+gdb.question_168_a_3+"','"+gdb.question_168_a_4+"','"+gdb.question_168_ca+"'),"+
                "('"+gdb.question_169+"','"+gdb.question_169_a_1+"','"+gdb.question_169_a_2+"','"+gdb.question_169_a_3+"','"+gdb.question_169_a_4+"','"+gdb.question_169_ca+"'),"+
                "('"+gdb.question_170+"','"+gdb.question_170_a_1+"','"+gdb.question_170_a_2+"','"+gdb.question_170_a_3+"','"+gdb.question_170_a_4+"','"+gdb.question_170_ca+"'),"+

                "('"+gdb.question_171+"','"+gdb.question_171_a_1+"','"+gdb.question_171_a_2+"','"+gdb.question_171_a_3+"','"+gdb.question_171_a_4+"','"+gdb.question_171_ca+"'),"+
                "('"+gdb.question_172+"','"+gdb.question_172_a_1+"','"+gdb.question_172_a_2+"','"+gdb.question_172_a_3+"','"+gdb.question_172_a_4+"','"+gdb.question_172_ca+"'),"+
                "('"+gdb.question_173+"','"+gdb.question_173_a_1+"','"+gdb.question_173_a_2+"','"+gdb.question_173_a_3+"','"+gdb.question_173_a_4+"','"+gdb.question_173_ca+"'),"+
                "('"+gdb.question_174+"','"+gdb.question_174_a_1+"','"+gdb.question_174_a_2+"','"+gdb.question_174_a_3+"','"+gdb.question_174_a_4+"','"+gdb.question_174_ca+"'),"+
                "('"+gdb.question_175+"','"+gdb.question_175_a_1+"','"+gdb.question_175_a_2+"','"+gdb.question_175_a_3+"','"+gdb.question_175_a_4+"','"+gdb.question_175_ca+"'),"+
                "('"+gdb.question_176+"','"+gdb.question_176_a_1+"','"+gdb.question_176_a_2+"','"+gdb.question_176_a_3+"','"+gdb.question_176_a_4+"','"+gdb.question_176_ca+"'),"+
                "('"+gdb.question_177+"','"+gdb.question_177_a_1+"','"+gdb.question_177_a_2+"','"+gdb.question_177_a_3+"','"+gdb.question_177_a_4+"','"+gdb.question_177_ca+"'),"+
                "('"+gdb.question_178+"','"+gdb.question_178_a_1+"','"+gdb.question_178_a_2+"','"+gdb.question_178_a_3+"','"+gdb.question_178_a_4+"','"+gdb.question_178_ca+"'),"+
                "('"+gdb.question_179+"','"+gdb.question_179_a_1+"','"+gdb.question_179_a_2+"','"+gdb.question_179_a_3+"','"+gdb.question_179_a_4+"','"+gdb.question_179_ca+"'),"+

                "('"+gdb.question_180+"','"+gdb.question_180_a_1+"','"+gdb.question_180_a_2+"','"+gdb.question_180_a_3+"','"+gdb.question_180_a_4+"','"+gdb.question_180_ca+"'),"+
                "('"+gdb.question_181+"','"+gdb.question_181_a_1+"','"+gdb.question_181_a_2+"','"+gdb.question_181_a_3+"','"+gdb.question_181_a_4+"','"+gdb.question_181_ca+"'),"+
                "('"+gdb.question_182+"','"+gdb.question_182_a_1+"','"+gdb.question_182_a_2+"','"+gdb.question_182_a_3+"','"+gdb.question_182_a_4+"','"+gdb.question_182_ca+"'),"+
                "('"+gdb.question_183+"','"+gdb.question_183_a_1+"','"+gdb.question_183_a_2+"','"+gdb.question_183_a_3+"','"+gdb.question_183_a_4+"','"+gdb.question_183_ca+"'),"+
                "('"+gdb.question_184+"','"+gdb.question_184_a_1+"','"+gdb.question_184_a_2+"','"+gdb.question_184_a_3+"','"+gdb.question_184_a_4+"','"+gdb.question_184_ca+"'),"+
                "('"+gdb.question_185+"','"+gdb.question_185_a_1+"','"+gdb.question_185_a_2+"','"+gdb.question_185_a_3+"','"+gdb.question_185_a_4+"','"+gdb.question_185_ca+"'),"+
                "('"+gdb.question_186+"','"+gdb.question_186_a_1+"','"+gdb.question_186_a_2+"','"+gdb.question_186_a_3+"','"+gdb.question_186_a_4+"','"+gdb.question_186_ca+"'),"+
                "('"+gdb.question_187+"','"+gdb.question_187_a_1+"','"+gdb.question_187_a_2+"','"+gdb.question_187_a_3+"','"+gdb.question_187_a_4+"','"+gdb.question_187_ca+"'),"+
                "('"+gdb.question_188+"','"+gdb.question_188_a_1+"','"+gdb.question_188_a_2+"','"+gdb.question_188_a_3+"','"+gdb.question_188_a_4+"','"+gdb.question_188_ca+"'),"+
                "('"+gdb.question_189+"','"+gdb.question_189_a_1+"','"+gdb.question_189_a_2+"','"+gdb.question_189_a_3+"','"+gdb.question_189_a_4+"','"+gdb.question_189_ca+"'),"+

                "('"+gdb.question_190+"','"+gdb.question_190_a_1+"','"+gdb.question_190_a_2+"','"+gdb.question_190_a_3+"','"+gdb.question_190_a_4+"','"+gdb.question_190_ca+"'),"+
                "('"+gdb.question_191+"','"+gdb.question_191_a_1+"','"+gdb.question_191_a_2+"','"+gdb.question_191_a_3+"','"+gdb.question_191_a_4+"','"+gdb.question_191_ca+"'),"+
                "('"+gdb.question_192+"','"+gdb.question_192_a_1+"','"+gdb.question_192_a_2+"','"+gdb.question_192_a_3+"','"+gdb.question_192_a_4+"','"+gdb.question_192_ca+"'),"+
                "('"+gdb.question_193+"','"+gdb.question_193_a_1+"','"+gdb.question_193_a_2+"','"+gdb.question_193_a_3+"','"+gdb.question_193_a_4+"','"+gdb.question_193_ca+"'),"+
                "('"+gdb.question_194+"','"+gdb.question_194_a_1+"','"+gdb.question_194_a_2+"','"+gdb.question_194_a_3+"','"+gdb.question_194_a_4+"','"+gdb.question_194_ca+"'),"+
                "('"+gdb.question_195+"','"+gdb.question_195_a_1+"','"+gdb.question_195_a_2+"','"+gdb.question_195_a_3+"','"+gdb.question_195_a_4+"','"+gdb.question_195_ca+"'),"+
                "('"+gdb.question_196+"','"+gdb.question_196_a_1+"','"+gdb.question_196_a_2+"','"+gdb.question_196_a_3+"','"+gdb.question_196_a_4+"','"+gdb.question_196_ca+"'),"+
                "('"+gdb.question_197+"','"+gdb.question_197_a_1+"','"+gdb.question_197_a_2+"','"+gdb.question_197_a_3+"','"+gdb.question_197_a_4+"','"+gdb.question_197_ca+"'),"+
                "('"+gdb.question_198+"','"+gdb.question_198_a_1+"','"+gdb.question_198_a_2+"','"+gdb.question_198_a_3+"','"+gdb.question_198_a_4+"','"+gdb.question_198_ca+"'),"+
                "('"+gdb.question_199+"','"+gdb.question_199_a_1+"','"+gdb.question_199_a_2+"','"+gdb.question_199_a_3+"','"+gdb.question_199_a_4+"','"+gdb.question_199_ca+"'),"+
                "('"+gdb.question_200+"','"+gdb.question_200_a_1+"','"+gdb.question_200_a_2+"','"+gdb.question_200_a_3+"','"+gdb.question_200_a_4+"','"+gdb.question_200_ca+"')";

        db.execSQL(sql);



        //Toast.makeText(ProfessionalEducationActivity.this,"Data Installed on to your phone.",Toast.LENGTH_LONG).show();



    }
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA





}
