package de.dal3x.koga.menu.constants;

public enum HealthScore {
    UNHEALTHY(0),
    NORMAL(1),
    HEALTHY(2);

    public static HealthScore fromRating(int rating) {
        switch(rating) {
            case 0: return UNHEALTHY;
            case 1: return NORMAL;
            case 2: return HEALTHY;
            default: return null; // rating not supported
        }
    }

    private final int rating;

    HealthScore(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
}