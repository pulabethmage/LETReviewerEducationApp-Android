package com.rcustodio.letreviewerprofedandgened;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class CatogorySelectActivity extends Activity {

    CardView professionalCard ,generalCard, shareAppCard, RateApCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catogory_select);

        professionalCard = findViewById(R.id.categoryProfessionalButtonID);
        generalCard = findViewById(R.id.categoryGenaralButtonID);

        shareAppCard = findViewById(R.id.shareAppButton);
        RateApCard = findViewById(R.id.rateAppButton);



        professionalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CatogorySelectActivity.this,ProfessionalEducationActivity.class);
                startActivity(intent);

            }
        });

        generalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CatogorySelectActivity.this,GeneralEducationActivity.class);
                startActivity(intent);

            }
        });


        shareAppCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Context context = view.getContext();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Take the Professional and General Education test today, Check this app out :  https://play.google.com/store/apps/details?id="+context.getPackageName();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));


            }
        });

        RateApCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id="+getPackageName())));
                }
                catch (ActivityNotFoundException e){

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));

                }

            }
        });


    }

}
