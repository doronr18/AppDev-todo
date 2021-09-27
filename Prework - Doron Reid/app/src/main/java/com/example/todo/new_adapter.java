package com.example.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Responsible for displaying data from the model into a row in the recycler view
public class new_adapter extends RecyclerView.Adapter<new_adapter.ViewHolder>{

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        void OnItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public new_adapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {

        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a view holder and return it
        return new ViewHolder(todoView);
    }

    // resposible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //grab the item at the posistion
        String item = items.get(position);
        //bind the item to a specified view holder
        holder.bind(item);

    }

    //tells the rv how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy access to view that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);

        }

        //Update the view inside the view holder with this data
        public void bind(String items) {

            tvItem.setText(items);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                    return true;
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify the listener which position was long pressed
                    longClickListener.OnItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
