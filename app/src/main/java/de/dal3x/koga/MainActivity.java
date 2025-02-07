package de.dal3x.koga;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.dal3x.koga.options.Options;

public class MainActivity extends AppCompatActivity {

    private Options options;

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

        loadOptions();

        Button kogaButton = findViewById(R.id.button_koga);
        kogaButton.setOnClickListener(view -> startActivity(KogaActivity.class));
        Button addMenuButton = findViewById(R.id.button_addmenu);
        addMenuButton.setOnClickListener(view -> startActivity(AddActivity.class));
        Button listMenuButton = findViewById(R.id.button_listmenu);
        listMenuButton.setOnClickListener(view -> startActivity(ListActivity.class));
        Button optionButton = findViewById(R.id.button_options);
        optionButton.setOnClickListener(view -> startActivity(OptionsActivity.class));
    }

    // Starts an activity with all options bundled up
    private void startActivity (Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("numberDays", this.options.getNumberDays());
        intent.putExtra("numberMeat", this.options.getNumberMeat());
        intent.putExtra("maxDuplicate", this.options.getMaxDuplicate());
        intent.putExtra("maxCalorieScore", this.options.getMaxCalorieScore());
        intent.putExtra("maxCarbDuplicates", this.options.getMaxCarbDuplicates());
        startActivity(intent);
    }

    private void loadOptions() {
        // TODO load options from device
       this.options = new Options();
    }
}