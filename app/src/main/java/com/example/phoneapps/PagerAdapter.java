package com.example.phoneapps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull  FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ContactsFragment tab1 = new ContactsFragment();
                return tab1;

            case 1:
                RecentcallsFragment tab2 = new RecentcallsFragment();
                return tab2;

            case 2:
                NumpadsFragment tab3 = new NumpadsFragment();
                return tab3;

                default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
