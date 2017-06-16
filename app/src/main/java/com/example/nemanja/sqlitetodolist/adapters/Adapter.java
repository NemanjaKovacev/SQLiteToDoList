package com.example.nemanja.sqlitetodolist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nemanja.sqlitetodolist.R;
import com.example.nemanja.sqlitetodolist.interfaces.ItemClickListener;
import com.example.nemanja.sqlitetodolist.models.Item;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private final List<Item> itemsList;
    private final ItemClickListener itemClickListener;
    private static final int EDIT = 0;
    private static final int DELETE = 1;

    public Adapter(Context context, List<Item> itemsList, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemsList = itemsList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = itemsList.get(position);
        holder.textViewTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView textViewTitle;
        public final ImageView buttonEdit;
        public final ImageView buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textItem);
            buttonEdit = (ImageView) itemView.findViewById(R.id.iconEdit);
            buttonDelete = (ImageView) itemView.findViewById(R.id.iconDelete);

            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == buttonEdit) {
                if (itemClickListener != null) {
                    int id = itemsList.get(getAdapterPosition()).id;
                    itemClickListener.onClick(id, EDIT, itemsList.get(getAdapterPosition()).title);
                }
            } else if (view == buttonDelete) {
                if (itemClickListener != null) {
                    int id = itemsList.get(getAdapterPosition()).id;
                    itemClickListener.onClick(id, DELETE, null);
                }
            }
        }
    }
}
