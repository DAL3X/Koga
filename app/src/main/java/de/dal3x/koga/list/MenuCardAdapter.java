package de.dal3x.koga.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.dal3x.koga.ListActivity;
import de.dal3x.koga.R;
import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.room.MenuRepository;

public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.MenuViewHolder> {

    private final List<Menu> menus;
    private final ListActivity activity;

    public MenuCardAdapter(List<Menu> menus, ListActivity activity) {
        this.menus = menus;
        this.activity = activity;
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
        holder.setListActivity(activity);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private ListActivity activity;
        private Menu menu;

        private final TextView name;
        //extend here

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.menu_name);
            itemView.findViewById(R.id.deleteMenuButton).setOnClickListener(button -> {
                new MenuRepository(itemView.getContext()).deleteMenu(menu);
                activity.recreate();
            });
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
            name.setText(menu.getName());
            // extend here
        }

        public void setListActivity(ListActivity activity) {
            this.activity = activity;
        }
    }

}
