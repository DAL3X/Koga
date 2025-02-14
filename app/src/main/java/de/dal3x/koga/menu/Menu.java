package de.dal3x.koga.menu;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import de.dal3x.koga.menu.constants.Carbohydrate;
import de.dal3x.koga.menu.constants.HealthScore;


/** Represents a menu consisting of a name, ingredients, a likeness value, health score,
 * veggie boolean, carbohydrate type.
 * It can be linked together with a second menu to create a 2-day menu. The link gets identified via a menu name.
 */
@Entity
public class Menu {

    @PrimaryKey
    @NonNull
    private String name;
    private int likeness;
    private boolean isVeggie;
    private HealthScore healthScore;
    private Carbohydrate carbohydrate;
    private String link;
    private boolean isLinked;
    private Ingredients ingredients;


    public Menu(@NonNull String name, int likeness, boolean isVeggie, HealthScore healthScore, Carbohydrate carbohydrate, String link, Ingredients ingredients) {
        this.name = name;
        this.likeness = likeness;
        this.isVeggie = isVeggie;
        this.healthScore = healthScore;
        this.carbohydrate = carbohydrate;
        this.link = link;
        isLinked = !link.isBlank(); //sets isLinked to false if link equals empty string
        this.ingredients = ingredients;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getLikeness() {
        return likeness;
    }

    public void setLikeness(int likeness) {
        this.likeness = likeness;
    }

    public HealthScore getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(HealthScore healthScore) {
        this.healthScore = healthScore;
    }

    public boolean isVeggie() {
        return isVeggie;
    }

    public void setVeggie(boolean veggie) {
        isVeggie = veggie;
    }

    public Carbohydrate getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Carbohydrate carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        isLinked = true;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }
}
