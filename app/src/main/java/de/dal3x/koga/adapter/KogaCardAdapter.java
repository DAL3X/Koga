package de.dal3x.koga.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.dal3x.koga.R;
import de.dal3x.koga.generator.KogaGenerator;
import de.dal3x.koga.menu.Menu;

public class KogaCardAdapter extends RecyclerView.Adapter<KogaCardAdapter.MenuViewHolder> {

    private final List<Menu> menus;
    private final KogaGenerator generator;
    private final Context context;

    public KogaCardAdapter(List<Menu> menus, KogaGenerator generator, Context context) {
        this.menus = menus;
        this.generator = generator;
        this.context = context;
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
        holder.setContext(context);
        holder.setAdapter(this);
        holder.setGenerator(generator);
        holder.setMenu(menu);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private KogaCardAdapter adapter;
        private KogaGenerator generator;
        private Context context;

        private final TextView name;
        private final TextView carbohydrate;
        private final CheckBox meat;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.koga_menu_name);
            carbohydrate = itemView.findViewById(R.id.koga_carbs);
            meat = itemView.findViewById(R.id.koga_meat);
            itemView.findViewById(R.id.koga_button_reroll_menu).setOnClickListener(button -> {
                generator.reRollMenu(getAdapterPosition());
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
            });
        }

        public void setMenu(Menu menu) {
            name.setText(menu.getName());
            carbohydrate.setText(menu.getCarbohydrate().getString(context));
            meat.setChecked(!menu.isVeggie());
        }

        public void setAdapter(KogaCardAdapter adapter) {
            this.adapter = adapter;
        }

        public void setGenerator(KogaGenerator generator) {
            this.generator = generator;
        }

        public void setContext(Context context) {
            this.context = context;
        }

    }

}
