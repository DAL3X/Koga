package de.dal3x.koga.menu.constants;

public enum HealthScore {
    UNHEALTHY(0),
    NORMAL(1),
    HEALTHY(2);

    public final int number;

    HealthScore(int number) {
        this.number = number;
    }
}