package com.cchat.android_cchat.Activitiy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cchat.android_cchat.Adapter.TabFragmentPagerAdapter;
import com.cchat.android_cchat.Common.CreateDialog;
import com.cchat.android_cchat.R;
import com.cchat.android_cchat.View.BadgeTabLayout;

/**
 * Created by hyein on 2016. 8. 24..
 */
public class TabActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

//        checkUpdate();

        initTab();

        /**
         *  로그인 함수 실행
         * **/

//        setLogin thread = new setLogin();
//        thread.setDaemon(true);
//        thread.start();
    }

    private void initTab() {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabFragmentPagerAdapter pagerAdapter =
                new TabFragmentPagerAdapter(getSupportFragmentManager(), TabActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.cchat_main_color));

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

//        ((BadgeTabLayout) tabLayout.getTabAt(0).getCustomView()).setCount(100);
        ((BadgeTabLayout) tabLayout.getTabAt(1).getCustomView()).setCount(12);
//        ((BadgeTabLayout) tabLayout.getTabAt(3).getCustomView()).setCount("N");
    }

    @Override
    public void onBackPressed() {
        (new CreateDialog(TabActivity.this)).quit();
    }
}
