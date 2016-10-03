package com.cchat.android_cchat.Activitiy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 10. 3..
 */
public class AnniActivity extends FragmentActivity {

    TextView tv_alarm, tv_repeat, tv_withPlan, tv_kind, tv_alarm_title;
    Spinner spinner;
    EditText ed_title;
    Switch sw_repeat, sw_withPlan;
    ImageButton toolbar_back;
    ImageButton[] bts_kind;
    Drawable[] icons_kind;

    LinearLayout ly_plan;
    ScrollView scrollView;

    int last_pos = -1; // kind _ default

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);

        new PlanActivity(AnniActivity.this);
        init();
    }

    private void init() {
        scrollView = (ScrollView) findViewById(R.id.ann_scroll);
        ly_plan = (LinearLayout) findViewById(R.id.ann_ly_plan);
        toolbar_back = (ImageButton) findViewById(R.id.ann_tb_back);
        ed_title = (EditText) findViewById(R.id.ann_ed_title);
        tv_repeat = (TextView) findViewById(R.id.ann_tv_repeat);
        tv_withPlan = (TextView) findViewById(R.id.ann_tv_withP);
        tv_alarm_title = (TextView) findViewById(R.id.ann_tv_alarmT);
        tv_alarm = (TextView) findViewById(R.id.ann_tv_alarm);
        tv_kind = (TextView) findViewById(R.id.ann_tv_kind);
        spinner = (Spinner) findViewById(R.id.ann_spinner);
        sw_repeat = (Switch) findViewById(R.id.ann_sw_repeat);
        sw_withPlan = (Switch) findViewById(R.id.ann_sw_withP);
        bts_kind = new ImageButton[] {(ImageButton) findViewById(R.id.ann_ib_kind_1), (ImageButton) findViewById(R.id.ann_ib_kind_2),
                (ImageButton) findViewById(R.id.ann_ib_kind_3), (ImageButton) findViewById(R.id.ann_ib_kind_4)};
        icons_kind = new Drawable[bts_kind.length];

        for (int i = 0; i < icons_kind.length; i++)
            icons_kind[i] = bts_kind[i].getDrawable();

        spinnerInit();
        edSetListener();
        switchSetListener();

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void spinnerInit() {
        //스피너 어댑터 설정
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alarm, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //스피너 이벤트 발생
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_alarm.setText(getResources().getStringArray(R.array.alarm)[position]);

                if (position == 0) {
                    tv_alarm_title.setTextColor(getResources().getColor(R.color.cchat_hint_text_color));
                    tv_alarm.setTextColor(getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    tv_alarm_title.setTextColor(getResources().getColor(android.R.color.black));
                    tv_alarm.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void edSetListener() {
        ed_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Drawable icon;
                if (TextUtils.isEmpty(charSequence.toString())) {
                    icon = getDrawable(android.R.drawable.ic_menu_my_calendar);
                    ed_title.setTextColor(getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = getDrawable(android.R.drawable.ic_media_next);
                    ed_title.setTextColor(getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                ed_title.setCompoundDrawables(icon, null, null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void switchSetListener() {
        sw_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Drawable icon;
                if (b) {
                    icon = getDrawable(android.R.drawable.ic_media_next);
                    tv_repeat.setTextColor(getResources().getColor(android.R.color.black));
                } else {
                    icon = getDrawable(android.R.drawable.ic_menu_compass);
                    tv_repeat.setTextColor(getResources().getColor(R.color.cchat_hint_text_color));
                }
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                tv_repeat.setCompoundDrawables(icon, null, null, null);
            }
        });

        sw_withPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Drawable icon;
                if (b) {
                    icon = getDrawable(android.R.drawable.ic_media_next);
                    tv_withPlan.setTextColor(getResources().getColor(android.R.color.black));
                    ly_plan.setVisibility(View.VISIBLE);

                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, ly_plan.getHeight());
                        }
                    });
                } else {
                    icon = getDrawable(android.R.drawable.ic_menu_compass);
                    tv_withPlan.setTextColor(getResources().getColor(R.color.cchat_hint_text_color));
                    ly_plan.setVisibility(View.GONE);
                }
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                tv_withPlan.setCompoundDrawables(icon, null, null, null);
            }
        });
    }

    public void kindOnClick(View view) {
        ImageButton bt_kind = (ImageButton) findViewById(view.getId());

        if (last_pos != -1)
            bts_kind[last_pos].setImageDrawable(icons_kind[last_pos]);

        switch (view.getId()) {
            case R.id.ann_ib_kind_1:
                last_pos = 0;
                bt_kind.setImageResource(android.R.drawable.ic_menu_compass);
                break;
            case R.id.ann_ib_kind_2:
                last_pos = 1;
                bt_kind.setImageResource(android.R.drawable.ic_menu_agenda);
                break;
            case R.id.ann_ib_kind_3:
                last_pos = 2;
                bt_kind.setImageResource(android.R.drawable.ic_menu_add);
                break;
            case R.id.ann_ib_kind_4:
                last_pos = 3;
                bt_kind.setImageResource(android.R.drawable.ic_menu_view);
                break;
        }

        Drawable icon = getDrawable(android.R.drawable.ic_media_next);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        tv_kind.setTextColor(getResources().getColor(android.R.color.black));
        tv_kind.setCompoundDrawables(icon, null, null, null);
    }
}
