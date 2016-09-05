package com.cchat.android_cchat.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.cchat.android_cchat.R;


/**
 * Created by hyein on 2016. 9. 4..
 */
public class AccountActivity extends FragmentActivity implements View.OnTouchListener, View.OnClickListener {

    private ViewFlipper flipper;
    float xAtDown, xAtUp;
    ImageView iv_sign_up,iv_log_in;
    int flipperIndex, count;

    Button bt_sign_up, bt_log_in;

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
                startActivity(new Intent(AccountActivity.this, TabActivity.class));
            }
        });
    }

    private void init() {
        bt_sign_up = (Button) findViewById(R.id.account_bt_signup_ok);
        bt_log_in = (Button) findViewById(R.id.account_bt_login_ok);

        bt_sign_up.setOnClickListener(this);
        bt_log_in.setOnClickListener(this);
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
                moveFlipper(1.0f, 0.0f); // Down > Up 인 임의 값
                break;
            case R.id.account_bt_login_ok:
                startActivity(new Intent(AccountActivity.this, ConnectProfileActivity.class));
                AccountActivity.this.finish();
        }
    }
}
