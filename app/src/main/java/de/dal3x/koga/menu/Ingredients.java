package de.dal3x.koga.menu;

import android.util.Pair;

import java.util.Map;

import de.dal3x.koga.menu.constants.Unit;

public class Ingredients {
    private Map<String, Pair<Double, Unit>> contents;

    public Ingredients(Map<String, Pair<Double, Unit>> contents) {
        this.contents = contents;
    }

    public Map<String, Pair<Double, Unit>> getContents() {
        return contents;
    }

    public void setContents(Map<String, Pair<Double, Unit>> contents) {
        this.contents = contents;
    }
}
