package com.example.madcamp24_week1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RegionFragment();
            case 1:
                return new GalleryFragment();
            case 2:
                return new ContactFragment();
            default:
                return new RegionFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
