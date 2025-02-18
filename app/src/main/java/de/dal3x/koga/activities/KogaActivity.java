package de.dal3x.koga.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.dal3x.koga.R;
import de.dal3x.koga.generator.KogaGenerator;

public class KogaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_koga);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.koga), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        KogaGenerator generator = new KogaGenerator(this);
        generator.getSelection().observe(this, selection -> {
            // TODO update recycleview
        });
    }
}