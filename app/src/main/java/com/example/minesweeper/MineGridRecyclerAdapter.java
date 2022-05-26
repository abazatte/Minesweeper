package com.example.minesweeper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MineGridRecyclerAdapter extends RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder>{

    private List<Cell> cells;
    private onCellClickListener listener;

    public MineGridRecyclerAdapter(List<Cell> cells, onCellClickListener listener){
        this.cells=cells;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MineTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new MineTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells){
        this.cells = cells;
        notifyDataSetChanged();
    }

    class MineTileViewHolder extends RecyclerView.ViewHolder {

        TextView valueTextView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);
            // Das selbe wie html ->value = documents.getElementsById(blabla...)
            valueTextView = itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell){
            itemView.setBackgroundColor(Color.DKGRAY);
            itemView.setOnClickListener(v -> listener.onCellClick(cell));

            if(cell.isRevealed()){
                if(cell.getValue() == Cell.BOMB){
                    valueTextView.setText(R.string.bomb);
                    itemView.setBackgroundColor(Color.RED);
                } else if ((cell.getValue() == Cell.BLANK)){
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    if(cell.getValue() == 1){
                        valueTextView.setTextColor(Color.BLUE);
                        itemView.setBackgroundColor(Color.WHITE);
                    } else if (cell.getValue() == 2){
                        valueTextView.setTextColor(Color.GREEN);
                        itemView.setBackgroundColor(Color.WHITE);
                    } else if (cell.getValue() >= 3){
                        valueTextView.setTextColor(Color.RED);
                        itemView.setBackgroundColor(Color.WHITE);
                    }
                }
            } else if (cell.isFlaged()){
                valueTextView.setText(R.string.flag);
            }

        }

    }
}
