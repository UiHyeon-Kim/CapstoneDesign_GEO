package com.example.capstonedesign_geo.data.model;

public class ChatMessage {
    private static String SENT_BY_ME = "me";
    private static String SENT_BY_BOT = "bot";

    private String sender;
    private String message;

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
