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
import de.dal3x.koga.generator.KogaGenerator;
import de.dal3x.koga.list.KogaCardAdapter;

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

        ImageButton back = findViewById(R.id.koga_header_button);
        back.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        KogaGenerator generator = new KogaGenerator(this);
        generator.getSelection().observe(this, selection -> {
            if (!selection.isEmpty()) {
                RecyclerView recycler = findViewById(R.id.koga_recycle);
                recycler.setLayoutManager(new LinearLayoutManager(this));
                recycler.setAdapter(new KogaCardAdapter(selection, generator, this));
            }
        });


    }
}