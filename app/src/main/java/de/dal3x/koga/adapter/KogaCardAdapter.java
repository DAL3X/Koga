package de.dal3x.koga.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.dal3x.koga.R;
import de.dal3x.koga.generator.KogaGenerator;
import de.dal3x.koga.menu.Menu;

public class KogaCardAdapter extends RecyclerView.Adapter<KogaCardAdapter.MenuViewHolder> {

    private final List<Menu> menus;
    private final KogaGenerator generator;
    private final AppCompatActivity parentActivity;

    public KogaCardAdapter(List<Menu> menus, KogaGenerator generator, AppCompatActivity parentActivity) {
        this.menus = menus;
        this.generator = generator;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kogaelement, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.setMenu(menu);
        holder.setAdapter(this);
        holder.setGenerator(generator);
        holder.setParentActivity(parentActivity);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private KogaCardAdapter adapter;
        private KogaGenerator generator;
        private AppCompatActivity parentActivity;

        private final TextView name;
        //extend here

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.koga_menu_name);
            itemView.findViewById(R.id.koga_button_reroll_menu).setOnClickListener(button -> {
                generator.reRollMenu(parentActivity, getAdapterPosition());
                adapter.notifyItemChanged(getAdapterPosition());
            });
        }

        public void setMenu(Menu menu) {
            name.setText(menu.getName());
            // extend here
        }

        public void setAdapter(KogaCardAdapter adapter) {
            this.adapter = adapter;
        }

        public void setGenerator(KogaGenerator generator) {
            this.generator = generator;
        }

        public void setParentActivity(AppCompatActivity parentActivity) {
            this.parentActivity = parentActivity;
        }

    }

}
