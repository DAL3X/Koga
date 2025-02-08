package de.dal3x.koga.util;

// Enum containing all string that are hardcoded and solely used as identifiers.
public enum Names {

    DATASTORE("koga_data");

    public final String string;

    private Names(String string) {
        this.string = string;
    }
}
