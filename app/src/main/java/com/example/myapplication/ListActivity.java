package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.service.MessageDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ListActivity extends AppCompatActivity {

    private Spinner edtUID;
    private Button btnChoose, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        edtUID = findViewById(R.id.edtUID);
        btnChoose = findViewById(R.id.btnChoose);
        btnLogout = findViewById(R.id.btnLogout);


        String[] arraySpinner = new String[]{
                "Ea49MuBYJRdZD0aMhfx3py4CMx72",
                "ULKmtpUHbBh5YKNZfjxkvcHDo453"
        };


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtUID.setAdapter(adapter);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ListActivity.this, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", edtUID.getSelectedItem().toString());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ListActivity.this, LoginActivity.class));
            }
        });
    }
}
