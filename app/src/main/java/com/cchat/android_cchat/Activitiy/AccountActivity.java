package com.cchat.android_cchat.Activitiy;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cchat.android_cchat.R;


/**
 * Created by hyein on 2016. 9. 4..
 */
public class AccountActivity extends FragmentActivity implements View.OnTouchListener, View.OnClickListener, View.OnFocusChangeListener {

    private ViewFlipper flipper;
    float xAtDown, xAtUp;
    ImageView iv_sign_up,iv_log_in;
    int flipperIndex, count;

    Button bt_sign_up, bt_log_in, bt_profile_pre;
    ImageButton ib_profile_ok;
    EditText et_sign_up_email, et_sign_up_pw, et_sign_up_pw_2, et_log_in_email, et_log_in_pw;
    EditText et_profile_name, et_profile_birth, et_profile_gender;

    int et_icons[]
            = new int[] {android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_info, android.R.drawable.ic_dialog_info};

    int et_focus_icons[]
            = new int[] {android.R.drawable.ic_media_ff, android.R.drawable.ic_media_next,
            android.R.drawable.ic_menu_zoom, android.R.drawable.ic_menu_zoom, android.R.drawable.ic_menu_agenda,
            android.R.drawable.ic_menu_zoom, android.R.drawable.ic_menu_zoom, android.R.drawable.ic_menu_agenda};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        init();
        setFlipper();

        /**
         *
         * 지우기
         *
         */

        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountActivity.this.finish();
                startActivity(new Intent(AccountActivity.this, TabActivity.class));
            }
        });
    }

    private void init() {
        bt_sign_up = (Button) findViewById(R.id.account_bt_signup_ok);
        bt_log_in = (Button) findViewById(R.id.account_bt_login_ok);

        et_sign_up_email = (EditText) findViewById(R.id.account_et_signup_email);
        et_sign_up_pw = (EditText) findViewById(R.id.account_et_signup_pw);
        et_sign_up_pw_2 = (EditText) findViewById(R.id.account_et_signup_pw2);

        et_log_in_email = (EditText) findViewById(R.id.account_et_login_email);
        et_log_in_pw = (EditText) findViewById(R.id.account_et_login_pw);

        et_profile_name = (EditText) findViewById(R.id.profile_et_name);
        et_profile_birth = (EditText) findViewById(R.id.profile_et_birth);
        et_profile_gender = (EditText) findViewById(R.id.profile_et_gender);

        bt_profile_pre = (Button) findViewById(R.id.profile_ib_pre);
        ib_profile_ok = (ImageButton) findViewById(R.id.profile_ib_ok);

        bt_sign_up.setOnClickListener(this);
        bt_log_in.setOnClickListener(this);
        bt_profile_pre.setOnClickListener(this);
        ib_profile_ok.setOnClickListener(this);

        et_sign_up_email.setOnFocusChangeListener(this);
        et_sign_up_pw.setOnFocusChangeListener(this);
        et_sign_up_pw_2.setOnFocusChangeListener(this);

        et_log_in_email.setOnFocusChangeListener(this);
        et_log_in_pw.setOnFocusChangeListener(this);

        et_profile_name.setOnFocusChangeListener(this);
        et_profile_birth.setOnFocusChangeListener(this);
        et_profile_gender.setOnFocusChangeListener(this);
    }

    private void setFlipper() {
        flipper = (ViewFlipper) findViewById(R.id.account_vf);

        // 인디케이터
        iv_sign_up = (ImageView) findViewById(R.id.account_iv_signup);
        iv_log_in = (ImageView) findViewById(R.id.account_iv_login);

        flipper.setDisplayedChild(0); //0번째 자식뷰를 보여줌.
        flipper.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(view!=flipper) return false;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            xAtDown=event.getX();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            xAtUp = event.getX();

            moveFlipper(xAtDown, xAtUp);
        }
        return true;
    }

    private void moveFlipper(float xAtDown, float xAtUp) {
        if(xAtDown > xAtUp){
            flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
            count++;
            if(count < 2){
                flipper.showNext();
                flipperIndex = flipper.getDisplayedChild();
                setFlipperIndex(flipperIndex);}
            else{
                count--;
            }
        }
        else if(xAtDown < xAtUp){
            flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
            count--;
            if(count >- 1){
                flipper.showPrevious();
                flipperIndex = flipper.getDisplayedChild();
                setFlipperIndex(flipperIndex);}
            else{
                count++;
            }
        }
    }

    private void setFlipperIndex(int index){
        // 페이지 나타내는 이미지뷰 초기화
        iv_sign_up.setImageResource(android.R.drawable.presence_audio_online);
        iv_log_in.setImageResource(android.R.drawable.presence_video_online);

        // 인덱스의 값에 따라 해당 이미지뷰에 표시
        switch(index){
            case 0:
                iv_sign_up.setImageResource(android.R.drawable.presence_audio_busy);
                break;
            case 1:
                iv_log_in.setImageResource(android.R.drawable.presence_video_busy);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_bt_signup_ok:
                if (!et_sign_up_email.getText().toString().equals("") &&
                        !et_sign_up_pw.getText().toString().equals("") && !et_sign_up_pw_2.getText().toString().equals("")) {
                    findViewById(R.id.account_ly_profile).setVisibility(View.VISIBLE);
                    findViewById(R.id.account_ly_signup).setVisibility(View.GONE);
                } else {
                    Toast.makeText(AccountActivity.this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.account_bt_login_ok:
                if (!et_log_in_email.getText().toString().equals("") && !et_log_in_pw.getText().toString().equals("")) {
                    AccountActivity.this.finish();
                    startActivity(new Intent(AccountActivity.this, ConnectActivity.class));
                } else {
                    Toast.makeText(AccountActivity.this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_ib_pre:
                findViewById(R.id.account_ly_profile).setVisibility(View.GONE);
                findViewById(R.id.account_ly_signup).setVisibility(View.VISIBLE);

                et_profile_name.setText("");
                et_profile_birth.setText("");
                et_profile_gender.setText("");
                break;
            case R.id.profile_ib_ok:

                /**
                 * signUp()
                 * **/

                moveFlipper(1.0f, 0.0f); // Down > Up 인 임의 값
        }
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
