package de.dal3x.koga;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.MenuRepository;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton back = findViewById(R.id.imageButton_home);
        back.setOnClickListener(view -> startActivity(new Intent(getApplication(), MainActivity.class)));
        TextInputEditText name = findViewById(R.id.menu_name);
        Button add = findViewById(R.id.button_save);


        MenuRepository repository = new MenuRepository(getApplication());
        repository.deleteAll();

        add.setOnClickListener(view -> {
            repository.insert(new Menu(name.getEditableText().toString()));
            repository.getAllMenus().observe(this, words -> {
                if (!words.isEmpty()) {
                    add.setText(String.valueOf(words.size()));
                }
            });
        });
    }
}