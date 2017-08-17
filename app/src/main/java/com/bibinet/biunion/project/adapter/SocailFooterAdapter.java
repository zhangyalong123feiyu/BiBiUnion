package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BannerBean;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.builder.MyViewPager;
import com.bibinet.biunion.project.ui.activity.H5Activity;
import com.bibinet.biunion.project.ui.activity.PlatFormActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.BannerUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-1-6.
 */
public class SocailFooterAdapter extends PageActivityAdapter {
    private static final int TYPE_HEADER = 2; //头部banner
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Activity context;
    private List<ProjectInfoBean.ItemsBean> socailInfos;
    private BannerUtils bannerUtils;
    private List<BannerBean.ItemBean> bannerInfo=new ArrayList<>();
    private int selectType;
    private int detailType;

    public SocailFooterAdapter(Activity context, List<ProjectInfoBean.ItemsBean> socailInfos, List<BannerBean.ItemBean> bannerUrl, int selectType, int detailType) {
        super(context);
        this.context = context;
        this.socailInfos = socailInfos;
        this.bannerInfo=bannerUrl;
        inflater = LayoutInflater.from(context);
        this.selectType=selectType;
        this.detailType=detailType;
    }


    @Override
    protected int getChildItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    protected int getChildCount() {
        return -1;
    }

    public void setBannerUrl(List<BannerBean.ItemBean> bannerInfos){
        this.bannerInfo = bannerInfos;
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_projectinfo, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            ItemHolder itemViewHolder = new ItemHolder(view);
            return itemViewHolder;
        }  else if (viewType == TYPE_HEADER) {
            View headerView = inflater.inflate(R.layout.item_banner, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(headerView);
            return headerViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).companyName.setText(socailInfos.get(position-1).getProjectName());
            ((ItemHolder) holder).projectDescrp.setText(socailInfos.get(position-1).getProjectDescrp());
            ((ItemHolder) holder).projectLoaction.setText(socailInfos.get(position-1).getProjectLocation());
            if(socailInfos.get(position-1).getProjectTime().equals("")){
                ((ItemHolder) holder).projectTime.setText("- - -");
                ((ItemHolder) holder).projectTime.setTextSize(17);
            }else{
                ((ItemHolder) holder).projectTime.setText(socailInfos.get(position-1).getProjectTime());
                ((ItemHolder) holder).projectTime.setTextSize(10);
            }
            String type = socailInfos.get(position-1).getProjectType();
            if(type == null){
                ((ItemHolder) holder).projectTypeMain.setVisibility(View.GONE);
            }else{
                if (type.equals("A")) {
                    ((ItemHolder) holder).projectTypeImage.setImageResource(R.mipmap.shouye_gongcheng);
                    ((ItemHolder) holder).projectTypeMain.setVisibility(View.VISIBLE);
                } else if (type.equals("B")) {
                    ((ItemHolder) holder).projectTypeImage.setImageResource(R.mipmap.shouye_huowu);
                    ((ItemHolder) holder).projectTypeMain.setVisibility(View.VISIBLE);
                } else if(type.equals("C")){
                    ((ItemHolder) holder).projectTypeImage.setImageResource(R.mipmap.shouye_fuw);
                    ((ItemHolder) holder).projectTypeMain.setVisibility(View.VISIBLE);
                }else{
                    ((ItemHolder) holder).projectTypeMain.setVisibility(View.GONE);
                }
            }

            if(socailInfos.get(position-1).getProjectIsTop().equals("1")){
                ((ItemHolder) holder).projectIsTop.setVisibility(View.VISIBLE);
            }else{
                ((ItemHolder) holder).projectIsTop.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = position - 1;
                    Intent intent = new Intent(context, H5Activity.class);
                    intent.putExtra("detailUrl", socailInfos.get(p).getProjectUrl());
                    intent.putExtra("type",socailInfos.get(p).getProjectType());
                    intent.putExtra("projectCode",socailInfos.get(p).getProjectCode());
                    intent.putExtra("selectType",String.valueOf(selectType));
                    intent.putExtra("detailType",String.valueOf(detailType));
                    intent.putExtra("isCollect", socailInfos.get(p).isProjectIsCollect());
                    intent.putExtra("pos", p);
                    context.startActivityForResult(intent, H5Activity.requestCode);
                }
            });
        }else if (holder instanceof HeaderViewHolder) {
            if (bannerUtils==null) {
                bannerUtils = new BannerUtils(context,((HeaderViewHolder)holder).viewpager,((HeaderViewHolder)holder).groupContain, bannerInfo);
                bannerUtils.startPlayBanner();
            }else {
                return;
            }

        }
    }

    @Override
    public int getItemCount() {
        int count = socailInfos.size() > 0 ? socailInfos.size() + 2 : 2;
        return count;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.bibiPlatform)
        LinearLayout bibiPlatform;
        @BindView(R.id.finacePlatform)
        LinearLayout finacePlatform;
        @BindView(R.id.tenderPlatform)
        LinearLayout tenderPlatform;
        @BindView(R.id.servicePlatform)
        LinearLayout servicePlatform;
        @BindView(R.id.viewpager)
        MyViewPager viewpager;
        @BindView(R.id.group_contain)
        LinearLayout groupContain;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bibiPlatform.setOnClickListener(this);
            finacePlatform.setOnClickListener(this);
            tenderPlatform.setOnClickListener(this);
            servicePlatform.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, PlatFormActivity.class);
            switch (v.getId()) {
                case R.id.bibiPlatform:
                    intent.putExtra("Type","1");
                    context.startActivity(intent);
                    break;
                case R.id.finacePlatform:
                    intent.putExtra("Type","2");
                    context.startActivity(intent);
                    break;
                case R.id.tenderPlatform:
                    intent.putExtra("Type","3");
                    context.startActivity(intent);
                    break;
                case R.id.servicePlatform:
                    intent.putExtra("Type","4");
                    context.startActivity(intent);
                    break;
            }
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.projectTypeImage)
        ImageView projectTypeImage;
        @BindView(R.id.projectTypeMain)
        View projectTypeMain;
        @BindView(R.id.companyName)
        TextView companyName;
        @BindView(R.id.projectDescrp)
        TextView projectDescrp;
        @BindView(R.id.projectLoaction)
        TextView projectLoaction;
        @BindView(R.id.projectTime)
        TextView projectTime;
        @BindView(R.id.projectIsTop)
        View projectIsTop;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
