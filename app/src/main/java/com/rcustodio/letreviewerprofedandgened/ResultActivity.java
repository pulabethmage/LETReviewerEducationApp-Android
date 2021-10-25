package com.rcustodio.letreviewerprofedandgened;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    TextView resultNumber ;

    Button shareResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultNumber = findViewById(R.id.resultNumberTV);


        shareResult = findViewById(R.id.shareResultWithFriends);


        ////
        SharedPreferences prefs = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE);
        final Integer active_activity_number = prefs.getInt("ACTIVE_ACTIVITY",0);//"0" is the default value.

        if(active_activity_number ==1)
        {
            SharedPreferences prefsCorect = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
            Integer Correct_COUNT = prefsCorect.getInt("C_COUNT",0);//"0" is the default value.

            resultNumber.setText(Correct_COUNT.toString());

            ///setting default editer
            SharedPreferences.Editor Settingto_defaultediter = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE).edit();
            Settingto_defaultediter.putInt("ACTIVE_ACTIVITY",0);
            Settingto_defaultediter.apply();
            ///setting default editer

        }
        else if(active_activity_number ==2)
        {
            SharedPreferences prefsCorect = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
            Integer Correct_COUNT_GE = prefsCorect.getInt("C_COUNT_GE",0);//"0" is the default value.

            resultNumber.setText(Correct_COUNT_GE.toString());

            ///setting default editer
            SharedPreferences.Editor Settingto_defaultediter = getSharedPreferences("ONLINE_ACTIVITY", MODE_PRIVATE).edit();
            Settingto_defaultediter.putInt("ACTIVE_ACTIVITY",0);
            Settingto_defaultediter.apply();
            ///setting default editer

        }



        shareResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(active_activity_number ==1)
                {
                    SharedPreferences prefsCorect = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT = prefsCorect.getInt("C_COUNT",0);//"0" is the default value.


                    Context context = view.getContext();

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey I've got "+Correct_COUNT+" Correct Answers for Professional Education Test, Check Yours!! https://play.google.com/store/apps/details?id="+context.getPackageName();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));



                }
                else if(active_activity_number ==2)
                {
                    SharedPreferences prefsCorect = getSharedPreferences("CORRECT_COUNT", MODE_PRIVATE);
                    Integer Correct_COUNT_GE = prefsCorect.getInt("C_COUNT_GE",0);//"0" is the default value.

                    Context context = view.getContext();

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey I've got "+Correct_COUNT_GE+" Correct Answers for General Education Test, Check Yours!! https://play.google.com/store/apps/details?id="+context.getPackageName();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));

                }



            }
        });


    }
}
