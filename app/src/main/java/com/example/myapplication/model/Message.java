package com.example.myapplication.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Message {
    private String mText;
    private String mSender;
    private Date mDate;
    private String mDateString;
    private String mReciver;

    public Message() {

    }

    public Message(String mText, String mSender) {
        this.mText = mText;
        this.mSender = mSender;
    }

    public Message(String mText, String mSender, Date mDate) {
        this.mText = mText;
        this.mSender = mSender;
        this.mDate = mDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getReciver() {
        return mReciver;
    }

    public void setReciver(String mReciver) {
        this.mReciver = mReciver;
    }

    public String getDateString() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public void setDateString(String mDateString) {
        this.mDateString = mDateString;
    }
}
