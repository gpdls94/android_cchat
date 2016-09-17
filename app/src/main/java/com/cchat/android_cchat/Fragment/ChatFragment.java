package com.cchat.android_cchat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cchat.android_cchat.Adapter.ChatAdapter;
import com.cchat.android_cchat.Adapter.EmoticonsGridAdapter;
import com.cchat.android_cchat.Adapter.EmoticonsPagerAdapter;
import com.cchat.android_cchat.Adapter.PlusGridAdapter;
import com.cchat.android_cchat.Adapter.PlusPagerAdapter;
import com.cchat.android_cchat.Class.ChatMessage;
import com.cchat.android_cchat.R;
import com.cchat.android_cchat.View.SoftKeyboardDectectorView;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class ChatFragment extends Fragment implements EmoticonsGridAdapter.KeyClickListener, PlusGridAdapter.KeyClickListener {

    private final int NO_OF_EMOTICONS = 54;

    private Bitmap[] emoticons;
    private String[] btns;

    private View popUpView_emo;
    private View popUpView_plus;
    private boolean isKeyBoardVisible;
    private boolean isPlusVisible;
    private boolean isEmoVisible;

    private final int REQUEST_CODE = 1;

    private View view;
    private static boolean isNetwork;

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
        isKeyBoardVisible = false;
        isEmoVisible = false;
        isPlusVisible = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        popUpView_emo = getActivity().getLayoutInflater().inflate(R.layout.popup_emoticons, null);
        popUpView_plus = getActivity().getLayoutInflater().inflate(R.layout.popup_plus, null);

        init();
        readEmoticons();
        enablePopUpView();
        keyboardInit();

        return view;
    }

    private void init() {
        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);
        messageET = (EditText) view.findViewById(R.id.chat_et_chat);
        sendBtn = (Button) view.findViewById(R.id.chat_ib_send);
        cameraBtn = (ImageButton) view.findViewById(R.id.chat_ib_camera);
        emoBtn = (ImageButton) view.findViewById(R.id.chat_ib_emo);
        emoticonsCover = (LinearLayout) view.findViewById(R.id.footer_for_emoticons);


        this.btns = new String[]
                {getActivity().getResources().getString(R.string.chat_plus_camera), getActivity().getResources().getString(R.string.chat_plus_picture),
                        getActivity().getResources().getString(R.string.chat_plus_video), getActivity().getResources().getString(R.string.chat_plus_gift),
                        getActivity().getResources().getString(R.string.chat_plus_location), getActivity().getResources().getString(R.string.chat_plus_phone)};

        loadDummyHistory();

        messagesContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                hideSoftKeyboard(messageET);
                emoticonsCover.setVisibility(View.GONE);

                cameraBtn.setImageResource(android.R.drawable.ic_menu_camera);

                return false;
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spanned spanText = messageET.getText();
                if (TextUtils.isEmpty(spanText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122); //dummy
                chatMessage.setMessage(spanText);
                chatMessage.setDate(getTime());
                chatMessage.setNotMe(false);

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });

        messageET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emoticonsCover.setVisibility(LinearLayout.GONE);
                cameraBtn.setImageResource(android.R.drawable.ic_menu_camera);
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
                } else {
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

                if (!isPlusVisible) {

                    if (isEmoVisible) {
                        emoticonsCover.removeView(popUpView_emo);
                        isEmoVisible = false;
                    }

                    emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    emoticonsCover.addView(popUpView_plus);

                    cameraBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);

                    isPlusVisible = true;

                    if (isKeyBoardVisible) {
                        hideSoftKeyboard(messageET);
                    }

                } else {

                    emoticonsCover.setVisibility(LinearLayout.GONE);
                    emoticonsCover.removeView(popUpView_plus);

                    cameraBtn.setImageResource(android.R.drawable.ic_menu_camera);

                    isPlusVisible = false;
                }
            }
        });

        emoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isEmoVisible) {


                    if (isPlusVisible) {
                        emoticonsCover.removeView(popUpView_plus);
                        isPlusVisible = false;
                    }

                    emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    emoticonsCover.addView(popUpView_emo);

                    isEmoVisible = true;

                    if (isKeyBoardVisible) {
                        hideSoftKeyboard(messageET);
                    }

                } else {

                    emoticonsCover.setVisibility(LinearLayout.GONE);
                    emoticonsCover.removeView(popUpView_emo);

                    isEmoVisible = false;
                }

                cameraBtn.setImageResource(android.R.drawable.ic_menu_camera);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    protected void hideSoftKeyboard(final View view) {

        InputMethodManager ipm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        ipm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory() {
        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setNotMe(true);
        msg.setMessage(SpannableString.valueOf("안농"));
        msg.setCategory(0);
        Calendar now = Calendar.getInstance();
        msg.setDate(getTime());
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setNotMe(true);
        msg1.setMessage(SpannableString.valueOf("방가방가"));
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

                for (int i = 0; i < photos.size(); i++) {
                    try {
                        ImageView image = new ImageView(getActivity());
                        Bitmap orgImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                                Uri.parse("file://" + photos.get(i)));
                        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 300, 400, true);
                        image.setImageBitmap(resize);

                        //test
                        ChatMessage msg = new ChatMessage();
                        msg.setId(3);
                        msg.setNotMe(true);
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

        return ampm + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE);
    }


    /**
     * Defining all components of emoticons keyboard
     */
    private void enablePopUpView() {

        setEmoticonsPager();
        setPlusPager();
    }

    private void setPlusPager() {

        ViewPager pager = (ViewPager) popUpView_plus.findViewById(R.id.plus_pager);
        pager.setOffscreenPageLimit(3);

        PlusPagerAdapter adapter = new PlusPagerAdapter(getActivity(), btns, this);
        pager.setAdapter(adapter);

        LinearLayout ly_indicators = (LinearLayout) popUpView_plus.findViewById(R.id.plus_ly_indicators);
        final ArrayList<ImageView> indicators = new ArrayList<>();


        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView indicator = new ImageView(getActivity());
            indicator.setPadding(4, 4, 4, 4);
            indicator.setImageResource(android.R.drawable.presence_offline);

            ly_indicators.addView(indicator);
            indicators.add(indicator);
        }

        indicators.get(pager.getCurrentItem()).setImageResource(android.R.drawable.presence_online);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicators.size(); i++) {
                    if (i != position) {
                        indicators.get(i).setImageResource(android.R.drawable.presence_offline);
                    }

                    indicators.get(position).setImageResource(android.R.drawable.presence_online);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void setEmoticonsPager() {

        ViewPager pager = (ViewPager) popUpView_emo.findViewById(R.id.emoticons_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

        for (short i = 1; i <= NO_OF_EMOTICONS; i++) {
            paths.add(i + ".png");
        }

        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(getActivity(), paths, this);
        pager.setAdapter(adapter);

        LinearLayout ly_indicators = (LinearLayout) popUpView_emo.findViewById(R.id.emoticons_ly_indicators);
        final ArrayList<ImageView> indicators = new ArrayList<>();


        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView indicator = new ImageView(getActivity());
            indicator.setPadding(4, 4, 4, 4);
            indicator.setImageResource(android.R.drawable.presence_offline);

            ly_indicators.addView(indicator);
            indicators.add(indicator);
        }

        indicators.get(pager.getCurrentItem()).setImageResource(android.R.drawable.presence_online);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicators.size(); i++) {
                    if (i != position) {
                        indicators.get(i).setImageResource(android.R.drawable.presence_offline);
                    }

                    indicators.get(position).setImageResource(android.R.drawable.presence_online);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * Reading all emoticons in local cache
     */
    private void readEmoticons() {

        emoticons = new Bitmap[NO_OF_EMOTICONS];
        for (short i = 0; i < NO_OF_EMOTICONS; i++) {
            emoticons[i] = getImage((i + 1) + ".png");
        }

    }

    /**
     * For loading smileys from assets
     */
    private Bitmap getImage(String path) {
        AssetManager mngr = getActivity().getAssets();
        InputStream in = null;
        try {
            in = mngr.open("emoticons/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap temp = BitmapFactory.decodeStream(in, null, null);
        return temp;
    }

    @Override
    public void emoKeyClickedIndex(final String index) {

        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                StringTokenizer st = new StringTokenizer(index, ".");
                Drawable d = new BitmapDrawable(getResources(), emoticons[Integer.parseInt(st.nextToken()) - 1]);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };

        Spanned cs = Html.fromHtml("<img src ='" + index + "'/>", imageGetter, null);

        int cursorPosition = messageET.getSelectionStart();
        messageET.getText().insert(cursorPosition, cs);
    }

    @Override
    public void plusKeyClickedIndex(String index) {

        if (index.equals(btns[0])) {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
            intent.setMaxSelectCount(20);
            intent.setShowCamera(true);
            intent.setShowGif(true);
            intent.setSelectCheckBox(false);
            intent.setMaxGrideItemCount(3);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (index.equals(btns[1])) {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
            intent.setMaxSelectCount(20);
            intent.setShowCamera(true);
            intent.setShowGif(true);
            intent.setSelectCheckBox(false);
            intent.setMaxGrideItemCount(3);
            startActivityForResult(intent, REQUEST_CODE);
        } else if (index.equals(btns[2])) {

        } else if (index.equals(btns[3])) {

        } else if (index.equals(btns[4])) {

        } else if (index.equals(btns[5])) {

        } else {
            Toast.makeText(getActivity(), "준비 중..", Toast.LENGTH_SHORT).show();
        }
    }

    private void keyboardInit() {
        final SoftKeyboardDectectorView softKeyboardDecector = new SoftKeyboardDectectorView(getActivity());
        getActivity().addContentView(softKeyboardDecector, new FrameLayout.LayoutParams(-1, -1));

        softKeyboardDecector.setOnShownKeyboard(new SoftKeyboardDectectorView.OnShownKeyboardListener() {

            @Override
            public void onShowSoftKeyboard() {
                //키보드 등장할 때
                if (isPlusVisible) {
                    emoticonsCover.setVisibility(View.GONE);
                    emoticonsCover.removeView(popUpView_plus);
                    isPlusVisible = false;
                }

                if (isEmoVisible) {
                    emoticonsCover.setVisibility(View.GONE);
                    emoticonsCover.removeView(popUpView_emo);
                    isEmoVisible = false;
                }

                isKeyBoardVisible = true;
            }
        });

        softKeyboardDecector.setOnHiddenKeyboard(new SoftKeyboardDectectorView.OnHiddenKeyboardListener() {

            @Override
            public void onHiddenSoftKeyboard() {
                // 키보드 사라질 때
                isKeyBoardVisible = false;
            }
        });
    }
}