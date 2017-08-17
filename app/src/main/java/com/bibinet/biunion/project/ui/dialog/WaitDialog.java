package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.view.WriteTenderHistoryActivityView;
import com.bibinet.biunion.project.ui.custom.WaitView;


/**
 * Created by bibinet on 2017-5-3.
 */

public class WaitDialog extends Dialog{

    private WaitView waitView;
    public WaitDialog(Context context) {
        super(context);
        initDialog();
        setContentView(R.layout.dialog_wait);
        waitView = (WaitView) findViewById(R.id.dia_wait);
    }

    public void open(){
        waitView.start();
        show();
    }

    public void close(){
        waitView.stop();
        dismiss();
    }

    private void initDialog(){
        /*
         * 鑾峰彇鍦ｈ癁妗嗙殑绐楀彛瀵硅薄鍙婂弬鏁板璞′互淇敼瀵硅瘽妗嗙殑甯冨眬璁剧疆,
         * 鍙互鐩存帴璋冪敤getWindow(),琛ㄧず鑾峰緱杩欎釜Activity鐨刉indow
         * 瀵硅薄,杩欐牱杩欏彲浠ヤ互鍚屾牱鐨勬柟寮忔敼鍙樿繖涓狝ctivity鐨勫睘鎬?
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        /*
         * lp.x涓巐p.y琛ㄧず鐩稿浜庡師濮嬩綅缃殑鍋忕Щ.
         * 褰撳弬鏁板€煎寘鍚獹ravity.LEFT鏃?瀵硅瘽妗嗗嚭鐜板湪宸﹁竟,鎵€浠p.x灏辫〃绀虹浉瀵瑰乏杈圭殑鍋忕Щ,璐熷€煎拷鐣?
         * 褰撳弬鏁板€煎寘鍚獹ravity.RIGHT鏃?瀵硅瘽妗嗗嚭鐜板湪鍙宠竟,鎵€浠p.x灏辫〃绀虹浉瀵瑰彸杈圭殑鍋忕Щ,璐熷€煎拷鐣?
         * 褰撳弬鏁板€煎寘鍚獹ravity.TOP鏃?瀵硅瘽妗嗗嚭鐜板湪涓婅竟,鎵€浠p.y灏辫〃绀虹浉瀵逛笂杈圭殑鍋忕Щ,璐熷€煎拷鐣?
         * 褰撳弬鏁板€煎寘鍚獹ravity.BOTTOM鏃?瀵硅瘽妗嗗嚭鐜板湪涓嬭竟,鎵€浠p.y灏辫〃绀虹浉瀵逛笅杈圭殑鍋忕Щ,璐熷€煎拷鐣?
         * 褰撳弬鏁板€煎寘鍚獹ravity.CENTER_HORIZONTAL鏃?
         * ,瀵硅瘽妗嗘按骞冲眳涓?鎵€浠p.x灏辫〃绀哄湪姘村钩灞呬腑鐨勪綅缃Щ鍔╨p.x鍍忕礌,姝ｅ€煎悜鍙崇Щ鍔?璐熷€煎悜宸︾Щ鍔?
         * 褰撳弬鏁板€煎寘鍚獹ravity.CENTER_VERTICAL鏃?
         * ,瀵硅瘽妗嗗瀭鐩村眳涓?鎵€浠p.y灏辫〃绀哄湪鍨傜洿灞呬腑鐨勪綅缃Щ鍔╨p.y鍍忕礌,姝ｅ€煎悜鍙崇Щ鍔?璐熷€煎悜宸︾Щ鍔?
         * gravity鐨勯粯璁ゅ€间负Gravity.CENTER,鍗矴ravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 鏈潵setGravity鐨勫弬鏁板€间负Gravity.LEFT | Gravity.TOP鏃跺璇濇搴斿嚭鐜板湪绋嬪簭鐨勫乏涓婅,浣嗗湪
         * 鎴戞墜鏈轰笂娴嬭瘯鏃跺彂鐜拌窛宸﹁竟涓庝笂杈归兘鏈変竴灏忔璺濈,鑰屼笖鍨傜洿鍧愭爣鎶婄▼搴忔爣棰樻爮涔熻绠楀湪鍐呬簡,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM涓嶨ravity.RIGHT閮芥槸濡傛,鎹竟鐣屾湁涓€灏忔璺濈
         */
//        lp.x = 8; // 鏂颁綅缃甔鍧愭爣
//        lp.y = 12; // 鏂颁綅缃甕鍧愭爣
//        lp.width = 300; // 瀹藉害
//        lp.height = 300; // 楂樺害
//        lp.alpha = 0.85f; // 閫忔槑搴?

        // 褰揥indow鐨凙ttributes鏀瑰彉鏃剁郴缁熶細璋冪敤姝ゅ嚱鏁?鍙互鐩存帴璋冪敤浠ュ簲鐢ㄤ笂闈㈠绐楀彛鍙傛暟鐨勬洿鏀?涔熷彲浠ョ敤setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }


}
