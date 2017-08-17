package com.bibinet.biunion.project.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.CustomServiceAdapter;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bumptech.glide.Glide;
import com.easemob.helpdeskdemo.Constant;
import com.easemob.helpdeskdemo.MessageHelper;
import com.easemob.helpdeskdemo.Preferences;
import com.easemob.helpdeskdemo.ui.*;
import com.easemob.helpdeskdemo.ui.ChatActivity;
import com.easemob.helpdeskdemo.ui.LoginActivity;
import com.easemob.helpdeskdemo.utils.GlideCircleTransform;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.chat.OfficialAccount;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.easeui.util.SmileUtils;
import com.hyphenate.helpdesk.model.AgentInfo;
import com.hyphenate.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.mapapi.BMapManager.getContext;

public class CustomServiceActivity extends Activity {
    private final List<Conversation> conversationList = new ArrayList<>();
    private final List<Conversation> conversationList1 = new ArrayList<>();
    @BindView(R.id.act_custom_service_not_message)
    View notMessageV;
    private WaitDialog waitDialog;
    private ConversationAdapter adapter;
    private List<List<Message>> dataList = new ArrayList<List<Message>>();
    private List<Message> dataList1 = new ArrayList<Message>();
    private List<Message> dataList2 = new ArrayList<Message>();
    private List<Message> dataList3 = new ArrayList<Message>();

//    @Override
//    public void onClick(View v) {
//        this.dismiss();
//        switch (v.getId()) {
//            case R.id.btn_pre_sales:
//                mContext.startActivity(new Intent(mContext, com.easemob.helpdeskdemo.ui.LoginActivity.class).putExtra(Constant.MESSAGE_TO_INTENT_EXTRA,
//                        Constant.MESSAGE_TO_PRE_SALES));
//                break;
//            case R.id.btn_after_sales:
//                mContext.startActivity(new Intent(mContext, com.easemob.helpdeskdemo.ui.LoginActivity.class).putExtra(Constant.MESSAGE_TO_INTENT_EXTRA,
//                        Constant.MESSAGE_TO_AFTER_SALES));
//                break;
//            default:
//                break;
//        }
//    }
    private List<Message> dataListTop = new ArrayList<Message>();
    Handler ha = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            conversationList.clear();
            String toChat = Constant.DEFAULT_CUSTOMER_ACCOUNT;
            Conversation conversation1 = ChatClient.getInstance().chatManager().getConversation(toChat);
            conversationList.add(conversation1);

            //对conversationList进行一次整体的分割 拿出需要的部分
            dataList1.clear();
            dataList2.clear();
            dataList3.clear();
            dataListTop.clear();
            dataList.clear();
            if(conversationList.size()<=0){
                notMessageV.setVisibility(View.VISIBLE);
                Log.e("-=-", "-=wankong");
                return;
            }
            Conversation conversation = conversationList.get(0);
            Log.e("-1=", "-"+conversation.getAllMsgCount());
            Log.e("-2=", "-"+conversation.getMessageOfPosition(0));
            int tag = -1;
            boolean isFirst = true;
            for(int i=0 ; i<conversation.getAllMsgCount() ; i++){
                Message m = conversation.getMessageOfPosition(i);
                AgentInfo agentInfo = com.hyphenate.helpdesk.model.MessageHelper.getAgentInfo(m);
                if(agentInfo!=null){
                    String name = agentInfo.getNickname();
                    switch (name){
                        case "客服专员01":
                            dataList1.add(m);
                            tag = 0;
                            if(isFirst){
                                dataList1.addAll(0, dataListTop);
                            }
                            break;
                        case "客服专员02":
                            dataList2.add(m);
                            tag = 1;
                            if(isFirst){
                                dataList2.addAll(0, dataListTop);
                            }
                            break;
                        case "客服专员03":
                            dataList3.add(m);
                            tag = 2;
                            if(isFirst){
                                dataList3.addAll(0, dataListTop);
                            }
                            break;
                    }
                    isFirst = false;
                }else{
                    switch (tag){
                        case 0:
                            dataList1.add(m);
                            break;
                        case 1:
                            dataList2.add(m);
                            break;
                        case 2:
                            dataList3.add(m);
                            break;
                        default:
                            dataListTop.add(m);
                            break;
                    }
                }
            }
            if(dataList1.size()>0){
                dataList.add(dataList1);
            }
            if(dataList2.size()>0){
                dataList.add(dataList2);
            }
            if(dataList3.size()>0){
                dataList.add(dataList3);
            }

