package com.cchat.android_cchat.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;

import com.cchat.android_cchat.R;

import java.util.ArrayList;

/**
 * Created by hyein on 2016. 9. 16..
 */
public class PlusPagerAdapter extends PagerAdapter {

    private String[] btns;
    private Drawable[] icons;

    private static final int NO_OF_EMOTICONS_PER_PAGE = 20;
    FragmentActivity mActivity;
    PlusGridAdapter.KeyClickListener mListener;

    public PlusPagerAdapter(FragmentActivity activity, String[] btns, PlusGridAdapter.KeyClickListener listener) {
        this.mActivity = activity;
        this.mListener = listener;
        this.btns = btns;

        this.icons = new Drawable[]
                {activity.getDrawable(android.R.drawable.ic_menu_camera), activity.getDrawable(android.R.drawable.ic_menu_gallery),
                        activity.getDrawable(android.R.drawable.ic_menu_view),activity.getDrawable(android.R.drawable.ic_menu_share),
                        activity.getDrawable(android.R.drawable.ic_menu_mylocation),activity.getDrawable(android.R.drawable.ic_menu_call)};
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((double) btns.length
                / (double) NO_OF_EMOTICONS_PER_PAGE);
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        View layout = mActivity.getLayoutInflater().inflate(
                R.layout.grid_plus, null);

        int initialPosition = position * NO_OF_EMOTICONS_PER_PAGE;
        ArrayList<String> plusInAPage = new ArrayList<String>();

        for (int i = initialPosition; i < initialPosition
                + NO_OF_EMOTICONS_PER_PAGE
                && i < btns.length; i++) {
            plusInAPage.add(btns[i]);
        }

        GridView grid = (GridView) layout.findViewById(R.id.plus_grid);
        PlusGridAdapter adapter = new PlusGridAdapter(
                mActivity.getApplicationContext(), plusInAPage, icons, position,
                mListener);
        grid.setAdapter(adapter);

        ((ViewPager) collection).addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}