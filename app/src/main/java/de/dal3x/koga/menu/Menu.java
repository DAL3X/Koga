package de.dal3x.koga.menu;

import android.util.Pair;

import java.util.List;

/** Represents a menu consisting of a name, ingredients, a likeness value, health score,
 * veggie boolean, carbohydrate type.
 * It can be linked together with a second menu to create a 2-day menu. The link gets identified via a menu name.
 */
public class Menu {

    private String name;
    private List<Pair<String, String>> ingredients;
    private int likeness;
    private HealthScore healthScore;
    private boolean isVeggie;
    private Carbohydrate carbohydrate;
    private String link;
    private boolean isLinked;

    // Use this constructor for building single menus without links
    public Menu(String name, List<Pair<String, String>> ingredients, int likeness, HealthScore healthScore, boolean isVeggie, Carbohydrate carbohydrate) {
        setName(name);
        setIngredients(ingredients);
        setLikeness(likeness);
        setHealthScore(healthScore);
        setVeggie(isVeggie);
        setCarbohydrate(carbohydrate);
        isLinked = false;
    }

    // Use this constructor for building double menus with links
    public Menu(String name, List<Pair<String, String>> ingredients, int likeness, HealthScore healthScore, boolean isVeggie, Carbohydrate carbohydrate, String link) {
        setName(name);
        setIngredients(ingredients);
        setLikeness(likeness);
        setHealthScore(healthScore);
        setVeggie(isVeggie);
        setCarbohydrate(carbohydrate);
        setLink(link);
        isLinked = true;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        isLinked = true;
    }

    public void destroyLink(String link) {
        this.link = "";
        isLinked = false;
    }

    public boolean isLinked() {
        return isLinked;
    }
}
