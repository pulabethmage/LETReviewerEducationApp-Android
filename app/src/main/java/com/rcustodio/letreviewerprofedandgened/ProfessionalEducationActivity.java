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

public class ProfessionalEducationActivity extends Activity {

    DataBaseHelper mDatabaseHelper;

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
        setContentView(R.layout.activity_professional_education);

//        New Ad ID's:
//        App ID: ca-app-pub-2415030290687873~7281363454
//        Banner ID: ca-app-pub-2415030290687873/9683810296
//        Interstitial ID: ca-app-pub-2415030290687873/8402873439


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        /// Banner Ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //// Interstitial Ads
        mInterstitialAd = new InterstitialAd(ProfessionalEducationActivity.this);
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


        SharedPreferences.Editor active_activity_editer = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE).edit();
        active_activity_editer.putInt("ACTIVE_ACTIVITY",1);
        active_activity_editer.apply();

        ///



        mDatabaseHelper = new DataBaseHelper(this);

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
        editorcount.putInt("C_COUNT",0);
        editorcount.apply();

        answered = false;


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteData();
                SharedPreferences.Editor editor = getSharedPreferences("DATA_LOADED", MODE_PRIVATE).edit();
                editor.putString("LOAD", "N");
                editor.apply();
                // ProfessionalDataBaseData pdb = new ProfessionalDataBaseData();

            }
        });


        nextQ.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View view) {

//                SharedPreferences prefs123 = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE);
//                Integer active_activity_number = prefs123.getInt("ACTIVE_ACTIVITY",0);//"0" is the default value.
//                Toast.makeText(ProfessionalEducationActivity.this,"Active Activity : "+ active_activity_number ,Toast.LENGTH_LONG).show();

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
                        Integer Correct_COUNT = prefs.getInt("C_COUNT",0);//"0" is the default value.

                        Intent intent = new Intent(ProfessionalEducationActivity.this,ResultActivity.class);
                        startActivity(intent);
                        finish();

                        // Toast.makeText(ProfessionalEducationActivity.this,"Congrats You Finished All Questions!! And Number of Correct Answers : "+Correct_COUNT+" ,Out of 200 Questions!!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(ProfessionalEducationActivity.this,"Please Answer All The Questions, Thank you!",Toast.LENGTH_LONG).show();
                }




            }


        });

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
                    Integer Correct_COUNT = prefs.getInt("C_COUNT",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT",Correct_COUNT);
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
                    Integer Correct_COUNT = prefs.getInt("C_COUNT",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT",Correct_COUNT);
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
                    Integer Correct_COUNT = prefs.getInt("C_COUNT",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT",Correct_COUNT);
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
                    Integer Correct_COUNT = prefs.getInt("C_COUNT",0);//"0" is the default value.

                    Correct_COUNT++;
                    SharedPreferences.Editor editor = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE).edit();
                    editor.putInt("C_COUNT",Correct_COUNT);
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
        String STATUS = prefs.getString("LOAD","N");//"N" is the default value.

        if(STATUS.equals("N")){


            AddData();


            SharedPreferences.Editor editor = getSharedPreferences("DATA_LOADED", MODE_PRIVATE).edit();
            editor.putString("LOAD", "Y");
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


        String sql1 = "SELECT count(*) FROM professionalEducation";
        Cursor cursor2 = db.rawQuery(sql1,null);
        cursor2.moveToFirst();
        int coun = cursor2.getInt(0);
        //Query date


        if(coun!=0){

            String sql = "SELECT * FROM professionalEducation";

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

          //  Toast.makeText(ProfessionalEducationActivity.this,"Total Of : "+list.size(),Toast.LENGTH_LONG).show();


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
            Toast.makeText(ProfessionalEducationActivity.this,"No data saved!",Toast.LENGTH_LONG).show();



    }


    public void deleteData() {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String sql = "DELETE FROM professionalEducation";

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

        ProfessionalDataBaseData pdb  = new ProfessionalDataBaseData();



        String sql = "INSERT INTO professionalEducation (question,answer1,answer2,answer3,answer4,answercol)"+
                "VALUES " +
                "('"+pdb.question_1+"','"+pdb.question_1_a_1+"','"+pdb.question_1_a_2+"','"+pdb.question_1_a_3+"','"+pdb.question_1_a_4+"','"+pdb.question_1_ca+"'),"+
                "('"+pdb.question_2+"','"+pdb.question_2_a_1+"','"+pdb.question_2_a_2+"','"+pdb.question_2_a_3+"','"+pdb.question_2_a_4+"','"+pdb.question_2_ca+"'),"+
                "('"+pdb.question_3+"','"+pdb.question_3_a_1+"','"+pdb.question_3_a_2+"','"+pdb.question_3_a_3+"','"+pdb.question_3_a_4+"','"+pdb.question_3_ca+"'),"+
                "('"+pdb.question_4+"','"+pdb.question_4_a_1+"','"+pdb.question_4_a_2+"','"+pdb.question_4_a_3+"','"+pdb.question_4_a_4+"','"+pdb.question_4_ca+"'),"+
                "('"+pdb.question_5+"','"+pdb.question_5_a_1+"','"+pdb.question_5_a_2+"','"+pdb.question_5_a_3+"','"+pdb.question_5_a_4+"','"+pdb.question_5_ca+"'),"+
                "('"+pdb.question_6+"','"+pdb.question_6_a_1+"','"+pdb.question_6_a_2+"','"+pdb.question_6_a_3+"','"+pdb.question_6_a_4+"','"+pdb.question_6_ca+"'),"+
                "('"+pdb.question_7+"','"+pdb.question_7_a_1+"','"+pdb.question_7_a_2+"','"+pdb.question_7_a_3+"','"+pdb.question_7_a_4+"','"+pdb.question_7_ca+"'),"+
                "('"+pdb.question_8+"','"+pdb.question_8_a_1+"','"+pdb.question_8_a_2+"','"+pdb.question_8_a_3+"','"+pdb.question_8_a_4+"','"+pdb.question_8_ca+"'),"+
                "('"+pdb.question_9+"','"+pdb.question_9_a_1+"','"+pdb.question_9_a_2+"','"+pdb.question_9_a_3+"','"+pdb.question_9_a_4+"','"+pdb.question_9_ca+"'),"+

                "('"+pdb.question_10+"','"+pdb.question_10_a_1+"','"+pdb.question_10_a_2+"','"+pdb.question_10_a_3+"','"+pdb.question_10_a_4+"','"+pdb.question_10_ca+"'),"+
                "('"+pdb.question_11+"','"+pdb.question_11_a_1+"','"+pdb.question_11_a_2+"','"+pdb.question_11_a_3+"','"+pdb.question_11_a_4+"','"+pdb.question_11_ca+"'),"+
                "('"+pdb.question_12+"','"+pdb.question_12_a_1+"','"+pdb.question_12_a_2+"','"+pdb.question_12_a_3+"','"+pdb.question_12_a_4+"','"+pdb.question_12_ca+"'),"+
                "('"+pdb.question_13+"','"+pdb.question_13_a_1+"','"+pdb.question_13_a_2+"','"+pdb.question_13_a_3+"','"+pdb.question_13_a_4+"','"+pdb.question_13_ca+"'),"+
                "('"+pdb.question_14+"','"+pdb.question_14_a_1+"','"+pdb.question_14_a_2+"','"+pdb.question_14_a_3+"','"+pdb.question_14_a_4+"','"+pdb.question_14_ca+"'),"+
                "('"+pdb.question_15+"','"+pdb.question_15_a_1+"','"+pdb.question_15_a_2+"','"+pdb.question_15_a_3+"','"+pdb.question_15_a_4+"','"+pdb.question_15_ca+"'),"+
                "('"+pdb.question_16+"','"+pdb.question_16_a_1+"','"+pdb.question_16_a_2+"','"+pdb.question_16_a_3+"','"+pdb.question_16_a_4+"','"+pdb.question_16_ca+"'),"+
                "('"+pdb.question_17+"','"+pdb.question_17_a_1+"','"+pdb.question_17_a_2+"','"+pdb.question_17_a_3+"','"+pdb.question_17_a_4+"','"+pdb.question_17_ca+"'),"+
                "('"+pdb.question_18+"','"+pdb.question_18_a_1+"','"+pdb.question_18_a_2+"','"+pdb.question_18_a_3+"','"+pdb.question_18_a_4+"','"+pdb.question_18_ca+"'),"+
                "('"+pdb.question_19+"','"+pdb.question_19_a_1+"','"+pdb.question_19_a_2+"','"+pdb.question_19_a_3+"','"+pdb.question_19_a_4+"','"+pdb.question_19_ca+"'),"+
                "('"+pdb.question_20+"','"+pdb.question_20_a_1+"','"+pdb.question_20_a_2+"','"+pdb.question_20_a_3+"','"+pdb.question_20_a_4+"','"+pdb.question_20_ca+"'),"+

                "('"+pdb.question_21+"','"+pdb.question_21_a_1+"','"+pdb.question_21_a_2+"','"+pdb.question_21_a_3+"','"+pdb.question_21_a_4+"','"+pdb.question_21_ca+"'),"+
                "('"+pdb.question_22+"','"+pdb.question_22_a_1+"','"+pdb.question_22_a_2+"','"+pdb.question_22_a_3+"','"+pdb.question_22_a_4+"','"+pdb.question_22_ca+"'),"+
                "('"+pdb.question_23+"','"+pdb.question_23_a_1+"','"+pdb.question_23_a_2+"','"+pdb.question_23_a_3+"','"+pdb.question_23_a_4+"','"+pdb.question_23_ca+"'),"+
                "('"+pdb.question_24+"','"+pdb.question_24_a_1+"','"+pdb.question_24_a_2+"','"+pdb.question_24_a_3+"','"+pdb.question_24_a_4+"','"+pdb.question_24_ca+"'),"+
                "('"+pdb.question_25+"','"+pdb.question_25_a_1+"','"+pdb.question_25_a_2+"','"+pdb.question_25_a_3+"','"+pdb.question_25_a_4+"','"+pdb.question_25_ca+"'),"+
                "('"+pdb.question_26+"','"+pdb.question_26_a_1+"','"+pdb.question_26_a_2+"','"+pdb.question_26_a_3+"','"+pdb.question_26_a_4+"','"+pdb.question_26_ca+"'),"+
                "('"+pdb.question_27+"','"+pdb.question_27_a_1+"','"+pdb.question_27_a_2+"','"+pdb.question_27_a_3+"','"+pdb.question_27_a_4+"','"+pdb.question_27_ca+"'),"+
                "('"+pdb.question_28+"','"+pdb.question_28_a_1+"','"+pdb.question_28_a_2+"','"+pdb.question_28_a_3+"','"+pdb.question_28_a_4+"','"+pdb.question_28_ca+"'),"+
                "('"+pdb.question_29+"','"+pdb.question_29_a_1+"','"+pdb.question_29_a_2+"','"+pdb.question_29_a_3+"','"+pdb.question_29_a_4+"','"+pdb.question_29_ca+"'),"+
                "('"+pdb.question_30+"','"+pdb.question_30_a_1+"','"+pdb.question_30_a_2+"','"+pdb.question_30_a_3+"','"+pdb.question_30_a_4+"','"+pdb.question_30_ca+"'),"+

                "('"+pdb.question_31+"','"+pdb.question_31_a_1+"','"+pdb.question_31_a_2+"','"+pdb.question_31_a_3+"','"+pdb.question_31_a_4+"','"+pdb.question_31_ca+"'),"+
                "('"+pdb.question_32+"','"+pdb.question_32_a_1+"','"+pdb.question_32_a_2+"','"+pdb.question_32_a_3+"','"+pdb.question_32_a_4+"','"+pdb.question_32_ca+"'),"+
                "('"+pdb.question_33+"','"+pdb.question_33_a_1+"','"+pdb.question_33_a_2+"','"+pdb.question_33_a_3+"','"+pdb.question_33_a_4+"','"+pdb.question_33_ca+"'),"+
                "('"+pdb.question_34+"','"+pdb.question_34_a_1+"','"+pdb.question_34_a_2+"','"+pdb.question_34_a_3+"','"+pdb.question_34_a_4+"','"+pdb.question_34_ca+"'),"+
                "('"+pdb.question_35+"','"+pdb.question_35_a_1+"','"+pdb.question_35_a_2+"','"+pdb.question_35_a_3+"','"+pdb.question_35_a_4+"','"+pdb.question_35_ca+"'),"+
                "('"+pdb.question_36+"','"+pdb.question_36_a_1+"','"+pdb.question_36_a_2+"','"+pdb.question_36_a_3+"','"+pdb.question_36_a_4+"','"+pdb.question_36_ca+"'),"+
                "('"+pdb.question_37+"','"+pdb.question_37_a_1+"','"+pdb.question_37_a_2+"','"+pdb.question_37_a_3+"','"+pdb.question_37_a_4+"','"+pdb.question_37_ca+"'),"+
                "('"+pdb.question_38+"','"+pdb.question_38_a_1+"','"+pdb.question_38_a_2+"','"+pdb.question_38_a_3+"','"+pdb.question_38_a_4+"','"+pdb.question_38_ca+"'),"+
                "('"+pdb.question_39+"','"+pdb.question_39_a_1+"','"+pdb.question_39_a_2+"','"+pdb.question_39_a_3+"','"+pdb.question_39_a_4+"','"+pdb.question_39_ca+"'),"+


                "('"+pdb.question_40+"','"+pdb.question_40_a_1+"','"+pdb.question_40_a_2+"','"+pdb.question_40_a_3+"','"+pdb.question_40_a_4+"','"+pdb.question_40_ca+"'),"+
                "('"+pdb.question_41+"','"+pdb.question_41_a_1+"','"+pdb.question_41_a_2+"','"+pdb.question_41_a_3+"','"+pdb.question_41_a_4+"','"+pdb.question_41_ca+"'),"+
                "('"+pdb.question_42+"','"+pdb.question_42_a_1+"','"+pdb.question_42_a_2+"','"+pdb.question_42_a_3+"','"+pdb.question_42_a_4+"','"+pdb.question_42_ca+"'),"+
                "('"+pdb.question_43+"','"+pdb.question_43_a_1+"','"+pdb.question_43_a_2+"','"+pdb.question_43_a_3+"','"+pdb.question_43_a_4+"','"+pdb.question_43_ca+"'),"+
                "('"+pdb.question_44+"','"+pdb.question_44_a_1+"','"+pdb.question_44_a_2+"','"+pdb.question_44_a_3+"','"+pdb.question_44_a_4+"','"+pdb.question_44_ca+"'),"+
                "('"+pdb.question_45+"','"+pdb.question_45_a_1+"','"+pdb.question_45_a_2+"','"+pdb.question_45_a_3+"','"+pdb.question_45_a_4+"','"+pdb.question_45_ca+"'),"+
                "('"+pdb.question_46+"','"+pdb.question_46_a_1+"','"+pdb.question_46_a_2+"','"+pdb.question_46_a_3+"','"+pdb.question_46_a_4+"','"+pdb.question_46_ca+"'),"+
                "('"+pdb.question_47+"','"+pdb.question_47_a_1+"','"+pdb.question_47_a_2+"','"+pdb.question_47_a_3+"','"+pdb.question_47_a_4+"','"+pdb.question_47_ca+"'),"+
                "('"+pdb.question_48+"','"+pdb.question_48_a_1+"','"+pdb.question_48_a_2+"','"+pdb.question_48_a_3+"','"+pdb.question_48_a_4+"','"+pdb.question_48_ca+"'),"+
                "('"+pdb.question_49+"','"+pdb.question_49_a_1+"','"+pdb.question_49_a_2+"','"+pdb.question_49_a_3+"','"+pdb.question_49_a_4+"','"+pdb.question_49_ca+"'),"+
                "('"+pdb.question_50+"','"+pdb.question_50_a_1+"','"+pdb.question_50_a_2+"','"+pdb.question_50_a_3+"','"+pdb.question_50_a_4+"','"+pdb.question_50_ca+"'),"+

                "('"+pdb.question_51+"','"+pdb.question_51_a_1+"','"+pdb.question_51_a_2+"','"+pdb.question_51_a_3+"','"+pdb.question_51_a_4+"','"+pdb.question_51_ca+"'),"+
                "('"+pdb.question_52+"','"+pdb.question_52_a_1+"','"+pdb.question_52_a_2+"','"+pdb.question_52_a_3+"','"+pdb.question_52_a_4+"','"+pdb.question_52_ca+"'),"+
                "('"+pdb.question_53+"','"+pdb.question_53_a_1+"','"+pdb.question_53_a_2+"','"+pdb.question_53_a_3+"','"+pdb.question_53_a_4+"','"+pdb.question_53_ca+"'),"+
                "('"+pdb.question_54+"','"+pdb.question_54_a_1+"','"+pdb.question_54_a_2+"','"+pdb.question_54_a_3+"','"+pdb.question_54_a_4+"','"+pdb.question_54_ca+"'),"+
                "('"+pdb.question_55+"','"+pdb.question_55_a_1+"','"+pdb.question_55_a_2+"','"+pdb.question_55_a_3+"','"+pdb.question_55_a_4+"','"+pdb.question_55_ca+"'),"+
                "('"+pdb.question_56+"','"+pdb.question_56_a_1+"','"+pdb.question_56_a_2+"','"+pdb.question_56_a_3+"','"+pdb.question_56_a_4+"','"+pdb.question_56_ca+"'),"+
                "('"+pdb.question_57+"','"+pdb.question_57_a_1+"','"+pdb.question_57_a_2+"','"+pdb.question_57_a_3+"','"+pdb.question_57_a_4+"','"+pdb.question_57_ca+"'),"+
                "('"+pdb.question_58+"','"+pdb.question_58_a_1+"','"+pdb.question_58_a_2+"','"+pdb.question_58_a_3+"','"+pdb.question_58_a_4+"','"+pdb.question_58_ca+"'),"+
                "('"+pdb.question_59+"','"+pdb.question_59_a_1+"','"+pdb.question_59_a_2+"','"+pdb.question_59_a_3+"','"+pdb.question_59_a_4+"','"+pdb.question_59_ca+"'),"+
                "('"+pdb.question_60+"','"+pdb.question_60_a_1+"','"+pdb.question_60_a_2+"','"+pdb.question_60_a_3+"','"+pdb.question_60_a_4+"','"+pdb.question_60_ca+"'),"+

                "('"+pdb.question_61+"','"+pdb.question_61_a_1+"','"+pdb.question_61_a_2+"','"+pdb.question_61_a_3+"','"+pdb.question_61_a_4+"','"+pdb.question_61_ca+"'),"+
                "('"+pdb.question_62+"','"+pdb.question_62_a_1+"','"+pdb.question_62_a_2+"','"+pdb.question_62_a_3+"','"+pdb.question_62_a_4+"','"+pdb.question_62_ca+"'),"+
                "('"+pdb.question_63+"','"+pdb.question_63_a_1+"','"+pdb.question_63_a_2+"','"+pdb.question_63_a_3+"','"+pdb.question_63_a_4+"','"+pdb.question_63_ca+"'),"+
                "('"+pdb.question_64+"','"+pdb.question_64_a_1+"','"+pdb.question_64_a_2+"','"+pdb.question_64_a_3+"','"+pdb.question_64_a_4+"','"+pdb.question_64_ca+"'),"+
                "('"+pdb.question_65+"','"+pdb.question_65_a_1+"','"+pdb.question_65_a_2+"','"+pdb.question_65_a_3+"','"+pdb.question_65_a_4+"','"+pdb.question_65_ca+"'),"+
                "('"+pdb.question_66+"','"+pdb.question_66_a_1+"','"+pdb.question_66_a_2+"','"+pdb.question_66_a_3+"','"+pdb.question_66_a_4+"','"+pdb.question_66_ca+"'),"+
                "('"+pdb.question_67+"','"+pdb.question_67_a_1+"','"+pdb.question_67_a_2+"','"+pdb.question_67_a_3+"','"+pdb.question_67_a_4+"','"+pdb.question_67_ca+"'),"+
                "('"+pdb.question_68+"','"+pdb.question_68_a_1+"','"+pdb.question_68_a_2+"','"+pdb.question_68_a_3+"','"+pdb.question_68_a_4+"','"+pdb.question_68_ca+"'),"+
                "('"+pdb.question_69+"','"+pdb.question_69_a_1+"','"+pdb.question_69_a_2+"','"+pdb.question_69_a_3+"','"+pdb.question_69_a_4+"','"+pdb.question_69_ca+"'),"+
                "('"+pdb.question_70+"','"+pdb.question_70_a_1+"','"+pdb.question_70_a_2+"','"+pdb.question_70_a_3+"','"+pdb.question_70_a_4+"','"+pdb.question_70_ca+"'),"+

                "('"+pdb.question_71+"','"+pdb.question_71_a_1+"','"+pdb.question_71_a_2+"','"+pdb.question_71_a_3+"','"+pdb.question_71_a_4+"','"+pdb.question_71_ca+"'),"+
                "('"+pdb.question_72+"','"+pdb.question_72_a_1+"','"+pdb.question_72_a_2+"','"+pdb.question_72_a_3+"','"+pdb.question_72_a_4+"','"+pdb.question_72_ca+"'),"+
                "('"+pdb.question_73+"','"+pdb.question_73_a_1+"','"+pdb.question_73_a_2+"','"+pdb.question_73_a_3+"','"+pdb.question_73_a_4+"','"+pdb.question_73_ca+"'),"+
                "('"+pdb.question_74+"','"+pdb.question_74_a_1+"','"+pdb.question_74_a_2+"','"+pdb.question_74_a_3+"','"+pdb.question_74_a_4+"','"+pdb.question_74_ca+"'),"+
                "('"+pdb.question_75+"','"+pdb.question_75_a_1+"','"+pdb.question_75_a_2+"','"+pdb.question_75_a_3+"','"+pdb.question_75_a_4+"','"+pdb.question_75_ca+"'),"+
                "('"+pdb.question_76+"','"+pdb.question_76_a_1+"','"+pdb.question_76_a_2+"','"+pdb.question_76_a_3+"','"+pdb.question_76_a_4+"','"+pdb.question_76_ca+"'),"+
                "('"+pdb.question_77+"','"+pdb.question_77_a_1+"','"+pdb.question_77_a_2+"','"+pdb.question_77_a_3+"','"+pdb.question_77_a_4+"','"+pdb.question_77_ca+"'),"+
                "('"+pdb.question_78+"','"+pdb.question_78_a_1+"','"+pdb.question_78_a_2+"','"+pdb.question_78_a_3+"','"+pdb.question_78_a_4+"','"+pdb.question_78_ca+"'),"+
                "('"+pdb.question_79+"','"+pdb.question_79_a_1+"','"+pdb.question_79_a_2+"','"+pdb.question_79_a_3+"','"+pdb.question_79_a_4+"','"+pdb.question_79_ca+"'),"+



                "('"+pdb.question_80+"','"+pdb.question_80_a_1+"','"+pdb.question_80_a_2+"','"+pdb.question_80_a_3+"','"+pdb.question_80_a_4+"','"+pdb.question_80_ca+"'),"+
                "('"+pdb.question_81+"','"+pdb.question_81_a_1+"','"+pdb.question_81_a_2+"','"+pdb.question_81_a_3+"','"+pdb.question_81_a_4+"','"+pdb.question_81_ca+"'),"+
                "('"+pdb.question_82+"','"+pdb.question_82_a_1+"','"+pdb.question_82_a_2+"','"+pdb.question_82_a_3+"','"+pdb.question_82_a_4+"','"+pdb.question_82_ca+"'),"+
                "('"+pdb.question_83+"','"+pdb.question_83_a_1+"','"+pdb.question_83_a_2+"','"+pdb.question_83_a_3+"','"+pdb.question_83_a_4+"','"+pdb.question_83_ca+"'),"+
                "('"+pdb.question_84+"','"+pdb.question_84_a_1+"','"+pdb.question_84_a_2+"','"+pdb.question_84_a_3+"','"+pdb.question_84_a_4+"','"+pdb.question_84_ca+"'),"+
                "('"+pdb.question_85+"','"+pdb.question_85_a_1+"','"+pdb.question_85_a_2+"','"+pdb.question_85_a_3+"','"+pdb.question_85_a_4+"','"+pdb.question_85_ca+"'),"+
                "('"+pdb.question_86+"','"+pdb.question_86_a_1+"','"+pdb.question_86_a_2+"','"+pdb.question_86_a_3+"','"+pdb.question_86_a_4+"','"+pdb.question_86_ca+"'),"+
                "('"+pdb.question_87+"','"+pdb.question_87_a_1+"','"+pdb.question_87_a_2+"','"+pdb.question_87_a_3+"','"+pdb.question_87_a_4+"','"+pdb.question_87_ca+"'),"+
                "('"+pdb.question_88+"','"+pdb.question_88_a_1+"','"+pdb.question_88_a_2+"','"+pdb.question_88_a_3+"','"+pdb.question_88_a_4+"','"+pdb.question_88_ca+"'),"+
                "('"+pdb.question_89+"','"+pdb.question_89_a_1+"','"+pdb.question_89_a_2+"','"+pdb.question_89_a_3+"','"+pdb.question_89_a_4+"','"+pdb.question_89_ca+"'),"+
                "('"+pdb.question_90+"','"+pdb.question_90_a_1+"','"+pdb.question_90_a_2+"','"+pdb.question_90_a_3+"','"+pdb.question_90_a_4+"','"+pdb.question_90_ca+"'),"+

                "('"+pdb.question_91+"','"+pdb.question_91_a_1+"','"+pdb.question_91_a_2+"','"+pdb.question_91_a_3+"','"+pdb.question_91_a_4+"','"+pdb.question_91_ca+"'),"+
                "('"+pdb.question_92+"','"+pdb.question_92_a_1+"','"+pdb.question_92_a_2+"','"+pdb.question_92_a_3+"','"+pdb.question_92_a_4+"','"+pdb.question_92_ca+"'),"+
                "('"+pdb.question_93+"','"+pdb.question_93_a_1+"','"+pdb.question_93_a_2+"','"+pdb.question_93_a_3+"','"+pdb.question_93_a_4+"','"+pdb.question_93_ca+"'),"+
                "('"+pdb.question_94+"','"+pdb.question_94_a_1+"','"+pdb.question_94_a_2+"','"+pdb.question_94_a_3+"','"+pdb.question_94_a_4+"','"+pdb.question_94_ca+"'),"+
                "('"+pdb.question_95+"','"+pdb.question_95_a_1+"','"+pdb.question_95_a_2+"','"+pdb.question_95_a_3+"','"+pdb.question_95_a_4+"','"+pdb.question_95_ca+"'),"+
                "('"+pdb.question_96+"','"+pdb.question_96_a_1+"','"+pdb.question_96_a_2+"','"+pdb.question_96_a_3+"','"+pdb.question_96_a_4+"','"+pdb.question_96_ca+"'),"+
                "('"+pdb.question_97+"','"+pdb.question_97_a_1+"','"+pdb.question_97_a_2+"','"+pdb.question_97_a_3+"','"+pdb.question_97_a_4+"','"+pdb.question_97_ca+"'),"+
                "('"+pdb.question_98+"','"+pdb.question_98_a_1+"','"+pdb.question_98_a_2+"','"+pdb.question_98_a_3+"','"+pdb.question_98_a_4+"','"+pdb.question_98_ca+"'),"+
                "('"+pdb.question_99+"','"+pdb.question_99_a_1+"','"+pdb.question_99_a_2+"','"+pdb.question_99_a_3+"','"+pdb.question_99_a_4+"','"+pdb.question_99_ca+"'),"+



                "('"+pdb.question_100+"','"+pdb.question_100_a_1+"','"+pdb.question_100_a_2+"','"+pdb.question_100_a_3+"','"+pdb.question_100_a_4+"','"+pdb.question_100_ca+"'),"+
                "('"+pdb.question_101+"','"+pdb.question_101_a_1+"','"+pdb.question_101_a_2+"','"+pdb.question_101_a_3+"','"+pdb.question_101_a_4+"','"+pdb.question_101_ca+"'),"+
                "('"+pdb.question_102+"','"+pdb.question_102_a_1+"','"+pdb.question_102_a_2+"','"+pdb.question_102_a_3+"','"+pdb.question_102_a_4+"','"+pdb.question_102_ca+"'),"+
                "('"+pdb.question_103+"','"+pdb.question_103_a_1+"','"+pdb.question_103_a_2+"','"+pdb.question_103_a_3+"','"+pdb.question_103_a_4+"','"+pdb.question_103_ca+"'),"+
                "('"+pdb.question_104+"','"+pdb.question_104_a_1+"','"+pdb.question_104_a_2+"','"+pdb.question_104_a_3+"','"+pdb.question_104_a_4+"','"+pdb.question_104_ca+"'),"+
                "('"+pdb.question_105+"','"+pdb.question_105_a_1+"','"+pdb.question_105_a_2+"','"+pdb.question_105_a_3+"','"+pdb.question_105_a_4+"','"+pdb.question_105_ca+"'),"+
                "('"+pdb.question_106+"','"+pdb.question_106_a_1+"','"+pdb.question_106_a_2+"','"+pdb.question_106_a_3+"','"+pdb.question_106_a_4+"','"+pdb.question_106_ca+"'),"+
                "('"+pdb.question_107+"','"+pdb.question_107_a_1+"','"+pdb.question_107_a_2+"','"+pdb.question_107_a_3+"','"+pdb.question_107_a_4+"','"+pdb.question_107_ca+"'),"+
                "('"+pdb.question_108+"','"+pdb.question_108_a_1+"','"+pdb.question_108_a_2+"','"+pdb.question_108_a_3+"','"+pdb.question_108_a_4+"','"+pdb.question_108_ca+"'),"+
                "('"+pdb.question_109+"','"+pdb.question_109_a_1+"','"+pdb.question_109_a_2+"','"+pdb.question_109_a_3+"','"+pdb.question_109_a_4+"','"+pdb.question_109_ca+"'),"+
                "('"+pdb.question_110+"','"+pdb.question_110_a_1+"','"+pdb.question_110_a_2+"','"+pdb.question_110_a_3+"','"+pdb.question_110_a_4+"','"+pdb.question_110_ca+"'),"+

                "('"+pdb.question_111+"','"+pdb.question_111_a_1+"','"+pdb.question_111_a_2+"','"+pdb.question_111_a_3+"','"+pdb.question_111_a_4+"','"+pdb.question_111_ca+"'),"+
                "('"+pdb.question_112+"','"+pdb.question_112_a_1+"','"+pdb.question_112_a_2+"','"+pdb.question_112_a_3+"','"+pdb.question_112_a_4+"','"+pdb.question_112_ca+"'),"+
                "('"+pdb.question_113+"','"+pdb.question_113_a_1+"','"+pdb.question_113_a_2+"','"+pdb.question_113_a_3+"','"+pdb.question_113_a_4+"','"+pdb.question_113_ca+"'),"+
                "('"+pdb.question_114+"','"+pdb.question_114_a_1+"','"+pdb.question_114_a_2+"','"+pdb.question_114_a_3+"','"+pdb.question_114_a_4+"','"+pdb.question_114_ca+"'),"+
                "('"+pdb.question_115+"','"+pdb.question_115_a_1+"','"+pdb.question_115_a_2+"','"+pdb.question_115_a_3+"','"+pdb.question_115_a_4+"','"+pdb.question_115_ca+"'),"+
                "('"+pdb.question_116+"','"+pdb.question_116_a_1+"','"+pdb.question_116_a_2+"','"+pdb.question_116_a_3+"','"+pdb.question_116_a_4+"','"+pdb.question_116_ca+"'),"+
                "('"+pdb.question_117+"','"+pdb.question_117_a_1+"','"+pdb.question_117_a_2+"','"+pdb.question_117_a_3+"','"+pdb.question_117_a_4+"','"+pdb.question_117_ca+"'),"+
                "('"+pdb.question_118+"','"+pdb.question_118_a_1+"','"+pdb.question_118_a_2+"','"+pdb.question_118_a_3+"','"+pdb.question_118_a_4+"','"+pdb.question_118_ca+"'),"+
                "('"+pdb.question_119+"','"+pdb.question_119_a_1+"','"+pdb.question_119_a_2+"','"+pdb.question_119_a_3+"','"+pdb.question_119_a_4+"','"+pdb.question_119_ca+"'),"+

                "('"+pdb.question_120+"','"+pdb.question_120_a_1+"','"+pdb.question_120_a_2+"','"+pdb.question_120_a_3+"','"+pdb.question_120_a_4+"','"+pdb.question_120_ca+"'),"+
                "('"+pdb.question_121+"','"+pdb.question_121_a_1+"','"+pdb.question_121_a_2+"','"+pdb.question_121_a_3+"','"+pdb.question_121_a_4+"','"+pdb.question_121_ca+"'),"+
                "('"+pdb.question_122+"','"+pdb.question_122_a_1+"','"+pdb.question_122_a_2+"','"+pdb.question_122_a_3+"','"+pdb.question_122_a_4+"','"+pdb.question_122_ca+"'),"+
                "('"+pdb.question_123+"','"+pdb.question_123_a_1+"','"+pdb.question_123_a_2+"','"+pdb.question_123_a_3+"','"+pdb.question_123_a_4+"','"+pdb.question_123_ca+"'),"+
                "('"+pdb.question_124+"','"+pdb.question_124_a_1+"','"+pdb.question_124_a_2+"','"+pdb.question_124_a_3+"','"+pdb.question_124_a_4+"','"+pdb.question_124_ca+"'),"+
                "('"+pdb.question_125+"','"+pdb.question_125_a_1+"','"+pdb.question_125_a_2+"','"+pdb.question_125_a_3+"','"+pdb.question_125_a_4+"','"+pdb.question_125_ca+"'),"+
                "('"+pdb.question_126+"','"+pdb.question_126_a_1+"','"+pdb.question_126_a_2+"','"+pdb.question_126_a_3+"','"+pdb.question_126_a_4+"','"+pdb.question_126_ca+"'),"+
                "('"+pdb.question_127+"','"+pdb.question_127_a_1+"','"+pdb.question_127_a_2+"','"+pdb.question_127_a_3+"','"+pdb.question_127_a_4+"','"+pdb.question_127_ca+"'),"+
                "('"+pdb.question_128+"','"+pdb.question_128_a_1+"','"+pdb.question_128_a_2+"','"+pdb.question_128_a_3+"','"+pdb.question_128_a_4+"','"+pdb.question_128_ca+"'),"+
                "('"+pdb.question_129+"','"+pdb.question_129_a_1+"','"+pdb.question_129_a_2+"','"+pdb.question_129_a_3+"','"+pdb.question_129_a_4+"','"+pdb.question_129_ca+"'),"+
                "('"+pdb.question_130+"','"+pdb.question_130_a_1+"','"+pdb.question_130_a_2+"','"+pdb.question_130_a_3+"','"+pdb.question_130_a_4+"','"+pdb.question_130_ca+"'),"+

                "('"+pdb.question_131+"','"+pdb.question_131_a_1+"','"+pdb.question_131_a_2+"','"+pdb.question_131_a_3+"','"+pdb.question_131_a_4+"','"+pdb.question_131_ca+"'),"+
                "('"+pdb.question_132+"','"+pdb.question_132_a_1+"','"+pdb.question_132_a_2+"','"+pdb.question_132_a_3+"','"+pdb.question_132_a_4+"','"+pdb.question_132_ca+"'),"+
                "('"+pdb.question_133+"','"+pdb.question_133_a_1+"','"+pdb.question_133_a_2+"','"+pdb.question_133_a_3+"','"+pdb.question_133_a_4+"','"+pdb.question_133_ca+"'),"+
                "('"+pdb.question_134+"','"+pdb.question_134_a_1+"','"+pdb.question_134_a_2+"','"+pdb.question_134_a_3+"','"+pdb.question_134_a_4+"','"+pdb.question_134_ca+"'),"+
                "('"+pdb.question_135+"','"+pdb.question_135_a_1+"','"+pdb.question_135_a_2+"','"+pdb.question_135_a_3+"','"+pdb.question_135_a_4+"','"+pdb.question_135_ca+"'),"+
                "('"+pdb.question_136+"','"+pdb.question_136_a_1+"','"+pdb.question_136_a_2+"','"+pdb.question_136_a_3+"','"+pdb.question_136_a_4+"','"+pdb.question_136_ca+"'),"+
                "('"+pdb.question_137+"','"+pdb.question_137_a_1+"','"+pdb.question_137_a_2+"','"+pdb.question_137_a_3+"','"+pdb.question_137_a_4+"','"+pdb.question_137_ca+"'),"+
                "('"+pdb.question_138+"','"+pdb.question_138_a_1+"','"+pdb.question_138_a_2+"','"+pdb.question_138_a_3+"','"+pdb.question_138_a_4+"','"+pdb.question_138_ca+"'),"+
                "('"+pdb.question_139+"','"+pdb.question_139_a_1+"','"+pdb.question_139_a_2+"','"+pdb.question_139_a_3+"','"+pdb.question_139_a_4+"','"+pdb.question_139_ca+"'),"+

                "('"+pdb.question_140+"','"+pdb.question_140_a_1+"','"+pdb.question_140_a_2+"','"+pdb.question_140_a_3+"','"+pdb.question_140_a_4+"','"+pdb.question_140_ca+"'),"+
                "('"+pdb.question_141+"','"+pdb.question_141_a_1+"','"+pdb.question_141_a_2+"','"+pdb.question_141_a_3+"','"+pdb.question_141_a_4+"','"+pdb.question_141_ca+"'),"+
                "('"+pdb.question_142+"','"+pdb.question_142_a_1+"','"+pdb.question_142_a_2+"','"+pdb.question_142_a_3+"','"+pdb.question_142_a_4+"','"+pdb.question_142_ca+"'),"+
                "('"+pdb.question_143+"','"+pdb.question_143_a_1+"','"+pdb.question_143_a_2+"','"+pdb.question_143_a_3+"','"+pdb.question_143_a_4+"','"+pdb.question_143_ca+"'),"+
                "('"+pdb.question_144+"','"+pdb.question_144_a_1+"','"+pdb.question_144_a_2+"','"+pdb.question_144_a_3+"','"+pdb.question_144_a_4+"','"+pdb.question_144_ca+"'),"+
                "('"+pdb.question_145+"','"+pdb.question_145_a_1+"','"+pdb.question_145_a_2+"','"+pdb.question_145_a_3+"','"+pdb.question_145_a_4+"','"+pdb.question_145_ca+"'),"+
                "('"+pdb.question_146+"','"+pdb.question_146_a_1+"','"+pdb.question_146_a_2+"','"+pdb.question_146_a_3+"','"+pdb.question_146_a_4+"','"+pdb.question_146_ca+"'),"+
                "('"+pdb.question_147+"','"+pdb.question_147_a_1+"','"+pdb.question_147_a_2+"','"+pdb.question_147_a_3+"','"+pdb.question_147_a_4+"','"+pdb.question_147_ca+"'),"+
                "('"+pdb.question_148+"','"+pdb.question_148_a_1+"','"+pdb.question_148_a_2+"','"+pdb.question_148_a_3+"','"+pdb.question_148_a_4+"','"+pdb.question_148_ca+"'),"+
                "('"+pdb.question_149+"','"+pdb.question_149_a_1+"','"+pdb.question_149_a_2+"','"+pdb.question_149_a_3+"','"+pdb.question_149_a_4+"','"+pdb.question_149_ca+"'),"+
                "('"+pdb.question_150+"','"+pdb.question_150_a_1+"','"+pdb.question_150_a_2+"','"+pdb.question_150_a_3+"','"+pdb.question_150_a_4+"','"+pdb.question_150_ca+"'),"+

                "('"+pdb.question_151+"','"+pdb.question_151_a_1+"','"+pdb.question_151_a_2+"','"+pdb.question_151_a_3+"','"+pdb.question_151_a_4+"','"+pdb.question_151_ca+"'),"+
                "('"+pdb.question_152+"','"+pdb.question_152_a_1+"','"+pdb.question_152_a_2+"','"+pdb.question_152_a_3+"','"+pdb.question_152_a_4+"','"+pdb.question_152_ca+"'),"+
                "('"+pdb.question_153+"','"+pdb.question_153_a_1+"','"+pdb.question_153_a_2+"','"+pdb.question_153_a_3+"','"+pdb.question_153_a_4+"','"+pdb.question_153_ca+"'),"+
                "('"+pdb.question_154+"','"+pdb.question_154_a_1+"','"+pdb.question_154_a_2+"','"+pdb.question_154_a_3+"','"+pdb.question_154_a_4+"','"+pdb.question_154_ca+"'),"+
                "('"+pdb.question_155+"','"+pdb.question_155_a_1+"','"+pdb.question_155_a_2+"','"+pdb.question_155_a_3+"','"+pdb.question_155_a_4+"','"+pdb.question_155_ca+"'),"+
                "('"+pdb.question_156+"','"+pdb.question_156_a_1+"','"+pdb.question_156_a_2+"','"+pdb.question_156_a_3+"','"+pdb.question_156_a_4+"','"+pdb.question_156_ca+"'),"+
                "('"+pdb.question_157+"','"+pdb.question_157_a_1+"','"+pdb.question_157_a_2+"','"+pdb.question_157_a_3+"','"+pdb.question_157_a_4+"','"+pdb.question_157_ca+"'),"+
                "('"+pdb.question_158+"','"+pdb.question_158_a_1+"','"+pdb.question_158_a_2+"','"+pdb.question_158_a_3+"','"+pdb.question_158_a_4+"','"+pdb.question_158_ca+"'),"+
                "('"+pdb.question_159+"','"+pdb.question_159_a_1+"','"+pdb.question_159_a_2+"','"+pdb.question_159_a_3+"','"+pdb.question_159_a_4+"','"+pdb.question_159_ca+"'),"+

                "('"+pdb.question_160+"','"+pdb.question_160_a_1+"','"+pdb.question_160_a_2+"','"+pdb.question_160_a_3+"','"+pdb.question_160_a_4+"','"+pdb.question_160_ca+"'),"+
                "('"+pdb.question_161+"','"+pdb.question_161_a_1+"','"+pdb.question_161_a_2+"','"+pdb.question_161_a_3+"','"+pdb.question_161_a_4+"','"+pdb.question_161_ca+"'),"+
                "('"+pdb.question_162+"','"+pdb.question_162_a_1+"','"+pdb.question_162_a_2+"','"+pdb.question_162_a_3+"','"+pdb.question_162_a_4+"','"+pdb.question_162_ca+"'),"+
                "('"+pdb.question_163+"','"+pdb.question_163_a_1+"','"+pdb.question_163_a_2+"','"+pdb.question_163_a_3+"','"+pdb.question_163_a_4+"','"+pdb.question_163_ca+"'),"+
                "('"+pdb.question_164+"','"+pdb.question_164_a_1+"','"+pdb.question_164_a_2+"','"+pdb.question_164_a_3+"','"+pdb.question_164_a_4+"','"+pdb.question_164_ca+"'),"+
                "('"+pdb.question_165+"','"+pdb.question_165_a_1+"','"+pdb.question_165_a_2+"','"+pdb.question_165_a_3+"','"+pdb.question_165_a_4+"','"+pdb.question_165_ca+"'),"+
                "('"+pdb.question_166+"','"+pdb.question_166_a_1+"','"+pdb.question_166_a_2+"','"+pdb.question_166_a_3+"','"+pdb.question_166_a_4+"','"+pdb.question_166_ca+"'),"+
                "('"+pdb.question_167+"','"+pdb.question_167_a_1+"','"+pdb.question_167_a_2+"','"+pdb.question_167_a_3+"','"+pdb.question_167_a_4+"','"+pdb.question_167_ca+"'),"+
                "('"+pdb.question_168+"','"+pdb.question_168_a_1+"','"+pdb.question_168_a_2+"','"+pdb.question_168_a_3+"','"+pdb.question_168_a_4+"','"+pdb.question_168_ca+"'),"+
                "('"+pdb.question_169+"','"+pdb.question_169_a_1+"','"+pdb.question_169_a_2+"','"+pdb.question_169_a_3+"','"+pdb.question_169_a_4+"','"+pdb.question_169_ca+"'),"+
                "('"+pdb.question_170+"','"+pdb.question_170_a_1+"','"+pdb.question_170_a_2+"','"+pdb.question_170_a_3+"','"+pdb.question_170_a_4+"','"+pdb.question_170_ca+"'),"+

                "('"+pdb.question_171+"','"+pdb.question_171_a_1+"','"+pdb.question_171_a_2+"','"+pdb.question_171_a_3+"','"+pdb.question_171_a_4+"','"+pdb.question_171_ca+"'),"+
                "('"+pdb.question_172+"','"+pdb.question_172_a_1+"','"+pdb.question_172_a_2+"','"+pdb.question_172_a_3+"','"+pdb.question_172_a_4+"','"+pdb.question_172_ca+"'),"+
                "('"+pdb.question_173+"','"+pdb.question_173_a_1+"','"+pdb.question_173_a_2+"','"+pdb.question_173_a_3+"','"+pdb.question_173_a_4+"','"+pdb.question_173_ca+"'),"+
                "('"+pdb.question_174+"','"+pdb.question_174_a_1+"','"+pdb.question_174_a_2+"','"+pdb.question_174_a_3+"','"+pdb.question_174_a_4+"','"+pdb.question_174_ca+"'),"+
                "('"+pdb.question_175+"','"+pdb.question_175_a_1+"','"+pdb.question_175_a_2+"','"+pdb.question_175_a_3+"','"+pdb.question_175_a_4+"','"+pdb.question_175_ca+"'),"+
                "('"+pdb.question_176+"','"+pdb.question_176_a_1+"','"+pdb.question_176_a_2+"','"+pdb.question_176_a_3+"','"+pdb.question_176_a_4+"','"+pdb.question_176_ca+"'),"+
                "('"+pdb.question_177+"','"+pdb.question_177_a_1+"','"+pdb.question_177_a_2+"','"+pdb.question_177_a_3+"','"+pdb.question_177_a_4+"','"+pdb.question_177_ca+"'),"+
                "('"+pdb.question_178+"','"+pdb.question_178_a_1+"','"+pdb.question_178_a_2+"','"+pdb.question_178_a_3+"','"+pdb.question_178_a_4+"','"+pdb.question_178_ca+"'),"+
                "('"+pdb.question_179+"','"+pdb.question_179_a_1+"','"+pdb.question_179_a_2+"','"+pdb.question_179_a_3+"','"+pdb.question_179_a_4+"','"+pdb.question_179_ca+"'),"+

                "('"+pdb.question_180+"','"+pdb.question_180_a_1+"','"+pdb.question_180_a_2+"','"+pdb.question_180_a_3+"','"+pdb.question_180_a_4+"','"+pdb.question_180_ca+"'),"+
                "('"+pdb.question_181+"','"+pdb.question_181_a_1+"','"+pdb.question_181_a_2+"','"+pdb.question_181_a_3+"','"+pdb.question_181_a_4+"','"+pdb.question_181_ca+"'),"+
                "('"+pdb.question_182+"','"+pdb.question_182_a_1+"','"+pdb.question_182_a_2+"','"+pdb.question_182_a_3+"','"+pdb.question_182_a_4+"','"+pdb.question_182_ca+"'),"+
                "('"+pdb.question_183+"','"+pdb.question_183_a_1+"','"+pdb.question_183_a_2+"','"+pdb.question_183_a_3+"','"+pdb.question_183_a_4+"','"+pdb.question_183_ca+"'),"+
                "('"+pdb.question_184+"','"+pdb.question_184_a_1+"','"+pdb.question_184_a_2+"','"+pdb.question_184_a_3+"','"+pdb.question_184_a_4+"','"+pdb.question_184_ca+"'),"+
                "('"+pdb.question_185+"','"+pdb.question_185_a_1+"','"+pdb.question_185_a_2+"','"+pdb.question_185_a_3+"','"+pdb.question_185_a_4+"','"+pdb.question_185_ca+"'),"+
                "('"+pdb.question_186+"','"+pdb.question_186_a_1+"','"+pdb.question_186_a_2+"','"+pdb.question_186_a_3+"','"+pdb.question_186_a_4+"','"+pdb.question_186_ca+"'),"+
                "('"+pdb.question_187+"','"+pdb.question_187_a_1+"','"+pdb.question_187_a_2+"','"+pdb.question_187_a_3+"','"+pdb.question_187_a_4+"','"+pdb.question_187_ca+"'),"+
                "('"+pdb.question_188+"','"+pdb.question_188_a_1+"','"+pdb.question_188_a_2+"','"+pdb.question_188_a_3+"','"+pdb.question_188_a_4+"','"+pdb.question_188_ca+"'),"+
                "('"+pdb.question_189+"','"+pdb.question_189_a_1+"','"+pdb.question_189_a_2+"','"+pdb.question_189_a_3+"','"+pdb.question_189_a_4+"','"+pdb.question_189_ca+"'),"+

                "('"+pdb.question_190+"','"+pdb.question_190_a_1+"','"+pdb.question_190_a_2+"','"+pdb.question_190_a_3+"','"+pdb.question_190_a_4+"','"+pdb.question_190_ca+"'),"+
                "('"+pdb.question_191+"','"+pdb.question_191_a_1+"','"+pdb.question_191_a_2+"','"+pdb.question_191_a_3+"','"+pdb.question_191_a_4+"','"+pdb.question_191_ca+"'),"+
                "('"+pdb.question_192+"','"+pdb.question_192_a_1+"','"+pdb.question_192_a_2+"','"+pdb.question_192_a_3+"','"+pdb.question_192_a_4+"','"+pdb.question_192_ca+"'),"+
                "('"+pdb.question_193+"','"+pdb.question_193_a_1+"','"+pdb.question_193_a_2+"','"+pdb.question_193_a_3+"','"+pdb.question_193_a_4+"','"+pdb.question_193_ca+"'),"+
                "('"+pdb.question_194+"','"+pdb.question_194_a_1+"','"+pdb.question_194_a_2+"','"+pdb.question_194_a_3+"','"+pdb.question_194_a_4+"','"+pdb.question_194_ca+"'),"+
                "('"+pdb.question_195+"','"+pdb.question_195_a_1+"','"+pdb.question_195_a_2+"','"+pdb.question_195_a_3+"','"+pdb.question_195_a_4+"','"+pdb.question_195_ca+"'),"+
                "('"+pdb.question_196+"','"+pdb.question_196_a_1+"','"+pdb.question_196_a_2+"','"+pdb.question_196_a_3+"','"+pdb.question_196_a_4+"','"+pdb.question_196_ca+"'),"+
                "('"+pdb.question_197+"','"+pdb.question_197_a_1+"','"+pdb.question_197_a_2+"','"+pdb.question_197_a_3+"','"+pdb.question_197_a_4+"','"+pdb.question_197_ca+"'),"+
                "('"+pdb.question_198+"','"+pdb.question_198_a_1+"','"+pdb.question_198_a_2+"','"+pdb.question_198_a_3+"','"+pdb.question_198_a_4+"','"+pdb.question_198_ca+"'),"+
                "('"+pdb.question_199+"','"+pdb.question_199_a_1+"','"+pdb.question_199_a_2+"','"+pdb.question_199_a_3+"','"+pdb.question_199_a_4+"','"+pdb.question_199_ca+"'),"+
                "('"+pdb.question_200+"','"+pdb.question_200_a_1+"','"+pdb.question_200_a_2+"','"+pdb.question_200_a_3+"','"+pdb.question_200_a_4+"','"+pdb.question_200_ca+"')";

        db.execSQL(sql);



        //Toast.makeText(ProfessionalEducationActivity.this,"Data Installed on to your phone.",Toast.LENGTH_LONG).show();



    }
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA
    ////// END ADDING DATA






}
