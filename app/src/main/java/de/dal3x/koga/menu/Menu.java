package de.dal3x.koga.menu;

import android.util.Pair;

import java.util.List;

/** Represents a menu consisting of a name, ingredients, a likeness value, health score,
 * veggie boolean, carbohydrate type.
 */
public class Menu {

    private String name;
    private List<Pair<String, String>> ingredients;
    private int likeness;
    private HealthScore healthScore;
    private boolean isVeggie;
    private Carbohydrate carbohydrate;

    public Menu(String name, List<Pair<String, String>> ingredients, int likeness, HealthScore healthScore, boolean isVeggie, Carbohydrate carbohydrate) {
        setName(name);
        setIngredients(ingredients);
        setLikeness(likeness);
        setHealthScore(healthScore);
        setVeggie(isVeggie);
        setCarbohydrate(carbohydrate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Pair<String, String>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Pair<String, String>> ingredients) {
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
}
