package com.cchat.android_cchat.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cchat.android_cchat.Fragment.LogInFragment;
import com.cchat.android_cchat.Fragment.SignUpFragment;

/**
 * Created by hyein on 2016. 9. 4..
 */
public class AccountFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private Context context;

    private Fragment tabFragments[] = new Fragment[] {SignUpFragment.newInstance(), LogInFragment.newInstance()};

    public AccountFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments[position];
    }
}