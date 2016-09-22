package com.cchat.android_cchat.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.cchat.android_cchat.Fragment.AlbumFragment;
import com.cchat.android_cchat.Fragment.HomeFragment;
import com.cchat.android_cchat.Fragment.PageFragment;
import com.cchat.android_cchat.Fragment.SettingFragment;
import com.cchat.android_cchat.View.BadgeTabLayout;

import java.util.ArrayList;

/**
 * Created by hyein on 2016. 9. 1..
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    private Context context;

    private Fragment tabFragments[] = new Fragment[] {HomeFragment.newInstance(), PageFragment.newInstance(),
            AlbumFragment.newInstance(), SettingFragment.newInstance()};

    private int tabIcons[] = new int[] {android.R.drawable.presence_offline, android.R.drawable.btn_star_big_off,
            android.R.drawable.ic_lock_silent_mode, android.R.drawable.star_off};
    private int tabOnIcons[] = new int[] {android.R.drawable.presence_online, android.R.drawable.btn_star_big_on,
            android.R.drawable.ic_lock_silent_mode_off, android.R.drawable.star_on};

    private ArrayList<BadgeTabLayout> tabViews = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return "";
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        BadgeTabLayout view = new BadgeTabLayout(context);

//        view.setImage(tabIcons[0]);
//        view.setCount(0);

        view.setCount(0);
        tabViews.add(view);

        if (position == PAGE_COUNT-1) {
            setTabIcon(0);
        }

        return view;
    }

    public void setTabIcon(int position) {

        for (int i = 0; i < PAGE_COUNT; i++) {
            if (i != position) { //OFF
                tabViews.get(i).setImage(tabIcons[i]);
            } else { //ON
                tabViews.get(i).setImage(tabOnIcons[i]);
            }
        }
    }
}