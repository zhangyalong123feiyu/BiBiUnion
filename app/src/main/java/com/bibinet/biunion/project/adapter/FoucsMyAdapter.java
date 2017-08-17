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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.FoucsedBean;
import com.bibinet.biunion.project.ui.activity.H5Activity;
import com.bibinet.biunion.project.ui.activity.H5Activity1;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-6-23.
 */

public class FoucsMyAdapter extends PageActivityAdapter {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Activity context;
    private List<FoucsedBean.ItemBean> socailInfos;

    public FoucsMyAdapter(Activity context, List<FoucsedBean.ItemBean> socailInfos) {
        super(context);
        this.context = context;
        this.socailInfos = socailInfos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return socailInfos.size();
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.adapter_foucs_my, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FoucsItemHolder itemViewHolder = new FoucsItemHolder(view);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FoucsItemHolder) {
            ((FoucsItemHolder) holder).projcetName.setText(socailInfos.get(position).getProjectName());
            ((FoucsItemHolder) holder).projcetDescrp.setText(socailInfos.get(position).getProjectDescrp());
            ((FoucsItemHolder) holder).projectLocation.setText(socailInfos.get(position).getProjectLocation());
            ((FoucsItemHolder) holder).projcetOffer.setText(socailInfos.get(position).getProjectAmount());
            ((FoucsItemHolder) holder).publishTime.setText(socailInfos.get(position).getProjectPublishTime());
            String title = socailInfos.get(position).getProjectTitle();
            if(title.equals("")){
                ((FoucsItemHolder) holder).projectTitle.setVisibility(View.GONE);
            }else{
                ((FoucsItemHolder) holder).projectTitle.setText(title);
                ((FoucsItemHolder) holder).projectTitle.setVisibility(View.VISIBLE);
            }
            if (socailInfos.get(position).getProjectType().equals("A")) {
                ((FoucsItemHolder) holder).projectImage.setImageResource(R.mipmap.shouye_gongcheng);
                ((FoucsItemHolder) holder).projectType.setText("工程");
            } else if (socailInfos.get(position).getProjectType().equals("B")) {
                ((FoucsItemHolder) holder).projectImage.setImageResource(R.mipmap.shouye_huowu);
                ((FoucsItemHolder) holder).projectType.setText("货物");
            } else {
                ((FoucsItemHolder) holder).projectImage.setImageResource(R.mipmap.shouye_fuw);
                ((FoucsItemHolder) holder).projectType.setText("服务");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, H5Activity1.class);
                    intent.putExtra("detailUrl", socailInfos.get(position).getProjectUrl());
                    intent.putExtra("projectCode", socailInfos.get(position).getProjectCode());
                    intent.putExtra("isCollect", socailInfos.get(position).isProjectIsCollect());
                    intent.putExtra("pos", position);
                    intent.putExtra("collectType", socailInfos.get(position).getCollectType());
                    intent.putExtra("detailName", socailInfos.get(position).getDetailName());
                    context.startActivityForResult(intent, H5Activity1.requestCode);
                }
            });
        }
    }

    class FoucsItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_foucs_my_title)
        TextView projectTitle;
        @BindView(R.id.ada_foucs_my_name)
        TextView projcetName;
        @BindView(R.id.ada_foucs_my_offer)
        TextView projcetOffer;
        @BindView(R.id.ada_foucs_my_descrp)
        TextView projcetDescrp;
        @BindView(R.id.ada_foucs_my_type_img)
        ImageView projectImage;
        @BindView(R.id.ada_foucs_my_type)
        TextView projectType;
        @BindView(R.id.ada_foucs_my_location)
        TextView projectLocation;
        @BindView(R.id.ada_foucs_my_date)
        TextView publishTime;

        public FoucsItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}


