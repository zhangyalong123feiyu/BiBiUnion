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
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.ui.activity.H5Activity;
import com.bibinet.biunion.project.ui.activity.PlatFormActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.AdapterHolderUtils;
import com.bibinet.biunion.project.utils.BannerUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-1-6.
 */
public class MoreProjectAdapter extends PageActivityAdapter {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Activity context;
    private List<ProjectInfoBean.ItemsBean> socailInfos;
    private String selectType = "";
    private String detailType = "";

    public MoreProjectAdapter(Activity context, List<ProjectInfoBean.ItemsBean> socailInfos, String selectType, String detailType) {
        super(context);
        this.context = context;
        this.socailInfos = socailInfos;
        this.selectType = selectType;
        this.detailType = detailType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.adapter_more_project, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            ItemHolder itemViewHolder = new ItemHolder(view);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).projcetName.setText(socailInfos.get(position).getProjectName());
            ((ItemHolder) holder).projcetDescrp.setText(socailInfos.get(position).getProjectDescrp());
            ((ItemHolder) holder).projectLocation.setText(socailInfos.get(position).getProjectLocation());
            ((ItemHolder) holder).projcetOffer.setText(socailInfos.get(position).getProjectAmount());
            ((ItemHolder) holder).publishTime.setText(socailInfos.get(position).getProjectPublishTime());
            String title = socailInfos.get(position).getProjectTitle();
            if(title.equals("")){
                ((ItemHolder) holder).projectTitle.setVisibility(View.GONE);
            }else{
                ((ItemHolder) holder).projectTitle.setText(title);
                ((ItemHolder) holder).projectTitle.setVisibility(View.VISIBLE);
            }
            String type = socailInfos.get(position).getProjectType();
            ItemHolder h = ((ItemHolder) holder);
            AdapterHolderUtils.setType(h.projectTypeMain, h.projectImage, h.projectType, type);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, H5Activity.class);
                    intent.putExtra("detailUrl", socailInfos.get(position).getProjectUrl());
                    intent.putExtra("projectCode", socailInfos.get(position).getProjectCode());
                    intent.putExtra("selectType", selectType);
                    intent.putExtra("detailType", detailType);
                    intent.putExtra("isCollect", socailInfos.get(position).isProjectIsCollect());
                    intent.putExtra("pos", position);
                    context.startActivityForResult(intent, H5Activity.requestCode);
                }
            });
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return socailInfos.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_more_title)
        TextView projectTitle;
        @BindView(R.id.ada_more_name)
        TextView projcetName;
        @BindView(R.id.ada_more_offer)
        TextView projcetOffer;
        @BindView(R.id.ada_more_descrp)
        TextView projcetDescrp;
        @BindView(R.id.ada_more_type_img)
        ImageView projectImage;
        @BindView(R.id.ada_more_type)
        TextView projectType;
        @BindView(R.id.ada_more_type_main)
        View projectTypeMain;
        @BindView(R.id.ada_more_project)
        TextView projectLocation;
        @BindView(R.id.ada_more_date)
        TextView publishTime;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
