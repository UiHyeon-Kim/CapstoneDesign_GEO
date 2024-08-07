package com.example.capstonedesign_geo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.Model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> dataList;

    // 데이터 리스트 세팅(누가 보낸지, 메시지)
    public void setDataList(List<ChatMessage> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    // 채팅 메시지 추가 되었을 때 어댑터에 반영
    public void addChatMsg(ChatMessage chatMessage) {
        dataList.add(chatMessage);
        notifyItemInserted(dataList.size());
    }

    // 각 아이템의 뷰타입 호출 시 ChatMessage 클래스에 따라 내 메시지 0, 챗봇 메시지 1을 반환
    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).sentBy.equals(ChatMessage.SENT_BY_USER)) return 0;
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 뷰타입이 0이면 MyChatViewHolder 반환
        if (viewType == 0){
            return new UserChatViewHolder(inflater.inflate(R.layout.item_user_chat, parent, false));
        } else { // 아니면 GeoChatViewHolder 반환
            return new GeoChatViewHolder(inflater.inflate(R.layout.item_geo_chat, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = dataList.get(position);
        if(chatMessage.sentBy.equals(chatMessage.SENT_BY_USER)) {
            ((UserChatViewHolder)holder).setMsg(chatMessage);
        } else {
            ((GeoChatViewHolder)holder).setMsg(chatMessage);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    // 내가 보낸 메시지를 띄우기 위한 뷰홀더
    class UserChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;
        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
        public void setMsg(ChatMessage chatMsg) {
            tvMsg.setText(chatMsg.content);
        }
    }

    class GeoChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;
        public GeoChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
        public void setMsg(ChatMessage chatMsg) {
            tvMsg.setText(chatMsg.content);
        }
    }
}
