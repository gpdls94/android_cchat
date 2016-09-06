package com.cchat.android_cchat.Activitiy;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 9. 5..
 */
public class ConnectActivity extends FragmentActivity implements View.OnFocusChangeListener {

    ImageButton ib_connect_ok;
    EditText et_connect_number1, et_connect_number2;

    int et_icons[]
            = new int[] {android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_info};

    int et_focus_icons[]
            = new int[] {android.R.drawable.ic_partial_secure, android.R.drawable.ic_media_next};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        init();
    }

    private void init() {
        ib_connect_ok = (ImageButton) findViewById(R.id.connect_ib_ok);
        ib_connect_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectActivity.this, TabActivity.class));
                ConnectActivity.this.finish();
            }
        });

        et_connect_number1 = (EditText) findViewById(R.id.connect_et_number);
        et_connect_number2 = (EditText) findViewById(R.id.connect_et_number2);

        et_connect_number1.setOnFocusChangeListener(this);
        et_connect_number2.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        EditText et = (EditText) view;
        int iconIndex = new Integer(et.getTag().toString());

        if (b) {
            Drawable focus_icon = getDrawable(et_focus_icons[iconIndex]);
            focus_icon.setBounds(0, 0, focus_icon.getIntrinsicWidth(), focus_icon.getIntrinsicHeight());

            et.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cchat_main_color)));
            et.setCompoundDrawables(focus_icon, null, null, null);
        } else {
            if (et.getText().toString().equals("")) {
                Drawable icon = getDrawable(et_icons[iconIndex]);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

                et.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cchat_hint_text_color)));
                et.setCompoundDrawables(icon, null, null, null);
            }
        }
    }
}
