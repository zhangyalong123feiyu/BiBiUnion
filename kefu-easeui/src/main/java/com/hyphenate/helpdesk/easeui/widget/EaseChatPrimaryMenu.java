package com.hyphenate.helpdesk.easeui.widget;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.helpdesk.R;

/**
 * 聊天输入栏主菜单栏
 *
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements View.OnClickListener {

    private EditText editText;
    private View buttonSetModeKeyboard;
    private RelativeLayout edittext_layout;
    private View buttonSetModeVoice;
    private RecorderMenu buttonPressToSpeak;
    private ImageView faceNormal;
    private ImageView faceKeyboard;
    private Button buttonMore;
    private Button buttonLess;
    private RelativeLayout faceLayout;
    private Context context;
    private boolean emojiSengBtnEnable = false;
    private Button buttonSend;

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.hd_widget_chat_primary_menu, this, true);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        buttonPressToSpeak = (RecorderMenu) findViewById(R.id.record_menu);
        faceLayout = (RelativeLayout) findViewById(R.id.rl_face);
        faceNormal = (ImageView) findViewById(R.id.iv_face);
        faceKeyboard = (ImageView) findViewById(R.id.iv_face_keyboard);
        buttonMore = (Button) findViewById(R.id.btn_more);
        buttonLess = (Button) findViewById(R.id.btn_less);
        buttonSend = (Button) findViewById(R.id.input_emoji_send_button);
        edittext_layout.setBackgroundResource(R.drawable.hd_input_bar_bg_normal);

        buttonSetModeVoice.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        buttonLess.setOnClickListener(this);
        faceNormal.setOnClickListener(this);
        faceKeyboard.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout.setBackgroundResource(R.drawable.hd_input_bar_bg_active);
                    if(listener != null){
                        listener.onEditTextClicked();
                    }
                } else {
                    edittext_layout.setBackgroundResource(R.drawable.hd_input_bar_bg_normal);
                }

            }
        });

        //编辑输入监听
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refleshEmojiSendBtn();
                //刷新发送按钮
                refleshInputEmojiSendBtn();
            }
        });
        buttonPressToSpeak.setAudioFinishRecorderListener(new RecorderMenu.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                if (listener != null) {
                    listener.onRecorderCompleted(seconds, filePath);
                }
            }
        });

        //设置编辑确定键
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    if (sendTextMsg()) return true;
//                    return true;
//                }
//                return false;
//            }
//        });
        //设置输入栏发送按钮
        setInputEmojiSendBtn(buttonSend);
    }

    private boolean sendTextMsg() {
        if (listener != null) {
            String s = editText.getText().toString();
            if (s.length() <= 0 || s.startsWith("\n")) {
                return true;
            }
            editText.setText("");
            refleshEmojiSendBtn();
            listener.onSendBtnClicked(s);
        }
        return false;
    }


    public boolean isRecording(){
        return buttonPressToSpeak.isRecording();
    }

    /**
     * 表情输入
     *
     * @param emojiContent
     */
    public void onEmojiconInputEvent(CharSequence emojiContent) {
        editText.append(emojiContent);
    }

    /**
     * 表情删除
     */
    public void onEmojiconDeleteEvent() {
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_set_mode_voice) {
            if (listener != null)
                listener.onToggleVoiceBtnClicked();
            setModeVoice();
        } else if (id == R.id.btn_set_mode_keyboard) {
            if (listener != null)
                listener.onToggleVoiceBtnClicked();
            editText.requestFocus();
            displayKeyboard(editText);
        } else if (id == R.id.btn_more) {
            hideVoiceMode();
            showMoreorLess(false);
            showNormalFaceImage();
            if (listener != null)
                listener.onToggleExtendClicked();
        } else if (id ==R.id.btn_less) {
            showMoreorLess(true);
            if (listener != null)
                listener.onToggleExtendClicked();
        } else if (id == R.id.et_sendmessage) {
            edittext_layout.setBackgroundResource(R.drawable.hd_input_bar_bg_active);
            faceNormal.setVisibility(View.VISIBLE);
            faceKeyboard.setVisibility(View.INVISIBLE);
            hideVoiceMode();
            showMoreorLess(true);
            if (listener != null)
                listener.onEditTextClicked();
        } else if (id == R.id.iv_face) {
            refleshEmojiSendBtn();
            toggleFaceImage();
            showMoreorLess(true);
            hideVoiceMode();
            if (listener != null) {
                listener.onToggleEmojiconClicked();
            }
        } else if (id == R.id.iv_face_keyboard) {
            toggleFaceImage();
            if (listener != null) {
                listener.onToggleEmojiconClicked();
            }
            editText.requestFocus();
            displayKeyboard(editText);
        } else {
        }
    }

    private void hideVoiceMode() {
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonPressToSpeak.setVisibility(View.GONE);
    }

    private void showMoreorLess(boolean isShowMore) {
        if (isShowMore) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonLess.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonLess.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示语音图标按钮
     *
     */
    protected void setModeVoice() {
        hideKeyboard();
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        showNormalFaceImage();
        showMoreorLess(true);
    }

    /**
     * 显示键盘图标
     */
    protected void setModeKeyboard() {
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        editText.requestFocus();
        buttonPressToSpeak.setVisibility(View.GONE);
        showMoreorLess(true);
    }

    protected void toggleFaceImage() {
        if (faceNormal.getVisibility() == View.VISIBLE) {
            showSelectedFaceImage();
        } else {
            showNormalFaceImage();
        }
    }

    private void showNormalFaceImage() {
        faceNormal.setVisibility(View.VISIBLE);
        faceKeyboard.setVisibility(View.INVISIBLE);
    }

    private void showSelectedFaceImage() {
        faceNormal.setVisibility(View.INVISIBLE);
        faceKeyboard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExtendAllContainerHide() {
        showNormalFaceImage();
        setModeKeyboard();
    }

    @Override
    public void setInputMessage(CharSequence txtContent) {
        editText.setText(txtContent);
    }

    @Override
    public void setEmojiSendBtn(Button btn) {
        super.setEmojiSendBtn(btn);
        emojiSengBtnEnable = false;
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMsg();
            }
        });
    }

    //输入栏的按钮
    @Override
    public void setInputEmojiSendBtn(Button btn) {
        super.setInputEmojiSendBtn(btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMsg();
            }
        });
    }

    private void refleshEmojiSendBtn() {
        if (editText.getText().length() > 0 && !emojiSengBtnEnable) {
            emojiSendBtn.setEnabled(true);
            emojiSendBtn.setBackgroundResource(R.color.emoji_send_btn_enable_bg_color);
            emojiSengBtnEnable = true;
        } else if(editText.getText().length() == 0 && emojiSengBtnEnable) {
            emojiSendBtn.setEnabled(false);
            emojiSendBtn.setBackgroundResource(R.color.emoji_send_btn_disable_bg_color);
            emojiSengBtnEnable = false;
        }
    }

    private void refleshInputEmojiSendBtn() {
        if (editText.getText().length() > 0) {
            inputEmojiSendBtn.setVisibility(View.VISIBLE);
        } else if(editText.getText().length() == 0) {
            inputEmojiSendBtn.setVisibility(View.GONE);
        }
    }
}