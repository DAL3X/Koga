package de.dal3x.koga.data.room;

import android.util.Pair;

import java.util.Map;

public class Recipe {

    private Map<String, String> contents;

    public Recipe(Map<String, String> contents) {
        setContents(contents);
    }

    public Map<String, String> getContents() {
        return contents;
    }

    public void setContents(Map<String, String> contents) {
        this.contents = contents;
    }
}
