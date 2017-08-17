package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.SelectAddressAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.bean.cityselectbean.AddressTag;
import com.bibinet.biunion.project.bean.cityselectbean.City;
import com.bibinet.biunion.project.bean.cityselectbean.County;
import com.bibinet.biunion.project.utils.AssetsUtils;
import com.bibinet.biunion.project.utils.FirstLetterUtil;
import com.bibinet.biunion.project.utils.LoactionUtils;
import com.bibinet.biunion.project.utils.cityselectutil.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 吴昊 on 2017-5-8.
 */

public class SelectAddressActivity extends BaseActivity implements TextWatcher, SelectAddressAdapter.OnSelectAddressAdapter {
    public final static String LOCATION = "location";
    @BindView(R.id.act_select_address_main)
    ListView mainLV;
    @BindView(R.id.act_select_address_search)
    EditText searchET;
    @BindView(R.id.act_select_address_cancel)
    View cancelV;
    @BindView(R.id.act_home_more_go_back_icon)
    View goBackV;
    @BindView(R.id.act_select_address_location)
    View locationV;
    //右部快捷导航
    @BindView(R.id.act_select_address_navigation)
    LinearLayout navigationLL;
    @BindView(R.id.act_select_address_current_location)
    TextView currentLocation;
    List<City> provinceList;
    List<City> cityList;
    List<County> countyList;
    List<City> totalList;
    //实际数据
    private List<AddressTag> addressTagList;
    //手动删除一下没有城市的首字母
    private String [] navigation = {"热", "门", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P",
    "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    private LoactionUtils loactionUtils;
    private SelectAddressAdapter adapter;
    private Intent intent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        loactionUtils = new LoactionUtils(this, currentLocation);
        loactionUtils.startLoaction();

        searchET.addTextChangedListener(this);
        addressTagList = new ArrayList<AddressTag>();
        //解析数据 不用执行 目的已经达到
//        parase();
        //使用已经制成的列表对象 这算是一个耗时操作 看情况处理
        addressTagList = getData();
//        LogUtils.e("tag1=", "-" + addressTagList.toString());
        //出了首字母分类 还需要加入热门分类
        AddressTag addressTag = new AddressTag(-1, "热门城市", new ArrayList<City>());
        addressTag.getCityList().add(new City("110000", "北京市"));
        addressTag.getCityList().add(new City("310000", "上海市"));
        addressTag.getCityList().add(new City("440100", "广州市"));
        addressTag.getCityList().add(new City("440300", "深圳市"));
        addressTag.getCityList().add(new City("510100", "成都市"));
        addressTag.getCityList().add(new City("330100", "杭州市"));
        addressTagList.add(0, addressTag);
        //根据数据集合 生成界面数据集合 适配器做处理
        adapter = new SelectAddressAdapter(this, addressTagList, this);
        mainLV.setAdapter(adapter);
        //触摸滑动效果监听
        navigationLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //将高度平均分成 adapter.getTagPostionList().size()+1 分 每一份的值获取一下
                int count = adapter.getTagPostionList().size()+1;
                int height = navigationLL.getMeasuredHeight() / count; //每一份的高度
                //根据当前高度 计算位置 公式：触摸高度/每一份的高度
                int pos = (int) (event.getY() / height);
                int scroolPos = 0;
                if(pos>=count || pos <0){
                    return true;
                }
                if(pos==0 || pos == 1){
                    scroolPos = adapter.getTagPostionList().get(0);
                }else{
                    scroolPos = adapter.getTagPostionList().get(pos-1);
                }
                mainLV.setSelection(scroolPos);
                return true;
            }
        });
        //填充右侧快捷导航 循环数量是实际标签的数量+1 热门算两个
        for(int i=0 ; i<adapter.getTagPostionList().size()+1 ; i++){
            final TextView tv = new TextView(this);
            tv.setText(navigation[i]);
            tv.setTextColor(Color.parseColor("#f99e32"));
            tv.setTextSize(12);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            navigationLL.addView(tv);
        }
    }

    private List<AddressTag> getData() {
        ObjectInputStream ois=null;
        //从本地序列化中提取数据
        try {
            //address.code
            ois = new ObjectInputStream(AssetsUtils.getInputStream(this, "address.code"));
            return (List<AddressTag>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    //搜索字体 改变时效果
    @Override
    public void afterTextChanged(Editable editable) {
        //获取关键字
        String key = searchET.getText().toString();
        //根据关键字更新界面
        //有字符 显示取消 隐藏返回 隐藏定位那一块 快捷导航隐藏
        if(key.equals("")){
            cancelV.setVisibility(View.GONE);
            goBackV.setVisibility(View.VISIBLE);
            locationV.setVisibility(View.VISIBLE);
            navigationLL.setVisibility(View.VISIBLE);
        }else{
            cancelV.setVisibility(View.VISIBLE);
            goBackV.setVisibility(View.GONE);
            locationV.setVisibility(View.GONE);
            navigationLL.setVisibility(View.GONE);
        }
        //根据key对数据进行过滤
        adapter.search(key);
    }

    @OnClick(R.id.act_home_more_go_back_icon)
    void back(){
        goBack();
    }

    private void goBack(){
        finish();
    }

    @OnClick(R.id.act_select_address_cancel)
    void cancel(){
        searchET.setText("");
    }

    //点击 标签 设置 退出改界面
    @Override
    public void onAddressClick(City city) {
        City citySelect = city;
        intent.putExtra("resultCityNameHot",citySelect);
        setResult(2,intent);
        goBack();
    }

    //选择定位
    @OnClick(R.id.act_select_address_current_location)
    void location(){
        finish();
    }

    //1，解析原来的json数据 得到三级的地区数据
    //2，对数据进行过滤（取需要的数据）
    //3，对需要的数据进行新的实体类拼接
    //4，转换成新的json
    //5，用新的json解析实体类操作界面 该方法不在调用 保存好备用
    private void parase() {
        String json = AssetsUtils.getJsonFile(this, "city_code.json");
        provinceList = new ArrayList<City>();
        cityList = new ArrayList<City>();
        countyList = new ArrayList<County>();
        totalList = new ArrayList<City>();

        try {
            JSONObject o = new JSONObject(json);
            Iterator<String> it = o.keys();
            while (it.hasNext()) {
                String key = it.next();  //一级省份的编码
                JSONObject value = o.getJSONObject(key);
                Iterator<String> it1 = value.keys();
                //不用循环 取第一个 得到的key是山西省 北京市等
                String key1 = it1.next();  //一级省份的名称
                //大部分省份不需要 要过滤一下
                provinceList.add(new City(key, key1));

                //下一级
                JSONObject o1 = value.getJSONObject(key1);
                Iterator<String> it2 = o1.keys();
                while (it2.hasNext()) {
                    String key2 = it2.next();  //二级 城市的编码
                    JSONObject value1 = o1.getJSONObject(key2);
                    Iterator<String> it3 = value1.keys();
                    String key3 = it3.next();  //二级 城市的名称
                    //所有的市 大部分不用过滤 个别过滤
                    cityList.add(new City(key2, key3));

                    //下一级
                    JSONObject o2 = value1.getJSONObject(key3);
                    Iterator<String> it4 = o2.keys();
                    while (it4.hasNext()) {
                        String key4 = it4.next(); //三级 区域的编码
                        //直接根据key获取
                        String key5 = o2.getString(key4);  //三级 城市的名称

                        //所有的区域 应该不需要过滤
                        countyList.add(new County(key4, key5, key2, key3));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("res", "-" + provinceList.toString());
        Log.e("res=", "-" + provinceList.size());
        Log.e("res2", "-" + cityList.toString());
        Log.e("res2=", "-" + cityList.size());
        Log.e("res3", "-" + countyList.toString());
        Log.e("res3=", "-" + countyList.size());

        //进行过滤
        //省份过滤
        //去掉所有常规省 只要直辖市
        //利用代号码进行 逐一的过滤
        for (int i = 0; i < provinceList.size(); i++) {
            City city = provinceList.get(i);
            int no = Integer.parseInt(city.getNo());
            switch (no) {
                case 110000:
                case 120000:
                case 310000:
                case 500000:
                    continue;
            }
            provinceList.remove(i);
            i--;
        }
        Log.e("res4", "-" + provinceList.toString());
        Log.e("res4=", "-" + provinceList.size());

        //城市的过滤 只排除直辖市
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);
            int no = Integer.parseInt(city.getNo());
            switch (no) {
                case 110100:
                case 120100:
                case 310100:
                case 500100:
                    cityList.remove(i);
                    i--;
                    break;
            }
        }

        Log.e("res5", "-" + cityList.toString());
        Log.e("res5=", "-" + cityList.size());

        //县需要过滤掉市辖区
        for(int i=0 ; i<countyList.size() ; i++){
            if(countyList.get(i).getName().equals("市辖区")){
                countyList.remove(i);
                i--;
            }
        }
        //把过滤后的省和市  还有县拼接到一起
        totalList.addAll(provinceList);
        totalList.addAll(cityList);
        totalList.addAll(countyList);

        //循环地址标签列表 将所有的地址进行归类
        //根据26个字母 地区首字母归类
        for (int i = 0; i < 26; i++) {
            String n = "";
            switch (i) {
                case 0:
                    n = "A";
                    break;
                case 1:
                    n = "B";
                    break;
                case 2:
                    n = "C";
                    break;
                case 3:
                    n = "D";
                    break;
                case 4:
                    n = "E";
                    break;
                case 5:
                    n = "F";
                    break;
                case 6:
                    n = "G";
                    break;
                case 7:
                    n = "H";
                    break;
                case 8:
                    n = "I";
                    break;
                case 9:
                    n = "J";
                    break;
                case 10:
                    n = "K";
                    break;
                case 11:
                    n = "L";
                    break;
                case 12:
                    n = "M";
                    break;
                case 13:
                    n = "N";
                    break;
                case 14:
                    n = "O";
                    break;
                case 15:
                    n = "P";
                    break;
                case 16:
                    n = "Q";
                    break;
                case 17:
                    n = "R";
                    break;
                case 18:
                    n = "S";
                    break;
                case 19:
                    n = "T";
                    break;
                case 20:
                    n = "U";
                    break;
                case 21:
                    n = "V";
                    break;
                case 22:
                    n = "W";
                    break;
                case 23:
                    n = "X";
                    break;
                case 24:
                    n = "Y";
                    break;
                case 25:
                    n = "Z";
                    break;
            }
            AddressTag addressTag = new AddressTag(i, n, new ArrayList<City>());
            addressTagList.add(addressTag);
        }

        Log.e("tag=", "-" + addressTagList.toString());
        //循环所有的数据 对比首字母
        for (int i = 0; i < totalList.size(); i++) {
            City city = totalList.get(i);
            String name = city.getName();
            String first = FirstLetterUtil.getFirstLetter(name);
            for (int j = 0; j < addressTagList.size(); j++) {
                AddressTag tag = addressTagList.get(j);
                if(first.startsWith(tag.getName().toLowerCase())){
                    tag.getCityList().add(city);
                }
//                LogUtils.e("t=", "-" + first +"-" + "-"+tag.name.toLowerCase());
            }
        }
        Log.e("tag1=", "-" + addressTagList.toString());
        ObjectOutputStream oos=null;
        //保存到本地序列化
        try {
            oos = new ObjectOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"address.code"));
            oos.writeObject(addressTagList);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
