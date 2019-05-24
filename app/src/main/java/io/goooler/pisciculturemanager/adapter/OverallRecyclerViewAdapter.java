package io.goooler.pisciculturemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.OverallSingleBean;

public class OverallRecyclerViewAdapter extends RecyclerView.Adapter<OverallRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<OverallSingleBean> beans;

    private OnItemClickListener onItemClickListener;

    public OverallRecyclerViewAdapter(List<OverallSingleBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.card_overall, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i <= beans.size()) {
            viewHolder.itemView.setTag(i);
            viewHolder.titleTxt.setText(beans.get(i).getName());
            viewHolder.valueTxt.setText(beans.get(i).getValue() + Constants.NULL_STRING);
        }

    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
