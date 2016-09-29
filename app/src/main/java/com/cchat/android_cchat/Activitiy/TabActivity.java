package com.cchat.android_cchat.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.cchat.android_cchat.Adapter.TabFragmentPagerAdapter;
import com.cchat.android_cchat.Common.CreateDialog;
import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 8. 24..
 */
public class TabActivity extends AppCompatActivity {

    FloatingActionButton btn_chat, btn_add, btn_ann, btn_plan;
    LinearLayout ly_cal;
    TabLayout ly_tab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

//        checkUpdate();

        init();
        initTab();

        /**
         *  로그인 함수 실행
         * **/

//        setLogin thread = new setLogin();
//        thread.setDaemon(true);
//        thread.start();
    }

    private void init() {
        ly_tab = (TabLayout) findViewById(R.id.sliding_tabs);

        btn_ann = (FloatingActionButton) findViewById(R.id.tab_floating_ann);
        btn_plan = (FloatingActionButton) findViewById(R.id.tab_floating_plan);

        ly_cal = (LinearLayout) findViewById(R.id.tab_ly_cal);
        ly_cal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ly_cal.setVisibility(View.GONE);
                ly_tab.setVisibility(View.VISIBLE);
                return false;
            }
        });

        btn_add = (FloatingActionButton) findViewById(R.id.tab_floating_cal);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ly_cal.setVisibility(View.VISIBLE);
                ly_tab.setVisibility(View.GONE);
            }
        });

        btn_chat = (FloatingActionButton) findViewById(R.id.tab_floating);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabActivity.this, ChatActivity.class));
            }
        });
    }

    private void initTab() {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final TabFragmentPagerAdapter pagerAdapter =
                new TabFragmentPagerAdapter(getSupportFragmentManager(), TabActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setTag(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ((int) tab.getTag()) {
                    case 0:case 3:
                        btn_add.setVisibility(View.GONE);
                        break;
                    case 1:case 2:
                        btn_add.setVisibility(View.VISIBLE);
                        break;
                }

                pagerAdapter.setTabIcon((int) tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        ((BadgeTabLayout) tabLayout.getTabAt(0).getCustomView()).setCount(100);
//        ((BadgeTabLayout) tabLayout.getTabAt(1).getCustomView()).setCount(12);
//        ((BadgeTabLayout) tabLayout.getTabAt(3).getCustomView()).setCount("N");
    }

    @Override
    public void onBackPressed() {
        (new CreateDialog(TabActivity.this)).quit();
    }
}
