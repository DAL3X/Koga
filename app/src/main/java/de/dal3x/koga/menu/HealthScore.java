package de.dal3x.koga.menu;

public enum HealthScore {
    HEALTHY(0),
    NORMAL(1),
    UNHEALTHY(2);

    public final int number;

    private HealthScore(int number) {
        this.number = number;
    }
}
