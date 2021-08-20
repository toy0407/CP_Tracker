package com.toy0407.cptracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PastContestsFragment extends Fragment implements PastRecyclerViewAdapter.onContestListener{

    private static final String TAG = "PastContestsFragment";
    private RecyclerView pastcfRecyclerView;
    private PastRecyclerViewAdapter pastRecyclerViewAdapter;
    private LottieAnimationView pastlottieloadingAnimationView;
    private String handle=new String();
    private ArrayList<PastCodeforcesContestsClass> cfArrayList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_past_contest,container,false);


        pastcfRecyclerView=rootView.findViewById(R.id.pastcfrecyclerview);
        pastlottieloadingAnimationView=rootView.findViewById(R.id.pastlottieloadinganimation);

        getPastCodeforcesData();

        return rootView;
    }

    private void getPastCodeforcesData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Intent intent = getActivity().getIntent();
        handle=intent.getStringExtra("handle");
        String url="https://codeforces.com/api/user.rating?handle="+handle;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray array = response.getJSONArray("result");
//                            Log.i(TAG, "onResponse: "+response.getJSONArray("result").getJSONObject(2).getInt("newRating"));

                        for (int i=array.length()-1;i>=0;i--){
                            cfArrayList.add(new PastCodeforcesContestsClass(array.getJSONObject(i).getInt("contestId"),array.getJSONObject(i).getString("contestName"),array.getJSONObject(i).getInt("rank"),array.getJSONObject(i).getInt("oldRating"),array.getJSONObject(i).getInt("newRating")));
                        }

                        setAndDisplayRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.i(TAG, "onErrorResponse: " + error));

        queue.add(jsonObjectRequest);

    }

    @Override
    public void onContestClick(int position) {

    }

    private void setAndDisplayRecyclerView(){
        pastRecyclerViewAdapter =new PastRecyclerViewAdapter(getContext(),cfArrayList,this::onContestClick);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pastlottieloadingAnimationView.setVisibility(View.GONE);
        pastcfRecyclerView.setVisibility(View.VISIBLE);
        pastcfRecyclerView.setLayoutManager(linearLayoutManager);
        pastcfRecyclerView.setAdapter(pastRecyclerViewAdapter);
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_fall_down);
        pastcfRecyclerView.setLayoutAnimation(layoutAnimationController);
    }
}
