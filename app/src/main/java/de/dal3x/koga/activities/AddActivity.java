package de.dal3x.koga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.dal3x.koga.R;
import de.dal3x.koga.menu.constants.Carbohydrate;
import de.dal3x.koga.menu.constants.HealthScore;
import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.Ingredients;
import de.dal3x.koga.menu.room.MenuRepository;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton back = findViewById(R.id.add_header_button);
        back.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        Button addMenu = findViewById(R.id.add_button_save);

        MenuRepository repository = new MenuRepository(getApplicationContext());
        // Spinner list containing all Carbohydrate values in string format of current language
        List<String> carbSpinnerList = Arrays.stream(Carbohydrate.values()).map(value -> value.getString(getApplicationContext())).collect(Collectors.toList());
        ArrayAdapter<String> carbSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, carbSpinnerList);
        carbSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner carbSpinner = findViewById(R.id.add_input_carbohydrate);
        carbSpinner.setAdapter(carbSpinnerAdapter);

        addMenu.setOnClickListener(view -> {
            TextInputEditText menuName = findViewById(R.id.add_input_name);
            RatingBar likeRating = findViewById(R.id.add_input_likeness);
            SwitchCompat veggie = findViewById(R.id.add_input_veggie);
            RatingBar healthScore = findViewById(R.id.add_input_health);
            Menu menu = new Menu(menuName.getEditableText().toString(), Math.round(likeRating.getRating()), veggie.isChecked(),
                    HealthScore.fromRating(Math.round(healthScore.getRating())),
                    Carbohydrate.fromString(getApplicationContext(), carbSpinner.getSelectedItem().toString()),
                    "", new Ingredients(new HashMap<>()));
            repository.addMenu(menu);
        });



    }
}