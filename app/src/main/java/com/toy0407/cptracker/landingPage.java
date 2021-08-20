package com.toy0407.cptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class landingPage extends AppCompatActivity {

    TextView welcomeusername,appNameLandingPage;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        welcomeusername=findViewById(R.id.welcome_username);
        lottieAnimationView=findViewById(R.id.appLogoLandingPage);
        appNameLandingPage=findViewById(R.id.appNameLandingPage);

        String handle=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("handle","NotFound");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(handle.compareTo("NotFound")==0){
                    Intent intent=new Intent(landingPage.this,loginActivity.class);

                    Pair[] pairs=new Pair[2];
                    pairs[0]= new Pair<View,String>(lottieAnimationView,"logoAnimation");
                    pairs[1]= new Pair<View,String>(appNameLandingPage,"appNameTransition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(landingPage.this,pairs);
                    startActivity(intent, options.toBundle());
                    finish();
                }
                else{
                    welcomeusername.setText("Welcome "+handle);
                    welcomeusername.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(landingPage.this,MainActivity.class);
                    Pair[] pairs=new Pair[2];
                    pairs[0]= new Pair<View,String>(lottieAnimationView,"logoAnimation");
                    pairs[1]= new Pair<View,String>(welcomeusername,"handleTransition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(landingPage.this,pairs);
                    intent.putExtra("handle",handle);
                    startActivity(intent,options.toBundle());
                    finish();
                }
            }
        },2000);



    }
}