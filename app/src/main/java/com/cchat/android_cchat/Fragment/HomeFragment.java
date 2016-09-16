package com.cchat.android_cchat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cchat.android_cchat.R;
import com.szugyi.circlemenu.view.CircleLayout;

public class HomeFragment extends Fragment {

    private View view;
    private pl.droidsonroids.gif.GifImageView gif_character;
    private CircleLayout circle_menus;

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
        circle_menus = (CircleLayout) view.findViewById(R.id.circle_layout);
        gif_character = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.home_gif_character);
        gif_character.setLongClickable(true);
        gif_character.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                circle_menus.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }
}
