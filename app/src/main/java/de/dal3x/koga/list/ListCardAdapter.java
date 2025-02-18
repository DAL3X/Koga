package de.dal3x.koga.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.dal3x.koga.R;
import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.room.MenuRepository;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.MenuViewHolder> {

    private final List<Menu> menus;

    public ListCardAdapter(List<Menu> menus) {
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
        holder.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private Menu menu;
        private ListCardAdapter adapter;

        private final TextView name;
        //extend here

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.menu_name);
            itemView.findViewById(R.id.deleteMenuButton).setOnClickListener(button -> {
                new MenuRepository(itemView.getContext()).deleteMenu(menu);
                adapter.notifyItemRemoved(getAdapterPosition());
            });
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
            name.setText(menu.getName());
            // extend here
        }

        public void setAdapter(ListCardAdapter adapter) {
            this.adapter = adapter;
        }

    }

}
