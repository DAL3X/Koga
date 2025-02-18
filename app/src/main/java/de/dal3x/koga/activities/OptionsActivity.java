package de.dal3x.koga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.dal3x.koga.R;
import de.dal3x.koga.options.Options;
import de.dal3x.koga.options.OptionsRepository;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.options), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton back = findViewById(R.id.options_header_button);
        back.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        OptionsRepository repository = new OptionsRepository(getApplicationContext());
        Options currentOptions = repository.getOptions();

        EditText daysInput = findViewById(R.id.options_input_days);
        daysInput.setText(String.valueOf(currentOptions.getNumberDays()));
        EditText meatInput = findViewById(R.id.options_input_meat);
        meatInput.setText(String.valueOf(currentOptions.getNumberMeat()));
        EditText carbInput = findViewById(R.id.options_input_carbs);
        carbInput.setText(String.valueOf(currentOptions.getMaxCarbDuplicates()));
        EditText duplicateInput = findViewById(R.id.options_input_duplicate);
        duplicateInput.setText(String.valueOf(currentOptions.getMaxDuplicate()));
        EditText healthInput = findViewById(R.id.options_input_health);
        healthInput.setText(String.valueOf(currentOptions.getMinHealthScore()));

        Button save = findViewById(R.id.options_button_save);
        save.setOnClickListener(view -> {
            int numDays = Integer.parseInt(daysInput.getText().toString());
            int meat = Integer.parseInt(meatInput.getText().toString());
            int carbs = Integer.parseInt(carbInput.getText().toString());
            int duplicates = Integer.parseInt(duplicateInput.getText().toString());
            double health = Double.parseDouble(healthInput.getText().toString());
            Options newOptions = new Options(numDays, meat, duplicates, health, carbs);
            repository.storeOptions(newOptions);
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }
}