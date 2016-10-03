package com.cchat.android_cchat.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cchat.android_cchat.R;

import at.markushi.ui.CircleButton;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private pl.droidsonroids.gif.GifImageView gif_character;
    private LinearLayout circle_menus;

    private CircleButton[] circles;

    private static boolean isNetwork;
    private GestureDetectorCompat gestureDetector;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        return view;
    }

    private void init() {
        circle_menus = (LinearLayout) view.findViewById(R.id.circle_layout);
        circle_menus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        circles = new CircleButton[]
                {((CircleButton) view.findViewById(R.id.circle_emo1)), ((CircleButton) view.findViewById(R.id.circle_emo2)),
                        ((CircleButton) view.findViewById(R.id.circle_emo3)), ((CircleButton) view.findViewById(R.id.circle_emo4)),
                        ((CircleButton) view.findViewById(R.id.circle_heart)), ((CircleButton) view.findViewById(R.id.circle_store)),
                        ((CircleButton) view.findViewById(R.id.circle_share)), ((CircleButton) view.findViewById(R.id.circle_))};

        gif_character = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.home_gif_character);
        gif_character.setLongClickable(true);

        gif_character.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                circle_menus.setVisibility(View.VISIBLE);
                return false;
            }
        });

        gif_character.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    circle_menus.setVisibility(View.GONE);
                }

                checkOnClick(motionEvent.getAction(), motionEvent.getRawX(), motionEvent.getRawY());

                return false;
            }
        });
    }

    private void checkOnClick(float actoin, float x, float y) {

        for (int i = 0; i < circles.length; i++) {
            Rect r = new Rect();
            circles[i].getGlobalVisibleRect(r);

            if(x > r.left && x < r.right && y > r.top && y < r.bottom) {
                if (actoin == MotionEvent.ACTION_UP) {
                    onClick(circles[i]);
                }
                else if (actoin == MotionEvent.ACTION_MOVE) {
                    circles[i].setColor(getResources().getColor(R.color.cchat_main_color));
                    circles[i].setPressed(false);
                }

                break;
            } else {
                circles[i].setColor(getResources().getColor(R.color.cchat_hint_text_color));
            }
        }
    }

    @Override
    public void onClick(View view) {
//        ((CircleButton) view).setColor(getResources().getColor(R.color.cchat_main_color));
        System.out.println("CLICKCKC!");
    }
}
