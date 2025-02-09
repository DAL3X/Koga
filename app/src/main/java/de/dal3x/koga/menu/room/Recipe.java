package de.dal3x.koga.menu.room;

import java.util.Map;

public class Recipe {

    private Map<String, Double> contents;

    public Recipe(Map<String, Double> contents) {
        setContents(contents);
    }

    public Map<String, Double> getContents() {
        return contents;
    }

    public void setContents(Map<String, Double> contents) {
        this.contents = contents;
    }
}
