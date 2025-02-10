package de.dal3x.koga.menu.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

class IngredientsConverter {

    private static Gson gson;

    @TypeConverter
    public static Ingredients ingredientsFromString(String string) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(string, Ingredients.class);
    }

    @TypeConverter
    public static String ingredientsToString(Ingredients ingredients) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(ingredients);
    }

}
