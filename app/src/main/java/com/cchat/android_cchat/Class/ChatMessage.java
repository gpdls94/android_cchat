package com.cchat.android_cchat.Class;

import android.text.Spanned;
import android.widget.ImageView;

/**
 * Created by hyein on 2016. 9. 7..
 */
public class ChatMessage {
    private long id;
    private boolean isNotMe;
    private Spanned message;
    private Long userId;
    private String dateTime, date;

    /** 0 text 1 image 2 voice ... **/
    private int category = 0;
    private ImageView image;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean getIsme() {
        return isNotMe;
    }
    public void setNotMe(boolean isMe) {
        this.isNotMe = isMe;
    }
    public Spanned getMessage() {
        return message;
    }
    public void setMessage(Spanned message) {
        this.message = message;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return dateTime;
    }

    public void setTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
