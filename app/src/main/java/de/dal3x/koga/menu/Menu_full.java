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
public class Menu_full {

    @PrimaryKey
    @NonNull
    private String name;
    private Recipe ingredients;
    private int likeness;
    private HealthScore healthScore;
    private boolean isVeggie;
    private Carbohydrate carbohydrate;

    private String link;
    private boolean isLinked;


    public Menu_full(String name, Recipe ingredients, int likeness, HealthScore healthScore, boolean isVeggie, Carbohydrate carbohydrate, String link) {
        this.name = ""; //Because name is not allowed to be Null
        setName(name);
        setIngredients(ingredients);
        setLikeness(likeness);
        setHealthScore(healthScore);
        setVeggie(isVeggie);
        setCarbohydrate(carbohydrate);
        setLink(link);
        isLinked = !link.equalsIgnoreCase(""); //sets isLinked to false if link equals empty string
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    public Recipe getIngredients() {
        return ingredients;
    }

    public void setIngredients(Recipe ingredients) {
        this.ingredients = ingredients;
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
}
