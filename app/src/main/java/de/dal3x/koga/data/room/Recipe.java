package de.dal3x.koga.data.room;

import android.util.Pair;

import java.util.List;

public class Recipe {

    private List<Pair<String, String>> contents;

    public Recipe(List<Pair<String, String>> contents) {
        setContents(contents);
    }

    public List<Pair<String, String>> getContents() {
        return contents;
    }

    public void setContents(List<Pair<String, String>> contents) {
        this.contents = contents;
    }
}
