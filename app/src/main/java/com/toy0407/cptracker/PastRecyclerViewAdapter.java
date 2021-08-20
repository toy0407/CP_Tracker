package com.toy0407.cptracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class PastRecyclerViewAdapter extends RecyclerView.Adapter<PastRecyclerViewAdapter.Viewholder>{
    private final Context context;
    private final ArrayList<PastCodeforcesContestsClass> cfArrayList;
    private UpcomingRecyclerViewAdapter.onContestListener onContestListener;

    // Constructor
    public PastRecyclerViewAdapter(Context context, ArrayList<PastCodeforcesContestsClass> cfArrayList, UpcomingRecyclerViewAdapter.onContestListener onContestListener) {
        this.context = context;
        this.cfArrayList = cfArrayList;
        this.onContestListener=onContestListener;
    }

    @NonNull
    @Override
    public PastRecyclerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_cardlayout, parent, false);
        return new Viewholder(view,onContestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PastCodeforcesContestsClass model = cfArrayList.get(position);
        holder.pastExamName.setText(model.getName());
        holder.pastRank.setText("Rank: "+String.valueOf(model.getRank()));
        holder.oldrating.setText(String.valueOf(model.getOldRating()));
        holder.newrating.setText(String.valueOf(model.getNewRating()));
        int delta=model.getNewRating()-model.getOldRating();
        if (delta<0){
            holder.ratingDelta.setText(String.valueOf(delta));
            holder.ratingDelta.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        else {
            holder.ratingDelta.setText("+"+String.valueOf(delta));
            holder.ratingDelta.setTextColor(ContextCompat.getColor(context,R.color.green));
        }
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
        private final TextView pastExamName;
        private final TextView pastRank;
        private final TextView oldrating;
        private final TextView newrating;
        private final TextView ratingDelta;
        UpcomingRecyclerViewAdapter.onContestListener onContestListener;

        public Viewholder(@NonNull View itemView, UpcomingRecyclerViewAdapter.onContestListener onContestListener) {
            super(itemView);
            pastExamName = itemView.findViewById(R.id.pastexamName);
            pastRank = itemView.findViewById(R.id.pastrank);
            oldrating = itemView.findViewById(R.id.oldRating);
            newrating = itemView.findViewById(R.id.newRating);
            ratingDelta = itemView.findViewById(R.id.ratingDelta);
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
