package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.activity.CompanyInfoActivity;

/**
 * Created by Wh on 2017-7-6.
 */

public class ShowCompanyInfoSelectDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    private String[] industry = new String[]{"农、林、牧、渔业", "采矿业", "制造业", "电力、热力、燃气及水生产和供应业", "建筑业",
            "批发和零售业", "交通运输、仓储和邮政业", "住宿和餐饮业", "信息传输、软件和信息技术服务业", "金融业", "房地产业", "租赁和商务服务业", "科学研究和技术服务业", "水利、环境和公共设施管理业",
            "居民服务、修理和其他服务业", "教育", "卫生和社会工作", "文化、体育和娱乐业", "公共管理、社会保障和社会组织", "国际组织"};
    private String[] area = new String[]{"北京市", "天津市", "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省"
            , "河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区", "重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区",
            "台湾省", "香港特别行政区", "澳门特别行政区"};

    private ListView mainLV;
    private Context context;
    private int type = 0;

    public ShowCompanyInfoSelectDialog(Context context, int type, OnShowCompanyInfoSelectDialogListener listener) {
        super(context);
        this.context = context;
        this.type = type;
        this.listener = listener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_company_info_select, null, false);
        mainLV = (ListView) view.findViewById(R.id.dia_show_company_info_select_main);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        mainLV.setOnItemClickListener(this);
        mainLV.setAdapter(new DialogSelectAdapter());
        setContentView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String str = "";
        switch (type){
            case 0:
                str = industry[i];
                break;
            case 1:
                str = area[i];
                break;
        }
        dismiss();
        listener.onSelect(str);
    }

    private OnShowCompanyInfoSelectDialogListener listener;
    @Override
    public void onClick(View view) {
        dismiss();
    }

    private class DialogSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            switch (type){
                case 0:
                    return industry.length;
                case 1:
                    return area.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = new TextView(context);
            }
            TextView tv = (TextView) view;
            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, dip2px(context, 33)));
            tv.setGravity(Gravity.CENTER);
            switch (type){
                case 0:
                    tv.setText(industry[i]);
                    break;
                case 1:
                    tv.setText(area[i]);
                    break;
            }
            return view;
        }
    }

    public interface OnShowCompanyInfoSelectDialogListener{
        void onSelect(String s);
    }

    public int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }

}
