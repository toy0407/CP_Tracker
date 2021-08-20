package com.toy0407.cptracker;

import android.content.Intent;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UpcomingContestsFragment extends Fragment implements UpcomingRecyclerViewAdapter.onContestListener {

    private static final String TAG = "UpcomingContestsFragment";
    private ArrayList<UpcomingCodeforcesContestsClass> cfArrayList = new ArrayList<>();
    private RecyclerView cfRecyclerView;
    private UpcomingRecyclerViewAdapter upcomingRecyclerViewAdapter;
    private LottieAnimationView lottieloadingAnimationView;


    public UpcomingContestsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upcoming_contest, container, false);

        cfRecyclerView = rootView.findViewById(R.id.cfRecyclerView);
        lottieloadingAnimationView = rootView.findViewById(R.id.upcominglottieloadinganimation);

        getUpcomingCodeforcesData();

        return rootView;
    }


    private void getUpcomingCodeforcesData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://codeforces.com/api/contest.list?gym=false";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray array = response.getJSONArray("result");
//                            Log.i(TAG, "onResponse: "+response.getJSONArray("result").getJSONObject(2).getString("phase"));
                        for (int i = 0; i < 10; i++) {
                            if (array.getJSONObject(i).getString("phase").compareTo("BEFORE") == 0)
                                cfArrayList.add(new UpcomingCodeforcesContestsClass(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getInt("durationSeconds"), array.getJSONObject(i).getInt("startTimeSeconds")));

                        }
                        Collections.sort(cfArrayList, Comparator.comparingInt(UpcomingCodeforcesContestsClass::getStartTimeSeconds));
                        setAndDisplayRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.i(TAG, "onErrorResponse: " + error));
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onContestClick(int position) {
//        Log.i(TAG, "onContestClick: Clicked");
        String url = "https://codeforces.com/contests";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void setAndDisplayRecyclerView() {
        upcomingRecyclerViewAdapter = new UpcomingRecyclerViewAdapter(getContext(), cfArrayList, this::onContestClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lottieloadingAnimationView.setVisibility(View.GONE);
        cfRecyclerView.setVisibility(View.VISIBLE);
        cfRecyclerView.setLayoutManager(linearLayoutManager);
        cfRecyclerView.setAdapter(upcomingRecyclerViewAdapter);
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_fall_down);
        cfRecyclerView.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void onResume() {
        super.onResume();
        lottieloadingAnimationView.setVisibility(View.GONE);
    }
}
