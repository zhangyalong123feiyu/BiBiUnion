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
import com.bibinet.biunion.project.bean.SearchResultBean;
import com.bibinet.biunion.project.ui.activity.H5Activity;
import com.bibinet.biunion.project.ui.activity.H5Activity1;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.AdapterHolderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-1-6.
 */
public class SearchActivityAdapter extends PageActivityAdapter {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Activity context;
    private List<SearchResultBean.ItemsBean> socailInfos;

    public SearchActivityAdapter(Activity context, List<SearchResultBean.ItemsBean> socailInfos) {
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
            View view = inflater.inflate(R.layout.adapter_search_result, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            SearchItemHolder itemViewHolder = new SearchItemHolder(view);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchItemHolder) {
            //界面
            ((SearchItemHolder) holder).projcetName.setText(socailInfos.get(position).getProjectName());
            ((SearchItemHolder) holder).projcetDescrp.setText(socailInfos.get(position).getProjectDescrp());
            ((SearchItemHolder) holder).projectLocation.setText(socailInfos.get(position).getProjectLocation());
            ((SearchItemHolder) holder).projcetOffer.setText(socailInfos.get(position).getProjectAmount());
            ((SearchItemHolder) holder).publishTime.setText(socailInfos.get(position).getProjectPublishTime());
            String title = socailInfos.get(position).getProjectTitle();
            if (title.equals("")) {
                ((SearchItemHolder) holder).projectTitle.setVisibility(View.GONE);
            } else {
                ((SearchItemHolder) holder).projectTitle.setText(title);
                ((SearchItemHolder) holder).projectTitle.setVisibility(View.VISIBLE);
            }
            String type = socailInfos.get(position).getProjectType();
            SearchItemHolder h = ((SearchItemHolder) holder);
            AdapterHolderUtils.setType(h.projectTypeMain, h.projectImage, h.projectType, type);

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

    class SearchItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_search_result_title)
        TextView projectTitle;
        @BindView(R.id.ada_search_result_name)
        TextView projcetName;
        @BindView(R.id.ada_search_result_offer)
        TextView projcetOffer;
        @BindView(R.id.ada_search_result_descrp)
        TextView projcetDescrp;
        @BindView(R.id.ada_search_result_type_img)
        ImageView projectImage;
        @BindView(R.id.ada_search_result_type)
        TextView projectType;
        @BindView(R.id.ada_search_result_type_main)
        View projectTypeMain;
        @BindView(R.id.ada_search_result_location)
        TextView projectLocation;
        @BindView(R.id.ada_search_result_date)
        TextView publishTime;

        public SearchItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

