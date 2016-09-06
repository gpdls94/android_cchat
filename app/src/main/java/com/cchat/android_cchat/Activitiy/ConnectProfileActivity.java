package com.cchat.android_cchat.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 9. 5..
 */
public class ConnectProfileActivity extends FragmentActivity implements View.OnTouchListener {

    private ViewFlipper flipper;
    float xAtDown, xAtUp;
    int flipperIndex, count;

    ImageButton ib_connect_ok, ib_profile_ok;
    EditText et_connect_number1, et_connect_number2;
    EditText et_profile_name, et_profile_birth, et_profile_gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_profile);

        init();
        setFlipper();
    }

    private void init() {
        ib_connect_ok = (ImageButton) findViewById(R.id.connect_ib_ok);
        ib_connect_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveFlipper(0f, 0f, true);
            }
        });

        ib_profile_ok = (ImageButton) findViewById(R.id.profile_ib_ok);
        ib_profile_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConnectProfileActivity.this, TabActivity.class));
                ConnectProfileActivity.this.finish();
            }
        });

        et_connect_number1 = (EditText) findViewById(R.id.connect_et_number);
        et_connect_number2 = (EditText) findViewById(R.id.connect_et_number2);

        et_profile_name = (EditText) findViewById(R.id.profile_et_name);
        et_profile_birth = (EditText) findViewById(R.id.profile_et_birth);
        et_profile_gender = (EditText) findViewById(R.id.profile_et_gender);
    }

    private void setFlipper() {
        flipper = (ViewFlipper) findViewById(R.id.connect_vf);

        flipper.setDisplayedChild(0); //0번째 자식뷰를 보여줌.
        flipper.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view != flipper) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xAtDown = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            xAtUp = event.getX();

            moveFlipper(xAtDown, xAtUp, false);
        }
        return true;
    }

    private void moveFlipper(float xAtDown, float xAtUp, boolean isButton) {

        if ((!isButton && xAtDown > xAtUp) || isButton) {
            if (!et_connect_number1.getText().toString().equals("") && !et_connect_number2.getText().toString().equals("")) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
                count++;
                if (count < 2) {
                    flipper.showNext();
                    flipperIndex = flipper.getDisplayedChild();
                } else {
                    count--;
                }
            } else {
                Toast.makeText(ConnectProfileActivity.this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            }

        } else if (xAtDown < xAtUp) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
            count--;
            if (count > -1) {
                flipper.showPrevious();
                flipperIndex = flipper.getDisplayedChild();
            } else {
                count++;
            }
        }
    }
}
