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

import de.dal3x.koga.example.Word;
import de.dal3x.koga.example.WordRepository;
import de.dal3x.koga.menu.room.MenuRepository;

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


        WordRepository repository = new WordRepository(getApplication());
        //repository.insert(new Word("test"));
        //repository.deleteAll();

        //MenuRepository repo = new MenuRepository(getApplication());
        //repo.deleteAllMenus();
        //repo.addMenu(new Menu("succ"));

        TextInputEditText name = findViewById(R.id.menu_name);
        Button add = findViewById(R.id.button_save);

        add.setOnClickListener(view -> {
            repository.getAllWords().observe(this, words -> {
                if (!words.isEmpty()) {
                    add.setText(words.get(0).getWord());
                }
            });
        });
    }
}