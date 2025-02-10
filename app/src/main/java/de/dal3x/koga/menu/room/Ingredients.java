package de.dal3x.koga.menu.room;

import java.util.Map;

public class Ingredients {
    private Map<String, Double> contents;

    public Ingredients(Map<String, Double> contents) {
        setContents(contents);
    }

    public Map<String, Double> getContents() {
        return contents;
    }

    public void setContents(Map<String, Double> contents) {
        this.contents = contents;
    }
}
