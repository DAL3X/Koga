package de.dal3x.koga.menu.constants;

import android.content.Context;

import de.dal3x.koga.R;

public enum Unit {
    GRAM,
    PACKET,
    MILLILITRE,
    PIECE;

    public String getString(Context context) {
        switch(this) {
            case GRAM:
                return context.getResources().getString(R.string.unit_gram);
            case PACKET:
                return context.getResources().getString(R.string.unit_packet);
            case MILLILITRE:
                return context.getResources().getString(R.string.unit_millilitre);
            case PIECE:
                return context.getResources().getString(R.string.unit_piece);
        }
        return context.getResources().getString(R.string.unit_none);
    }
}
