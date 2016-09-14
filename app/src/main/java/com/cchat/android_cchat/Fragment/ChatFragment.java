package com.cchat.android_cchat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cchat.android_cchat.Adapter.ChatAdapter;
import com.cchat.android_cchat.Class.ChatMessage;
import com.cchat.android_cchat.R;
import com.cchat.android_cchat.View.ScalableLayout;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatFragment extends Fragment {

    private PopupWindow popupWindow;
    private View popUpView;
    private int keyboardHeight;
    private boolean isKeyBoardVisible;

    private final int REQUEST_CODE = 1;

    private View view;
    private static boolean isNetwork;

    private ScalableLayout ly_bottom;
    private LinearLayout ly_root;
    private LinearLayout emoticonsCover;
    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton cameraBtn, emoBtn;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        ly_root = (LinearLayout) view.findViewById(R.id.chat_ly_root);

        popUpView = getActivity().getLayoutInflater().inflate(R.layout.t, null);

        init();
        enablePopUpView();
        checkKeyboardHeight(ly_root);

        return view;
    }

    private void init() {
        ly_bottom = (ScalableLayout) view.findViewById(R.id.chat_rl_bottom);
        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);
        messageET = (EditText) view.findViewById(R.id.chat_et_chat);
        sendBtn = (Button) view.findViewById(R.id.chat_ib_send);
        cameraBtn = (ImageButton) view.findViewById(R.id.chat_ib_camera);
        emoBtn = (ImageButton) view.findViewById(R.id.chat_ib_emo);
        emoticonsCover = (LinearLayout) view.findViewById(R.id.footer_for_emoticons);

        loadDummyHistory();


        // Defining default height of keyboard which is equal to 230 dip
        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);

        messagesContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(messageET.getWindowToken(), 0);

                if (popupWindow.isShowing())
                    popupWindow.dismiss();
                return false;
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122); //dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(getTime());
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });

        messageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence.toString())) {

                    Drawable speak_icon = getActivity().getDrawable(android.R.drawable.ic_btn_speak_now);
                    speak_icon.setBounds(0, 0, speak_icon.getIntrinsicWidth(), speak_icon.getIntrinsicHeight());

                    sendBtn.setCompoundDrawables(speak_icon, null, null, null);
                    sendBtn.setText("");
                }
                else {
                    sendBtn.setCompoundDrawables(null, null, null, null);
                    sendBtn.setText(getActivity().getResources().getString(R.string.chat_send));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
                intent.setMaxSelectCount(20);
                intent.setShowCamera(true);
                intent.setShowGif(true);
                intent.setSelectCheckBox(false);
                intent.setMaxGrideItemCount(3);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        emoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!popupWindow.isShowing()) {

                    popupWindow.setHeight((int) (keyboardHeight));

                    if (isKeyBoardVisible) {
                        emoticonsCover.setVisibility(LinearLayout.GONE);
                    } else {
                        emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    }
                    popupWindow.showAtLocation(ly_root, Gravity.BOTTOM, 0, 0);

                } else {
                    popupWindow.dismiss();
                }
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() {
        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("안농");
        msg.setCategory(0);
        Calendar now = Calendar.getInstance();
        msg.setDate(getTime());
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("모하니...");
        msg.setCategory(0);
        msg.setDate(getTime());
        chatHistory.add(msg1);

        adapter = new ChatAdapter(getActivity(), new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                for(int i=0; i< photos.size(); i++){
                    try {
                        ImageView image = new ImageView(getActivity());
                        Bitmap orgImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                                Uri.parse("file://"+photos.get(i)));
                        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 300, 400, true);
                        image.setImageBitmap(resize);

                        //test
                        ChatMessage msg = new ChatMessage();
                        msg.setId(3);
                        msg.setMe(true);
                        msg.setCategory(1); //image
                        msg.setImage(image); //image
                        msg.setDate(getTime());
                        chatHistory.add(msg);

                        displayMessage(msg);

                    } catch (IOException e) {
                        e.printStackTrace();
//                        Toast.makeText(getActivity(),"이미지 첨부에 실패하였습니다."+e.toString(),Toast.LENGTH_LONG).show();
                    }
                }

                // start image viewr
//                Intent startActivity = new Intent(getActivity() , PhotoPagerActivity.class);
//                startActivity.putStringArrayListExtra("photos" , (ArrayList<String>) photos);
//                startActivity(startActivity);
            }
        }
    }

    private String getTime() {
        Calendar now = Calendar.getInstance();
        int isAMorPM = now.get(Calendar.AM_PM);
        String ampm = "";

        switch (isAMorPM) {
            case Calendar.AM:
                ampm = getActivity().getResources().getString(R.string.chat_time_AM);
            case Calendar.PM:
                ampm = getActivity().getResources().getString(R.string.chat_time_PM);
        }

        return ampm+now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE);
    }


    /**
     * Defining all components of emoticons keyboard
     */
    private void enablePopUpView() {

        ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

//        for (short i = 1; i <= NO_OF_EMOTICONS; i++) {
//            paths.add(i + ".png");
//        }
//
//        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(MainActivity.this, paths, this);
//        pager.setAdapter(adapter);

        // Creating a pop window for emoticons keyboard
        popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                emoticonsCover.setVisibility(LinearLayout.GONE);
            }
        });
    }

    /**
     * Checking keyboard height and keyboard visibility
     */
    int previousHeightDiffrence = 0;
    private void checkKeyboardHeight(final View parentLayout) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - heightDifference > 50) {
                            popupWindow.dismiss();
                        }

                        previousHeightDiffrence = heightDifference;
                        if (heightDifference > 100) {

                            isKeyBoardVisible = true;
                            changeKeyboardHeight(heightDifference);

                        } else {

                            isKeyBoardVisible = false;

                        }

                    }
                });

    }

    /**
     * change height of emoticons keyboard according to height of actual
     * keyboard
     *
     * @param height
     *            minimum height by which we can make sure actual keyboard is
     *            open or not
     */
    private void changeKeyboardHeight(int height) {

        if (height > 100) {
            keyboardHeight = height;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, keyboardHeight);
            emoticonsCover.setLayoutParams(params);
        }

    }
}