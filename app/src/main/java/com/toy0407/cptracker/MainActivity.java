package com.toy0407.cptracker;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    LottieAnimationView profileImage;
    ViewPagerAdapter viewPagerAdapter;
    ImageButton signoutbutton;
    TextView handletextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainactivitytablayout);
        ViewPager2 viewPager2= findViewById(R.id.mainactivityviewpager);
        signoutbutton=findViewById(R.id.signoutbutton);
        handletextview=findViewById(R.id.mainactivityusername);
        profileImage=findViewById(R.id.profileImage);


        tabLayout.addTab(tabLayout.newTab().setText("UPCOMING"));
        tabLayout.addTab(tabLayout.newTab().setText("PAST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        String handle=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("handle","Null");
        handletextview.setText(handle);

    }

    public void SignOut(View view){
        String handle=null;
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("handle",null).apply();
        Intent intent =new Intent(MainActivity.this, loginActivity.class);
        Pair[] pairs=new Pair[2];
        pairs[0]= new Pair<View,String>(profileImage,"logoAnimation");
        pairs[1]= new Pair<View,String>(handletextview,"handleTransition");
        ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
        startActivity(intent,options.toBundle());
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        finish();
    }
}