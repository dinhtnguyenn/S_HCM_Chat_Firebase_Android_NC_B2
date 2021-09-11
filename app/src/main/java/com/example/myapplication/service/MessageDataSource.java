package com.example.myapplication.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Message;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MessageDataSource {
    public static String ID, IDNguoiNhan;
    private static final DatabaseReference sRef = FirebaseDatabase.getInstance().getReference();
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //Gửi tin nhắn lên server
    public static void saveMessage(Message message) {
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put("text", message.getText());
        msg.put("sender", ID);
        msg.put("reciver", IDNguoiNhan);
        sRef.child("message").child(key).setValue(msg);
    }

    //Calling interface to handle event after getting data from server
    public static MessageListener addMessageListener(MessageCallback messageCallback) {
        MessageListener messageListener = new MessageListener(messageCallback);
        sRef.child("message").addChildEventListener(messageListener);
        return messageListener;
    }

    public static void stopListener(MessageListener mListener) {
        sRef.removeEventListener(mListener);
    }

    //Create a MessageListener which gets data from server in realtime
    //We set data new item(child) into MessageUser  on onChildAdded method
    public static class MessageListener implements ChildEventListener {
        private MessageCallback callback;

        public MessageListener(MessageCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            if((dataSnapshot.child("sender").getValue().equals(ID) && dataSnapshot.child("reciver").getValue().equals(IDNguoiNhan)) ||
                    (dataSnapshot.child("sender").getValue().equals(IDNguoiNhan) && dataSnapshot.child("reciver").getValue().equals(ID))){
                Message msg = dataSnapshot.getValue(Message.class);
                Message message = new Message();
                message.setSender(msg.getSender());
                message.setReciver(msg.getReciver());
                message.setText(msg.getText());
                try {
                    message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (callback != null) {
                    callback.onMessageAdded(message);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("child cancel ", "");
        }

    }

    public interface MessageCallback {
        void onMessageAdded(Message message);
    }
}
