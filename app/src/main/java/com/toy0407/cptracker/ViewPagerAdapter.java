package com.toy0407.cptracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(MainActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        if (position==0) return new UpcomingContestsFragment();
        return new PastContestsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
