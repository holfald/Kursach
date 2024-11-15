package com.mirea.kt.ribo.model;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.ribo.R;

import java.util.List;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.CalculationViewHolder> {

    private Context context;
    private List<CalculationItem> calculationItems;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CalculationAdapter(Context context, List<CalculationItem> calculationItems, OnItemClickListener listener) {
        this.context = context;
        this.calculationItems = calculationItems;
        this.listener = listener;
    }

    @Override
    public CalculationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calculation, parent, false);
        return new CalculationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalculationViewHolder holder, int position) {
        CalculationItem currentItem = calculationItems.get(position);
        holder.nameTextView.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return calculationItems.size();
    }

    public class CalculationViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public CalculationViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.calculation_name);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
