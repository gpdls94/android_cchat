package com.cchat.android_cchat.Fragment;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cchat.android_cchat.Activitiy.CalendarActivity;
import com.cchat.android_cchat.Activitiy.CalendarActivity.gsCalendarColorParam;
import com.cchat.android_cchat.R;

/**
 * Created by hyein on 2016. 9. 1..
 */
public class PageFragment extends Fragment {

    private View view;
    private static boolean isNetwork;

    TextView tvs[];
    Button btns[];

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
        init();
        return view;
    }

    private void init() {

        LinearLayout lv = (LinearLayout) view.findViewById( R.id.calendar_lLayout ) ;

        tvs = new TextView[3] ;
        tvs[0] = (TextView) view.findViewById( R.id.tv1 ) ;
        tvs[1] = (TextView) view.findViewById( R.id.tv2 ) ;
        tvs[2] = null ;

        btns = new Button[4] ;
        btns[0] = null ;
        btns[1] = null ;
        btns[2] = (Button) view.findViewById( R.id.Button03 ) ;
        btns[3] = (Button) view.findViewById( R.id.Button04 ) ;

        CalendarActivity cal = new CalendarActivity( getActivity(), lv ) ;
        gsCalendarColorParam cParam = new CalendarActivity.gsCalendarColorParam( ) ;

        cParam.m_cellColor = 0x00000000 ;
        cParam.m_textColor = getActivity().getResources().getColor(R.color.cchat_text_color);
        cParam.m_saturdayTextColor = getActivity().getResources().getColor(R.color.cchat_text_color);
        cParam.m_sundayTextColor = getActivity().getResources().getColor(R.color.cchat_text_color);
        cParam.m_lineColor = 0x99999999 ;
        cParam.m_topCellColor = 0xffffffff ;
        cParam.m_topTextColor = getActivity().getResources().getColor(R.color.cchat_hint_text_color);
        cParam.m_topSundayTextColor = getActivity().getResources().getColor(R.color.cchat_hint_text_color);
        cParam.m_topSaturdatTextColor = getActivity().getResources().getColor(R.color.cchat_hint_text_color);

        cal.setColorParam( cParam ) ;

//        Drawable img = getResources( ).getDrawable( R.drawable.bg ) ;
//        cal.setBackground( img ) ;

        cal.setCalendarSize(getScreenSize(getActivity()).x, ((2 * getScreenSize(getActivity()).y) / 3)) ;

        cal.setTopCellSize( 100 ) ;

        cal.setControl( btns ) ;

        cal.setViewTarget( tvs ) ;

        cal.initCalendar( ) ;
    }

    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return  size;
    }
}
