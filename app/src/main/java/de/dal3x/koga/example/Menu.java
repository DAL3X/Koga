package de.dal3x.koga.example;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "word_table")
public class Menu {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    public String name;

    public Menu(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return this.name;
    }
}
