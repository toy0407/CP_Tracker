package com.toy0407.cptracker;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {

    private static final String TAG = "loginActivity";
    EditText handleEdittext;
    String handle;
    Button loginButton;
    TextInputLayout textInputLayout;
    LottieAnimationView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        hideSoftKeyboard(this);

        handleEdittext=findViewById(R.id.handleedittext);
        loginButton=findViewById(R.id.loginButton);
        textInputLayout=findViewById(R.id.textInputLayout);
        appLogo=findViewById(R.id.appLogo);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle=handleEdittext.getText().toString().trim();
                Log.i(TAG, "onCreate: "+handle);

                if (handle!=null && handle!=""){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url="https://codeforces.com/api/user.rating?handle="+handle;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, response -> {
                                //Store in shared preferences & Launch Mainactivity
                                Log.i(TAG, "onClick: success");
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("handle",handle).apply();

                                Intent intent=new Intent(loginActivity.this,MainActivity.class);
                                Pair[] pairs=new Pair[2];
                                pairs[0]= new Pair<View,String>(appLogo,"logoAnimation");
                                pairs[1]= new Pair<View,String>(handleEdittext,"handleTransition");
                                ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(loginActivity.this,pairs);
                                intent.putExtra("handle",handle);
                                startActivity(intent,options.toBundle());
                                finish();


                            }, error ->{
                                Log.i(TAG, "onErrorResponse: " + error);
                                handle="";
                                textInputLayout.setError("Enter handle properly");
                            });

                    queue.add(jsonObjectRequest);
                }
            }
        });

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }


//    public void switchToSignupActivity(View view){
//        Intent intent=new Intent(loginActivity.this, signupActivity.class);
//        startActivity(intent);
//        finish();
//    }
}