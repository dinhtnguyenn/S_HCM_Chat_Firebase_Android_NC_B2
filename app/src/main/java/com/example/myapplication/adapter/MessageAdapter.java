package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessageUserList;
    private Context mContext;
    public static FirebaseAuth sFirebaseAuth = FirebaseAuth.getInstance();

    public MessageAdapter(List<Message> mMessageUserList, Context mContext) {
        this.mMessageUserList = mMessageUserList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message message = mMessageUserList.get(i);

        if (sFirebaseAuth.getCurrentUser() != null && message.getSender() != null) {
            if (message.getSender().equals(sFirebaseAuth.getCurrentUser().getUid())) {
                viewHolder.imgSender.setVisibility(View.VISIBLE);
                viewHolder.imgReciver.setVisibility(View.GONE);
                viewHolder.txtMessUser.setText(mMessageUserList.get(i).getText());
            } else {
                viewHolder.imgSender.setVisibility(View.GONE);
                viewHolder.imgReciver.setVisibility(View.VISIBLE);
                viewHolder.txtMessUser.setText(mMessageUserList.get(i).getText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMessageUserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgReciver, imgSender;
        private TextView txtMessUser;

        ViewHolder(View itemView) {
            super(itemView);
            imgReciver = itemView.findViewById(R.id.imgReciver);
            imgSender = itemView.findViewById(R.id.imgSender);
            txtMessUser = itemView.findViewById(R.id.txtMessUser);
        }
    }
}
