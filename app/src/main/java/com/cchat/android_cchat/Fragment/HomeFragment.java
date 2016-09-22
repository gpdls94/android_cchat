package com.cchat.android_cchat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cchat.android_cchat.R;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private pl.droidsonroids.gif.GifImageView gif_character;
    private LinearLayout circle_menus;

    private ImageButton[] circles;

    private static boolean isNetwork;

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
        circles = new ImageButton[]
                {((ImageButton) view.findViewById(R.id.circle_emo1)), ((ImageButton) view.findViewById(R.id.circle_emo2)),
                        ((ImageButton) view.findViewById(R.id.circle_emo3)), ((ImageButton) view.findViewById(R.id.circle_emo4)),
                        ((ImageButton) view.findViewById(R.id.circle_heart)), ((ImageButton) view.findViewById(R.id.circle_store)),
                        ((ImageButton) view.findViewById(R.id.circle_share)), ((ImageButton) view.findViewById(R.id.circle_))};

        gif_character = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.home_gif_character);
        gif_character.setLongClickable(true);

        gif_character.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                circle_menus.setVisibility(View.VISIBLE);
                return false;
            }
        });

        for (int i = 0; i < circles.length; i++) {
            circles[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
