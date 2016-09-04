package com.cchat.android_cchat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 9. 1..
 */
public class PageFragment extends Fragment {

    private View view;
    private static boolean isNetwork;

    public static PageFragment newInstance() {
        PageFragment fragment = new PageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }
}
