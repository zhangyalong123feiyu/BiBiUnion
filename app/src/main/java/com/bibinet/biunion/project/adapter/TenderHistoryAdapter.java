package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.HelpTenderHistoryPresenter;
import com.bibinet.biunion.project.bean.HelpTenderHistoryReusltBean;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-1-6.
 */
public class TenderHistoryAdapter extends PageActivityAdapter {//投标协助历史记录
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Context context;
    private List<HelpTenderHistoryReusltBean.ItemBean> historyInfo;
    private HelpTenderHistoryPresenter presenter;

    public TenderHistoryAdapter(Context context, List<HelpTenderHistoryReusltBean.ItemBean> historyInfo, HelpTenderHistoryPresenter presenter) {
        super(context);
        this.context = context;
        this.historyInfo = historyInfo;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return historyInfo.size();
    }


    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_historytender, parent, false);
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
            ((ItemHolder)holder).tenderHelpContent.setText(historyInfo.get(position).getContent());
            ((ItemHolder)holder).contactPerson.setText(historyInfo.get(position).getContact());
            ((ItemHolder)holder).contactType.setText(historyInfo.get(position).getCellPhone());
            ((ItemHolder)holder).tenderDate.setText(historyInfo.get(position).getCreateDateStr());
            if (historyInfo.get(position).getIsEnd()==2) {
                ((ItemHolder)holder).tenderType.setText("已处理");
                ((ItemHolder)holder).tenderType.setTextColor(Color.parseColor("#666666"));
            }else {
                ((ItemHolder) holder).tenderType.setText("待处理");
                ((ItemHolder) holder).tenderType.setTextColor(Color.parseColor("#666666"));
            }
            ((ItemHolder)holder).deleteHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //删除
                    String id = String.valueOf(historyInfo.get(position).getObjectId());
                    presenter.delectHistory(id);
                }
            });
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tenderHelpContent)
        TextView tenderHelpContent;
        @BindView(R.id.contactPerson)
        TextView contactPerson;
        @BindView(R.id.contactType)
        TextView contactType;
        @BindView(R.id.tenderType)
        TextView tenderType;
        @BindView(R.id.tenderHelpDate)
        TextView tenderDate;
        @BindView(R.id.delete_history)
        View deleteHistory;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
