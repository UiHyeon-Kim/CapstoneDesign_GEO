package com.example.capstonedesign_geo.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.model.ChatMessage;
import com.example.capstonedesign_geo.ui.adapter.ChatAdapter;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etChat;
    private Button btnSend;
    private ChatAdapter adapter;
    private List<ChatMessage> chatMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        recyclerView = findViewById(R.id.recyclerView);
        etChat = findViewById(R.id.etChat);
        btnSend = findViewById(R.id.btnSend);

        chatMessage = new ArrayList<>();
        adapter = new ChatAdapter(chatMessage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String message = etChat.getText().toString().trim();
            addMessage(message,"me");
            etChat.setText("");
        });
    }

    void addMessage(String message, String sender) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatMessage.add(new ChatMessage(message, sender));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }
}