            Log.e("1", dataList1.toString());
            Log.e("2", dataList2.toString());
            Log.e("3", dataList3.toString());

            if(dataList.size()<=0){
                if(dataListTop.size()>0){
                    dataList.add(dataListTop);
                }
            }

            if(dataList.size()<=0){
                notMessageV.setVisibility(View.VISIBLE);
            }else{
                notMessageV.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    };
    private Handler haE = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Toast.makeText(CustomServiceActivity.this, "客服异常", Toast.LENGTH_SHORT).show();
            notMessageV.setVisibility(View.VISIBLE);
            waitDialog.close();
        }
    };
    private int notSeeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_service);
        ButterKnife.bind(this);
        waitDialog = new WaitDialog(this);
        ListView mListView = (ListView) findViewById(R.id.listview);

        mListView.setAdapter(adapter = new ConversationAdapter(conversationList));
        loadConversationList();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversation = (Conversation) parent.getItemAtPosition(position);
                if(conversation==null){
                    startActivity(new Intent(CustomServiceActivity.this, com.easemob.helpdeskdemo.ui.LoginActivity.class).putExtra(Constant.MESSAGE_TO_INTENT_EXTRA,
                            Constant.MESSAGE_TO_PRE_SALES));
                    return;
                }
                // 进入主页面
                Intent intent = new IntentBuilder(CustomServiceActivity.this)
                        .setTargetClass(ChatActivity.class)
                        .setVisitorInfo(MessageHelper.createVisitorInfo())
                        .setServiceIMNumber(conversation.conversationId())
