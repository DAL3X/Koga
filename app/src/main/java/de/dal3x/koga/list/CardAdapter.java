package de.dal3x.koga.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.dal3x.koga.R;
import de.dal3x.koga.menu.Menu;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MenuViewHolder> {

    private final List<Menu> menus;

    public CardAdapter(List<Menu> menus) {
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listelement, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.setMenu(menu);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        //extend here

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.menu_name);
        }

        public void setMenu(Menu m) {
            name.setText(m.getName());
            // extend here
        }
    }

}
