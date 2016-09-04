package com.cchat.android_cchat.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.cchat.android_cchat.Fragment.ChatFragment;
import com.cchat.android_cchat.Fragment.HomeFragment;
import com.cchat.android_cchat.Fragment.PageFragment;
import com.cchat.android_cchat.Fragment.SettingFragment;
import com.cchat.android_cchat.View.BadgeTabLayout;

/**
 * Created by hyein on 2016. 9. 1..
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    private Context context;

    private Fragment tabFragments[] = new Fragment[] {HomeFragment.newInstance(),
            ChatFragment.newInstance(), PageFragment.newInstance(), SettingFragment.newInstance()};
    private int tabIcons[] = new int[] {android.R.drawable.ic_dialog_info};

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

        view.setImage(tabIcons[0]);
        view.setCount(0);

        return view;
    }
}