package de.dal3x.koga;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

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
        ImageButton back = findViewById(R.id.imageButton_home);
        back.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        Button addMenu = findViewById(R.id.button_save);

        MenuRepository repository = new MenuRepository(getApplicationContext());

        addMenu.setOnClickListener(view -> {
            TextInputEditText menuName = findViewById(R.id.menu_name);
            RatingBar likeRating = findViewById(R.id.menu_likeness);
            SwitchCompat veggie = findViewById(R.id.menu_isVeggie);
            RatingBar healthScore = findViewById(R.id.menu_healthScore);
            Menu m = new Menu(menuName.getEditableText().toString(), likeRating.getNumStars(), veggie.isActivated(),
                    HealthScore.fromRating(healthScore.getNumStars() - 1), Carbohydrate.PASTA, "", new Ingredients(new HashMap<>()));
            repository.addMenu(m);
        });



    }
}