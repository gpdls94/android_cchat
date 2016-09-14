package com.cchat.android_cchat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cchat.android_cchat.Class.ChatMessage;
import com.cchat.android_cchat.R;

import java.util.List;

/**
 * Created by hyein on 2016. 9. 7..
 */
public class ChatAdapter extends BaseAdapter {

    private final List<ChatMessage> chatMessages;
    private Activity context;

    public ChatAdapter(Activity context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    @Override
    public ChatMessage getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ChatMessage chatMessage = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.item_chatmsg, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean myMsg = chatMessage.getIsme() ;//Just a dummy check
        //to simulate whether it me or other sender
        setAlignment(holder, myMsg);


        switch (chatMessage.getCategory()) {
            case 0:
                holder.imgMessage.setVisibility(View.GONE);
                holder.txtMessage.setVisibility(View.VISIBLE);
                holder.txtMessage.setText(chatMessage.getMessage());
                holder.txtInfo.setText(chatMessage.getDate());
                break;
            case 1:
                holder.txtMessage.setVisibility(View.GONE);
                holder.imgMessage.setVisibility(View.VISIBLE);
                holder.imgMessage.setImageDrawable(chatMessage.getImage().getDrawable());
                holder.contentWithBG.setBackground(null);
                break;
            case 2:
                break;
            default:
        }

        return convertView;
    }

    public void add(ChatMessage message) {
        chatMessages.add(message);
    }

    public void add(List<ChatMessage> messages) {
        chatMessages.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (!isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);

            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);

//            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
//            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (RelativeLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.removeRule(RelativeLayout.RIGHT_OF);
            layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.contentWithBackground);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.setMargins(0, 0, pxToDp(context, 20), 0);
            holder.txtInfo.setLayoutParams(layoutParams);
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.contentWithBG.setLayoutParams(layoutParams);

//            RelativeLayout.LayoutParams lp =
//                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            holder.content.setLayoutParams(lp);

//            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
//            layoutParams.gravity = Gravity.LEFT;
//            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (RelativeLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.removeRule(RelativeLayout.LEFT_OF);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.contentWithBackground);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.setMargins(pxToDp(context, 20), 0, 0, 0);
            holder.txtInfo.setLayoutParams(layoutParams);
        }
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (RelativeLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.txtInfo = (TextView) v.findViewById(R.id.txtInfo);
        holder.imgMessage = (ImageView) v.findViewById(R.id.imgMessage);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public RelativeLayout content;
        public LinearLayout contentWithBG;
        public ImageView imgMessage;
    }
}