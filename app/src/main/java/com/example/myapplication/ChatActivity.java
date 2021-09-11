package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.adapter.MessageAdapter;
import com.example.myapplication.model.Message;
import com.example.myapplication.service.MessageDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements MessageDataSource.MessageCallback {

    private Button btnSignOut, btnMessage;
    private TextView txtInfo;
    private RecyclerView rcvMessage;
    private EditText edtMessage;
    private List<Message> mMesseageList;
    private MessageAdapter mAdapter;
    private FirebaseUser user;
    private MessageDataSource.MessageListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnSignOut = findViewById(R.id.btnSignOut);
        txtInfo = findViewById(R.id.txtInfo);
        btnMessage = findViewById(R.id.btnMessage);
        rcvMessage = findViewById(R.id.rcvMessage);
        edtMessage = findViewById(R.id.edtMessage);

        //lấy thông tin người dùng đăng nhập hiện tại
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
            String phone = user.getPhoneNumber();

            txtInfo.setText("UID: " + uid + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nPhoto URL: " + photoUrl);
        }

        MessageDataSource.ID = user.getUid();
        MessageDataSource.IDNguoiNhan = getIntent().getExtras().getString("id");

        init();

        //đăng xuất
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
            }
        });

        //gủi tin nhắn
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message messageUser = new Message();
                messageUser.setDate(new Date());
                messageUser.setText(edtMessage.getText().toString());
                messageUser.setSender(user.getEmail());

                //lưu tin nhắn vào firebase
                MessageDataSource.saveMessage(messageUser);

                edtMessage.setText("");
            }
        });


    }

    private void init() {
        mMesseageList = new ArrayList<>();
        mAdapter = new MessageAdapter(mMesseageList, this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);
        rcvMessage.setAdapter(mAdapter);

        mListener = MessageDataSource.addMessageListener(this);

    }

    //hiển thị tin nhắn lên recyclerview sau khi lưu tin nhắn thành công trên firebase
    @Override
    public void onMessageAdded(Message message) {
        mMesseageList.add(message);
        mAdapter.notifyItemInserted(mMesseageList.indexOf(message));
        rcvMessage.smoothScrollToPosition(mMesseageList.size()-1);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
