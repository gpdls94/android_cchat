package com.cchat.android_cchat.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 8. 24..
 */
public class BadgeTabLayout extends RelativeLayout {


    Context mContext;

    TextView mTv; // 숫자 입력

    ImageView mIv;

//    LinearLayout mLy;


    public BadgeTabLayout(Context context) {

        super(context);

        mContext = context;

        init();

    }


    public BadgeTabLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        mContext = context;

        init();

    }


    public BadgeTabLayout(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        mContext = context;

        init();

    }


    public void init() {


        LayoutInflater.from(mContext).inflate(R.layout.layout_badgetab, this, true);

        mIv = (ImageView) findViewById(R.id.tab_image);

        mTv = (TextView) findViewById(R.id.tab_badge);

//        mLy = (LinearLayout) findViewById(R.id.tab_ly);


    }


    public void setCount(String str) {


        setCount("" + str);


    }


    public void setCount(int i) {


        if (i != 0) {

            mTv.setVisibility(VISIBLE);

            mTv.setText("" + i);

        } else {

            mTv.setVisibility(GONE);

        }


    }


    public void setImage(int d) {

        mIv.setImageResource(d);

    }

    public Context getmContext() {
        return mContext;
    }

    public TextView getmTv() {
        return mTv;
    }

    public ImageView getmIv() {
        return mIv;
    }
}