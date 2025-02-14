package de.dal3x.koga.menu.constants;

import android.content.Context;

import de.dal3x.koga.R;

public enum Unit {
    GRAM,
    KILOGRAM,
    PACKET,
    LITRE,
    PIECE;

    public String getString(Context context) {
        switch(this) {
            case GRAM:
                return context.getResources().getString(R.string.unit_gram);
            case KILOGRAM:
                return context.getResources().getString(R.string.unit_kilogram);
            case PACKET:
                return context.getResources().getString(R.string.unit_packet);
            case LITRE:
                return context.getResources().getString(R.string.unit_litre);
            case PIECE:
                return context.getResources().getString(R.string.unit_piece);
        }
        return context.getResources().getString(R.string.unit_none);
    }
}
