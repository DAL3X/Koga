package de.dal3x.koga.menu.constants;

public enum HealthScore {
    UNHEALTHY(1),
    NORMAL(2),
    HEALTHY(3);

    public static HealthScore fromRating(int rating) {
        switch(rating) {
            case 1: return UNHEALTHY;
            case 2: return NORMAL;
            case 3: return HEALTHY;
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