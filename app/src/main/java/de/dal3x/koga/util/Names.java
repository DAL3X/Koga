package de.dal3x.koga.util;

// Enum containing all string that are hardcoded and solely used as identifiers.
public enum Names {

    OPTIONSSTORE("options_datastore"),
    MENUSTORE("menu_database"),
    OPTIONS_DAYS("numberDays"),
    OPTIONS_MEAT("numberMeat"),
    OPTIONS_DUPLICATE("maxDuplicate"),
    OPTIONS_HEALTH("maxHealthScore"),
    OPTIONS_CARBS("maxCarbDuplicates");

    public final String string;

    Names(String string) {
        this.string = string;
    }
}
