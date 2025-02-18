package de.dal3x.koga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.dal3x.koga.R;
import de.dal3x.koga.list.MenuCardAdapter;
import de.dal3x.koga.menu.room.MenuRepository;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton back = findViewById(R.id.imageButton_home);
        back.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        initialCardView();
    }

    private void initialCardView() {
        MenuRepository repository = new MenuRepository(getApplicationContext());
        repository.getAllMenus().observe(this, menus -> {
            RecyclerView recycler = findViewById(R.id.menu_list);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.setAdapter(new MenuCardAdapter(menus));
        });
    }
}