//                        .setTitleName(conversation.getOfficialAccount().getName())
                        .setShowUserNick(true)
                        .build();
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @OnClick(R.id.act_custom_service_exit)
    void goBack(){
        finish();
    }

    //右上角聊天
    @OnClick(R.id.act_custom_service_chat)
    void toChat(View view) {
//        startActivity(new Intent(this, ChatActivity.class));
        startActivity(new Intent(this, com.easemob.helpdeskdemo.ui.LoginActivity.class).putExtra(Constant.MESSAGE_TO_INTENT_EXTRA,
                        Constant.MESSAGE_TO_PRE_SALES));
    }

    private void loadConversationList(){
        Hashtable<String, Conversation> allConversations =
                ChatClient.getInstance().chatManager().getAllConversations();
        synchronized (conversationList1){
            conversationList1.clear();
            for (String conversationId : allConversations.keySet()){
                Conversation item = allConversations.get(conversationId);
                if (item.getOfficialAccount() != null){
                    conversationList1.add(item);
                }
            }
        }

        if(conversationList1.size()<=0){
            notSeeCount = 0;
        }else{
            Conversation conversation = conversationList1.get(0);
            if (conversation.getUnreadMsgCount() > 0){
                Log.e("--", "da+"+conversation.getUnreadMsgCount());
            }else{
                Log.e("--", "dxiao+"+conversation.getUnreadMsgCount());
            }
            notSeeCount = conversation.getUnreadMsgCount();
        }



        if (ChatClient.getInstance().isLoggedInBefore()) {
            ha.sendEmptyMessage(0);
        } else {
            //登录
            //用实体数据类里的用户民和密码直接登录
            String userName = Constants.loginresultInfo.getUser().getEmchatUserName();
            String userPassword = Constants.loginresultInfo.getUser().getEmchatPassword();
            Log.e("-=", userName+"-"+userPassword);
            login(userName,userPassword);
        }

    }

    public void refresh(){
        loadConversationList();
    }

    private void login(final String uname, final String upwd) {
        // login huanxin server
        waitDialog.open();
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                ha.sendEmptyMessage(0);
                waitDialog.close();
            }

            @Override
            public void onError(int code, String error) {
                haE.sendEmptyMessage(0);
                Log.e("-x", code+"-=-"+error);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private class ConversationAdapter extends BaseAdapter {
        private List<Conversation> objects;
        private ConversationAdapter(List<Conversation> objects) {
            this.objects = objects;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            if(conversationList1.size()<=0){
                return null;
            }
            return conversationList1.get(0);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(CustomServiceActivity.this).inflate(R.layout.em_row_conversation, null);
                viewHolder = new ViewHolder();
                viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.tvMessage = (TextView) convertView.findViewById(R.id.tv_message);
                viewHolder.tvUnreadCount = (TextView) convertView.findViewById(R.id.tv_unread);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Conversation conversation = (Conversation) getItem(position);
//            if (conversation == null){
////                return convertView;
//                viewHolder.tvUnreadCount.setVisibility(View.GONE);
//            }
            if (notSeeCount > 0){
                viewHolder.tvUnreadCount.setVisibility(View.VISIBLE);
//                viewHolder.tvUnreadCount.setText(String.valueOf(notSeeCount));
            }else{
                viewHolder.tvUnreadCount.setVisibility(View.GONE);
            }
//            Message lastMessage = conversation.getLastMessage();
            List<Message> da = dataList.get(position);
            Message lastMessage = da.get(da.size()-1);
            if (lastMessage != null){
                if (lastMessage.getType() == Message.Type.TXT){
                    EMTextMessageBody body = (EMTextMessageBody)lastMessage.getBody();
                    viewHolder.tvMessage.setText(SmileUtils.getSmiledText(CustomServiceActivity.this, body.getMessage()));
                }else if (lastMessage.getType() == Message.Type.IMAGE){
                    viewHolder.tvMessage.setText("[图片]");
                }else if (lastMessage.getType() == Message.Type.VOICE){
                    viewHolder.tvMessage.setText("[语音]");
                }else if (lastMessage.getType() == Message.Type.VIDEO){
                    viewHolder.tvMessage.setText("[视频]");
                }else if (lastMessage.getType() == Message.Type.LOCATION){
                    viewHolder.tvMessage.setText("[位置]");
                }else if (lastMessage.getType() == Message.Type.FILE){
                    viewHolder.tvMessage.setText("[文件]");
                }
                viewHolder.tvTime.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));

            }else{
                viewHolder.tvMessage.setText("");
            }
            //用来取客服信息 没有证明无客服回复 走默认图片
            OfficialAccount officialAccount = null;
            if(conversation!=null){
                officialAccount = conversation.getOfficialAccount();
            }
//            if (officialAccount == null){
//                return convertView;
//            }
            //设置客服名称 去当前名称 如果为空 证明是自己 取上一条信息 循环接着试
            //全部都没有 返回比比客服
            String name = "比比客服";
            for(int i=da.size()-1 ; i>=0 ; i--){
                AgentInfo agentInfo = com.hyphenate.helpdesk.model.MessageHelper.getAgentInfo(da.get(i));
                Log.e("name", "-"+"+-"+agentInfo);
                if(agentInfo!=null){
                    name = agentInfo.getNickname();
                    Log.e("name1", "-"+"+-"+name);
                    break;
                }
            }
            if(name.equals("")){
                name = "比比客服";
            }
            viewHolder.tvName.setText(name);

            if (officialAccount == null){
                viewHolder.ivAvatar.setImageResource(R.mipmap.img_custom_service_icon);
            }else{
                String imgUrl = officialAccount.getImg();
                if (imgUrl != null && imgUrl.startsWith("//")){
                    imgUrl = "http:" + imgUrl;
                }
                Glide.with(CustomServiceActivity.this).load(imgUrl).error(R.mipmap.img_custom_service_icon).transform(new GlideCircleTransform(CustomServiceActivity.this)).into(viewHolder.ivAvatar);
            }

            return convertView;
        }

        class ViewHolder{
            ImageView ivAvatar;
            TextView tvName;
            TextView tvTime;
            TextView tvMessage;
            TextView tvUnreadCount;
        }
    }
}
