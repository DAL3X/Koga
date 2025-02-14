package de.dal3x.koga.menu.constants;

public enum HealthScore {
    HEALTHY(0),
    NORMAL(1),
    UNHEALTHY(2);

    public final int number;

    HealthScore(int number) {
        this.number = number;
    }
}