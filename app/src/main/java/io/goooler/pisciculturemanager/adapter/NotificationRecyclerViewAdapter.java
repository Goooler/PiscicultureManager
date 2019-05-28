package io.goooler.pisciculturemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.WarnningDataBean;

/**
 * 第三个 fragment 上的 recyclerView 的适配器
 * TODO: 有时间考虑做个适配器的封装
 */

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {
    private List<WarnningDataBean> beans;

    private OnItemClickListener onItemClickListener;

    public NotificationRecyclerViewAdapter(List<WarnningDataBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_notification, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position <= beans.size()) {
            holder.itemView.setTag(position);
            holder.titleTxt.setText(beans.get(position).getDateString());
            holder.valueTxt.setText(beans.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public void setOnItemClickListener(NotificationRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView valueTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.card_title);
            valueTxt = itemView.findViewById(R.id.card_value);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
