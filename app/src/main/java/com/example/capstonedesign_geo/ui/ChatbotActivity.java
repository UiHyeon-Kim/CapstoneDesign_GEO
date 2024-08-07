package com.example.capstonedesign_geo.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.Model.ChatMessage;
import com.example.capstonedesign_geo.ui.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private Button btnSend;
    private EditText etMessage;
    private List<ChatMessage> chatMsgList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerView = findViewById(R.id.recyclerView);
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);

        chatMsgList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatAdapter = new ChatAdapter();
        chatAdapter.setDataList(chatMsgList);
        recyclerView.setAdapter(chatAdapter);

        btnSend.setOnClickListener(view -> {
            String msg = etMessage.getText().toString();
            chatAdapter.addChatMsg(new ChatMessage(ChatMessage.SENT_BY_USER, msg));
            etMessage.setText(null);
            InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        });

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSend.setEnabled(s.length() > 0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       /* for (int i = 0; i < 10; i++){
            chatMsgList.add(new ChatMessage(i % 2,"메시지" + i));
        }*/
    }
}
