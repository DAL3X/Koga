package de.dal3x.koga.menu;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.dal3x.koga.data.room.Recipe;

/** Represents a menu consisting of a name, ingredients, a likeness value, health score,
 * veggie boolean, carbohydrate type.
 * It can be linked together with a second menu to create a 2-day menu. The link gets identified via a menu name.
 */
@Entity
public class Menu {

    @PrimaryKey
    @NonNull
    private String name;


    public Menu(String name) {
        this.name = ""; //Because name is not allowed to be Null
        setName(name);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}
