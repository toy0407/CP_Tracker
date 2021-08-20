package com.toy0407.cptracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class UpcomingRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingRecyclerViewAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<UpcomingCodeforcesContestsClass> cfArrayList;
    private onContestListener onContestListener;

    // Constructor
    public UpcomingRecyclerViewAdapter(Context context, ArrayList<UpcomingCodeforcesContestsClass> cfArrayList, onContestListener onContestListener) {
        this.context = context;
        this.cfArrayList = cfArrayList;
        this.onContestListener=onContestListener;
    }

    @NonNull
    @Override
    public UpcomingRecyclerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_cardlayout, parent, false);
        return new Viewholder(view,onContestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingRecyclerViewAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        UpcomingCodeforcesContestsClass model = cfArrayList.get(position);
        holder.examName.setText(model.getName());
        holder.duration.setText("Duration: "+model.getDuration() / 3600 +" hrs");
        holder.startTime.setText(String.valueOf(new Date((long)model.getStartTimeSeconds()*1000)));

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return cfArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        private ImageView courseIV;
        private final TextView examName;
        private final TextView duration;
        private final TextView startTime;
        onContestListener onContestListener;

        public Viewholder(@NonNull View itemView,onContestListener onContestListener) {
            super(itemView);
            examName = itemView.findViewById(R.id.upcomingexamName);
            duration = itemView.findViewById(R.id.upcomingduration);
            startTime = itemView.findViewById(R.id.upcomingstartTime);
            this.onContestListener=onContestListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onContestListener.onContestClick(getAdapterPosition());
        }
    }

    public interface onContestListener{
        void onContestClick(int position);
    }
}


