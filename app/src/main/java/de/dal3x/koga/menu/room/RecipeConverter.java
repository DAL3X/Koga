package de.dal3x.koga.menu.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

class RecipeConverter {

    private static Gson gson;

    @TypeConverter
    public static Recipe recipeFromString(String string) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(string, Recipe.class);
    }

    @TypeConverter
    public static String recipeToString(Recipe recipe) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(recipe);
    }

}
