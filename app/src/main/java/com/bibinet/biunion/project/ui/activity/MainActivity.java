package com.bibinet.biunion.project.ui.activity;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.VersionUpdateManager;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.builder.JPushReciver;
import com.bibinet.biunion.project.ui.fragment.Fragment_Ask;
//import com.bibinet.biunion.project.ui.fragment.Fragment_Home;
import com.bibinet.biunion.project.ui.fragment.Fragment_Homex;
import com.bibinet.biunion.project.ui.fragment.Fragment_My;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements ChatManager.MessageListener {
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    @BindView(R.id.bottomhome)
    RelativeLayout bottomhome;
    //    @BindView(R.id.bottomFoucs)
//    RelativeLayout bottomFoucs;
    @BindView(R.id.bottomask)
    RelativeLayout bottomask;
    @BindView(R.id.bottomy)
    RelativeLayout bottomy;
    @BindView(R.id.texthome)
    TextView texthome;
    @BindView(R.id.imageask_new)
    View askNewV;
    private Fragment_Homex framentHome;
    private Fragment[] fragments = new Fragment[3];
    private Fragment_Ask fragment_Ask;
    private Fragment_My fragment_My;
    private RelativeLayout[] mTabs;
    private int index;
    private int currentTabIndex = 0;
    private VersionUpdateManager versionUpdateManager;
    private JPushReciver mMessageReceiver;
    private boolean isCanExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateBarTransparent();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        registerMessageReceiver();
        versionUpdateManager = new VersionUpdateManager(this);
        versionUpdateManager.versionUpdateCheck();
//        ImageUtils.generateImage("", Environment.getExternalStorageDirectory().getAbsolutePath()+"/img.png");
    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 定位所需权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
            } else {
            }
            //写入存储卡权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
            } else {

            }

        }

        SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(this);
        String loginString = sharedPresUtils.getString("loginResultData", null);
        Gson gson = new Gson();
        Constants.loginresultInfo = gson.fromJson(loginString, LoginResultBean.class);
        framentHome = new Fragment_Homex();
        fragment_Ask = new Fragment_Ask();
        fragment_My = new Fragment_My();
        fragments = new Fragment[]{framentHome, fragment_Ask, fragment_My};
        mTabs = new RelativeLayout[]{bottomhome, bottomask, bottomy};
        mTabs[0].setSelected(true);
        getSupportFragmentManager().beginTransaction().add(R.id.fragementcontainer, framentHome).show(framentHome).
                add(R.id.fragementcontainer, fragment_Ask).hide(fragment_Ask).add(R.id.fragementcontainer, fragment_My).hide(fragment_My)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void registerMessageReceiver() {
        setStyleBasic();
        JPushReciver jPushReciver = new JPushReciver(texthome);
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(jPushReciver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNew();
        ChatClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ChatClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatClient.getInstance().chatManager().removeMessageListener(this);
    }

    public void initStateBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | getWindow().getAttributes().flags);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_main_title));
            }
        }
    }

    public void setStateBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }

    public void clearStateBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_main_title));
        }
    }

    @OnClick({R.id.bottomhome, R.id.bottomask, R.id.bottomy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottomhome:
                index = 0;
                clearStateBarTransparent();
                break;
            case R.id.bottomask:
                index = 1;
                clearStateBarTransparent();
                break;
            case R.id.bottomy:
                index = 2;
                setStateBarTransparent();
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragementcontainer, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setStyleBasic() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.search;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if (isCanExit) {
                return super.onKeyDown(keyCode, event);
            }
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            isCanExit = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isCanExit = false;
                }
            }, 2000);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("==-=", "dfdfasdfasdfasdf");
        //详情页回调关注结果
        if (requestCode == H5Activity.requestCode && resultCode == H5Activity.resultCode) {
            try {
                int pos = data.getIntExtra("pos", -1);
                boolean isCollect = data.getBooleanExtra("isCollect", false);
                framentHome.updateCollectResult(pos, isCollect);
            } catch (Exception e) {

            }
        }

        //头像拍照结果
        fragment_My.onActivityResult(requestCode, resultCode, data);
    }

    private void checkNew() {
        List<Conversation> conversationList1 = new ArrayList<>();
        Hashtable<String, Conversation> allConversations =
                ChatClient.getInstance().chatManager().getAllConversations();
        synchronized (conversationList1) {
            conversationList1.clear();
            for (String conversationId : allConversations.keySet()) {
                Conversation item = allConversations.get(conversationId);
                if (item.getOfficialAccount() != null) {
                    conversationList1.add(item);
                }
            }
        }
        if (conversationList1.size() <= 0) {
            askNewV.setVisibility(View.GONE);
            return;
        }
        Conversation conversation = conversationList1.get(0);
        if (conversation.getUnreadMsgCount() > 0) {
            Log.e("--", "da+" + conversation.getUnreadMsgCount());
            askNewV.post(new Runnable() {
                @Override
                public void run() {
                    askNewV.setVisibility(View.VISIBLE);
                }
            });
            fragment_Ask.updateNewCircle(true);
        } else {
            Log.e("--", "dxiao+" + conversation.getUnreadMsgCount());
            askNewV.post(new Runnable() {
                @Override
                public void run() {
                    askNewV.setVisibility(View.GONE);
                }
            });
            fragment_Ask.updateNewCircle(false);
        }
    }

    @Override
    public void onMessage(List<Message> list) {
        Log.e("-=", "--wuaooioxoieoirij");
        checkNew();
    }

    @Override
    public void onCmdMessage(List<Message> list) {

    }

    @Override
    public void onMessageStatusUpdate() {

    }

    @Override
    public void onMessageSent() {

    }
}
