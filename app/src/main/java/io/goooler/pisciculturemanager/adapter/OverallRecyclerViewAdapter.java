package io.goooler.pisciculturemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.OverallSingleBean;

/**
 * 第一个 fragment 上的 recyclerView 的适配器
 */
public class OverallRecyclerViewAdapter extends RecyclerView.Adapter<OverallRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {
    private List<OverallSingleBean> beans;
    private boolean editable;

    private OnItemClickListener onItemClickListener;

    public OverallRecyclerViewAdapter(List<OverallSingleBean> beans) {
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_overall, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (beans != null && i <= beans.size()) {
            viewHolder.itemView.setTag(i);
            viewHolder.titleTxt.setText(beans.get(i).getName());
            viewHolder.valueTxt.setText(beans.get(i).getValueString());
            viewHolder.setEditable(editable);
        }
    }

    @Override
    public int getItemCount() {
        return beans != null ? beans.size() : 0;
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

    /**
     * 设置所有 item 上的 EditText 的可编辑状态
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        EditText valueTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.card_title);
            valueTxt = itemView.findViewById(R.id.card_value);
            //值默认只做展示不可编辑
            setEditable(false);
        }

        public double getValue() {
            return Double.valueOf(valueTxt.getText().toString());
        }

        /**
         * 设置单个 EditText 的可编辑状态
         *
         * @param editable 可编辑与否
         */
        public void setEditable(boolean editable) {
            //设置输入框中的光标不可见
            valueTxt.setCursorVisible(editable);
            //无焦点
            valueTxt.setFocusable(editable);
            //触摸时也得不到焦点
            valueTxt.setFocusableInTouchMode(editable);
        }
    }

    public interface OnItemClickListener {
        /**
         * 自定义的 recyclerView 的列表点击回调
         *
         * @param view     获取的单个 itemView
         * @param position 获取的位置
         */
        void onItemClick(View view, int position);
    }
}
