package com.example.capstonedesign_geo.data.Model;

public class ChatMessage {
    public static final String SENT_BY_GEO = "geo";
    public static final String SENT_BY_USER = "user";

    public String sentBy;  // 누가 보낸 메시지인지
    public String content; // 메시지 내용

    public ChatMessage(String sentBy, String content){
        this.sentBy = sentBy;
        this.content = content;
    }

    public String getMessage() {
        return content;
    }
}
