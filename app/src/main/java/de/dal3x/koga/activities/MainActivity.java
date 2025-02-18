package de.dal3x.koga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.dal3x.koga.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Implement all button functionality
        Button kogaButton = findViewById(R.id.main_button_koga);
        kogaButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), KogaActivity.class)));
        Button addMenuButton = findViewById(R.id.main_button_addmenu);
        addMenuButton.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), AddActivity.class)));
        Button listMenuButton = findViewById(R.id.main_button_listmenu);
        listMenuButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ListActivity.class)));
        Button optionButton = findViewById(R.id.main_button_options);
        optionButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OptionsActivity.class)));

    }
}