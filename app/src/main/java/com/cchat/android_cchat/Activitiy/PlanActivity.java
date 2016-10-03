package com.cchat.android_cchat.Activitiy;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 10. 4..
 */
public class PlanActivity extends FragmentActivity {

    Activity context;
    Spinner sp_to, sp_alarm, sp_repeat;
    Toolbar toolbar;
    TextView tv_to, tv_toT, tv_kind, tv_alarm, tv_alarmT, tv_repeat, tv_repeatT;
    EditText ed_title, ed_content, ed_loc;
    ImageButton toolbar_back;

    ImageButton[] bts_kind;
    Drawable[] icons_kind;

    int last_pos = -1; // kind _ default
    boolean isActivity;

    public PlanActivity() {}

    public PlanActivity(Activity context) {
        this.context = context;
        this.isActivity = false;
        init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_plan);

        if (this.context == null) {
            this.context = PlanActivity.this;
            this.isActivity = true;
        }

        init();
    }

    private void init() {
        toolbar = (Toolbar) context.findViewById(R.id.plan_toolbar);
        toolbar_back = (ImageButton) context.findViewById(R.id.plan_tb_back);

        if (!this.isActivity)
            toolbar.setVisibility(View.GONE);
        else
            toolbar.setVisibility(View.VISIBLE);

        tv_alarmT = (TextView) context.findViewById(R.id.plan_tv_alarmT);
        tv_alarm = (TextView) context.findViewById(R.id.plan_tv_alarm);
        tv_repeatT = (TextView) context.findViewById(R.id.plan_tv_repeatT);
        tv_repeat = (TextView) context.findViewById(R.id.plan_tv_repeat);
        sp_to = (Spinner) context.findViewById(R.id.plan_sp_to);
        sp_alarm = (Spinner) context.findViewById(R.id.plan_sp_alarm);
        sp_repeat = (Spinner) context.findViewById(R.id.plan_sp_repeat);
        tv_to = (TextView) context.findViewById(R.id.plan_tv_to);
        tv_kind = (TextView) context.findViewById(R.id.plan_tv_kind);
        tv_toT = (TextView) context.findViewById(R.id.plan_tv_toT);
        ed_title = (EditText) context.findViewById(R.id.plan_ed_title);
        ed_loc = (EditText) context.findViewById(R.id.plan_ed_loc);
        ed_content = (EditText) context.findViewById(R.id.plan_ed_content);
        bts_kind = new ImageButton[] {(ImageButton) context.findViewById(R.id.plan_ib_kind1), (ImageButton) context.findViewById(R.id.plan_ib_kind2),
                (ImageButton) context.findViewById(R.id.plan_ib_kind3), (ImageButton) context.findViewById(R.id.plan_ib_kind4)};
        icons_kind = new Drawable[bts_kind.length];

        for (int i = 0; i < icons_kind.length; i++)
            icons_kind[i] = bts_kind[i].getDrawable();

        for (int i = 0; i < bts_kind.length; i++) {
            final int finalI = i;
            bts_kind[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton bt_kind = (ImageButton) context.findViewById(view.getId());

                    if (last_pos != -1)
                        bts_kind[last_pos].setImageDrawable(icons_kind[last_pos]);

                    switch (finalI) {
                        case 0:
                            bt_kind.setImageResource(android.R.drawable.ic_menu_compass);
                            break;
                        case 1:
                            bt_kind.setImageResource(android.R.drawable.ic_menu_agenda);
                            break;
                        case 2:
                            bt_kind.setImageResource(android.R.drawable.ic_menu_add);
                            break;
                        case 3:
                            bt_kind.setImageResource(android.R.drawable.ic_menu_view);
                            break;
                    }

                    last_pos = finalI;

                    Drawable icon = context.getDrawable(android.R.drawable.ic_media_next);
                    icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                    tv_kind.setTextColor(context.getResources().getColor(android.R.color.black));
                    tv_kind.setCompoundDrawables(icon, null, null, null);
                }
            });
        }

        spinnerInit();
        edSetListener();

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void spinnerInit() {
        //스피너 어댑터 설정
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.to, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_to.setAdapter(adapter);

        //스피너 이벤트 발생
        sp_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_to.setText(context.getResources().getStringArray(R.array.to)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** alarm Spinner **/
        //스피너 어댑터 설정
        ArrayAdapter a_adapter = ArrayAdapter.createFromResource(context, R.array.alarm, android.R.layout.simple_spinner_item);
        a_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_alarm.setAdapter(a_adapter);

        //스피너 이벤트 발생
        sp_alarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Drawable icon;
                tv_alarm.setText(context.getResources().getStringArray(R.array.alarm)[position]);

                if (position == 0) {
                    icon = context.getDrawable(android.R.drawable.ic_menu_compass);
                    tv_alarmT.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                    tv_alarm.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = context.getDrawable(android.R.drawable.ic_media_next);
                    tv_alarmT.setTextColor(context.getResources().getColor(android.R.color.black));
                    tv_alarm.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                tv_alarmT.setCompoundDrawables(icon, null, null, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** repeat Spinner **/
        //스피너 어댑터 설정
        ArrayAdapter r_adapter = ArrayAdapter.createFromResource(context, R.array.repeat, android.R.layout.simple_spinner_item);
        r_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_repeat.setAdapter(r_adapter);

        //스피너 이벤트 발생
        sp_repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Drawable icon;
                tv_repeat.setText(context.getResources().getStringArray(R.array.repeat)[position]);

                if (position == 0) {
                    icon = context.getDrawable(android.R.drawable.ic_menu_compass);
                    tv_repeatT.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                    tv_repeat.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = context.getDrawable(android.R.drawable.ic_media_next);
                    tv_repeatT.setTextColor(context.getResources().getColor(android.R.color.black));
                    tv_repeat.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                tv_repeatT.setCompoundDrawables(icon, null, null, null);
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
                    icon = context.getDrawable(android.R.drawable.ic_menu_my_calendar);
                    ed_title.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = context.getDrawable(android.R.drawable.ic_media_next);
                    ed_title.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                ed_title.setCompoundDrawables(icon, null, null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ed_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Drawable icon;
                if (TextUtils.isEmpty(charSequence.toString())) {
                    icon = context.getDrawable(android.R.drawable.ic_menu_my_calendar);
                    ed_content.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = context.getDrawable(android.R.drawable.ic_media_next);
                    ed_content.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                ed_content.setCompoundDrawables(icon, null, null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ed_loc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Drawable icon;
                if (TextUtils.isEmpty(charSequence.toString())) {
                    icon = context.getDrawable(android.R.drawable.ic_menu_my_calendar);
                    ed_loc.setTextColor(context.getResources().getColor(R.color.cchat_hint_text_color));
                } else {
                    icon = context.getDrawable(android.R.drawable.ic_media_next);
                    ed_loc.setTextColor(context.getResources().getColor(android.R.color.black));
                }

                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                ed_loc.setCompoundDrawables(icon, null, null, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
