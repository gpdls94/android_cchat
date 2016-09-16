package com.cchat.android_cchat.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cchat.android_cchat.R;

import java.util.ArrayList;

/**
 * Created by hyein on 2016. 9. 16..
 */
public class PlusGridAdapter extends BaseAdapter {

    private ArrayList<String> paths;
    private Drawable[] icons;
    private int pageNumber;
    Context mContext;

    KeyClickListener mListener;

    public PlusGridAdapter(Context context, ArrayList<String> paths, Drawable[] icons, int pageNumber, KeyClickListener listener) {
        this.mContext = context;
        this.paths = paths;
        this.pageNumber = pageNumber;
        this.mListener = listener;
        this.icons = icons;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_plus, null);
        }

        final String path = paths.get(position);

        TextView btn = (TextView) v.findViewById(R.id.item);
        btn.setText(path);

        btn.setCompoundDrawables(null, getIcon(position), null, null);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.plusKeyClickedIndex(path);
            }
        });

        return v;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPageNumber () {
        return pageNumber;
    }

    public interface KeyClickListener {

        public void plusKeyClickedIndex(String index);
    }

    private Drawable getIcon(int position) {
        Drawable btn_icon = icons[position];
        btn_icon.setBounds(0, 0, btn_icon.getIntrinsicWidth(), btn_icon.getIntrinsicHeight());

        return btn_icon;
    }
}
