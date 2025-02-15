package de.dal3x.koga.menu.constants;


import android.content.Context;

import de.dal3x.koga.R;

public enum Carbohydrate {
    NONE,
    PASTA,
    RICE,
    POTATO,
    BREAD,
    OTHER;

    public static Carbohydrate fromString(Context context, String name) {
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_pasta))) {
            return PASTA;
        }
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_rice))) {
            return RICE;
        }
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_potato))) {
            return POTATO;
        }
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_bread))) {
            return BREAD;
        }
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_other))) {
            return OTHER;
        }
        if (name.equalsIgnoreCase(context.getResources().getString(R.string.carbohydrate_nothing))) {
            return NONE;
        }
        return NONE;
    }

    public String getString(Context context) {
        switch(this) {
            case PASTA:
                return context.getResources().getString(R.string.carbohydrate_pasta);
            case RICE:
                return context.getResources().getString(R.string.carbohydrate_rice);
            case POTATO:
                return context.getResources().getString(R.string.carbohydrate_potato);
            case BREAD:
                return context.getResources().getString(R.string.carbohydrate_bread);
            case OTHER:
                return context.getResources().getString(R.string.carbohydrate_other);
        }
        return context.getResources().getString(R.string.carbohydrate_nothing);
    }


}