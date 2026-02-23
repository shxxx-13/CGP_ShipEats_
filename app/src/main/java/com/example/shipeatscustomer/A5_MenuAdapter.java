package com.example.shipeatscustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class A5_MenuAdapter extends RecyclerView.Adapter<A5_MenuAdapter.MenuViewHolder> {
    private Context context;
    private List<A5_MenuItem> menuList;

    public A5_MenuAdapter(Context context, List<A5_MenuItem> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_it_menuitem, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        A5_MenuItem item = menuList.get(position);
        holder.tvName.setText(item.name);
        holder.tvPrice.setText("RM " + item.price);

        // Handle Edit Flow
        holder.itemView.setOnClickListener(v ->
                AdminDialogHelper.showEditMenuDialog(context, item, false));

        // Handle Delete Flow
        holder.btnRemove.setOnClickListener(v ->
                AdminDialogHelper.showDeleteConfirmDialog(context, item.id));
    }

    @Override
    public int getItemCount() { return menuList.size(); }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        Button btnRemove;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}