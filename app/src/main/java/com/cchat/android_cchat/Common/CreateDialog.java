package com.cchat.android_cchat.Common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cchat.android_cchat.R;

public class CreateDialog {
    String service;
    Activity act;
    LayoutInflater li;

    Dialog mDialog;
    View view;

    private final int DIALOG_PROFILE_PIC = 1;
    private final String DIALOG_UPDATE = "version_update";

    public CreateDialog(Activity act) {
        this.act = act;
        service = Context.LAYOUT_INFLATER_SERVICE;
        li = (LayoutInflater) act.getSystemService(service);
        view = null;
    }

    public void alert(String text) {
        mDialog = new Dialog(act);
        view = li.inflate(R.layout.dialog_message, (ViewGroup) act.findViewById(R.id.dialog_message));
        Button button = (Button) view.findViewById(R.id.dialog_ok);
        TextView tv = (TextView) view.findViewById(R.id.dialog_tv);

        if(text.equals(DIALOG_UPDATE)) {
            ((TextView) view.findViewById(R.id.dialog_title)).setText("업데이트 알림");
            tv.setText("업데이트 이후 이용 가능합니다.");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent marketLaunch = new Intent(Intent.ACTION_VIEW);

                    /** URL 수정 **/

                    marketLaunch.setData(Uri.parse("market://details?id=com.cchat"));
                    act.startActivity(marketLaunch);
                    System.exit(1);
                }
            });
        } else {
            tv.setText(text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();

                    /** 클래스에 따라 동작 구분 **/

//                    if (act.getClass().getName() == PartnerActivity.class.getName()) {
//                        act.finish();
//                    } else if (act.getClass().getName() == MoahActivity.class.getName()) {
//                        Intent in = new Intent(act, MoahActivity.class);
//                        in.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        act.startActivity(in);
//                    }
                }
            });
        }

        mDialog.setTitle(null);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.show();
    }

    public void quit() {
        mDialog = new Dialog(act);
        view = li.inflate(R.layout.dialog_quit, (ViewGroup) act.findViewById(R.id.dialog_quit_ly));
        Button ok_btn = (Button) view.findViewById(R.id.dialog_quit_ok);
        Button cancle_btn = (Button) view.findViewById(R.id.dialog_quit_cancle);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.setTitle(null);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.show();
    }

    public void network() {
        mDialog = new Dialog(act);
        view = li.inflate(R.layout.dialog_network, (ViewGroup) act.findViewById(R.id.dialog_network_ly));
        Button ok_btn = (Button) view.findViewById(R.id.dialog_network_ok);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        mDialog.setTitle(null);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);
        mDialog.show();
    }


    /**
     *
     * 프로필 수정 다이얼로그
     *
     * **/


//    public Dialog setProfile(int id) {
//        final SharedPreferences pref = act.getSharedPreferences("member", act.MODE_PRIVATE);
//
//        final Dialog mDialog = new Dialog(act);
//
//        if (id == DIALOG_PROFILE_PIC) {
//            view = li.inflate(R.layout.dialog_custom,
//                    (ViewGroup) act.findViewById(R.id.다이얼로그_레이아웃));
//
//            // custom view에서 사용한 위젯들의 동작을 정의합니다.
//            Button yes = (Button) view.findViewById(R.id.다이얼로그_사진변경);
//            yes.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent clsIntent = new Intent(Intent.ACTION_PICK);
//                    clsIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//                    clsIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    act.startActivityForResult(clsIntent, 100);
//                    mDialog.dismiss();
//                }
//            });
//
//            Button no = (Button) view.findViewById(R.id.다이얼로그_사진삭제);
//            no.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    CircleImageView 이미지뷰 = (CircleImageView) act.findViewById(R.id.수정_회원사진);
//                    이미지뷰.setImageResource(R.drawable.myinfo_icon);
//                    LoginActivity.check_login.setImage(null);
//                    pref.edit().putString("image", "").commit();
//                    mDialog.dismiss();
//                }
//            });
//        }
//        mDialog.setTitle(null);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(view);
//
//        return mDialog;
//    }
}
