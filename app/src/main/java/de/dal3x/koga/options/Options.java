package de.dal3x.koga.options;

import androidx.room.Entity;

//A data object encapsulating the selected application options
@Entity
public class Options {

    private int numberDays;
    private int numberMeat;
    private int maxDuplicate;
    private double maxHealthScore;
    private int maxCarbDuplicates;

    // Default value constructor
    public Options() {
        setNumberDays(7);
        setNumberMeat(3);
        setMaxDuplicate(1);
        setMaxHealthScore(2.2);
        setMaxCarbDuplicates(3);
    }
    public Options (int numberDays, int numberMeat, int maxDuplicate, double maxHealthScore, int maxCarbDuplicates) {
        setNumberDays(numberDays);
        setNumberMeat(numberMeat);
        setMaxDuplicate(maxDuplicate);
        setMaxHealthScore(maxHealthScore);
        setMaxCarbDuplicates(maxCarbDuplicates);
    }

    public int getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public int getNumberMeat() {
        return numberMeat;
    }

    public void setNumberMeat(int numberMeat) {
        this.numberMeat = numberMeat;
    }

    public int getMaxDuplicate() {
        return maxDuplicate;
    }

    public void setMaxDuplicate(int maxDuplicate) {
        this.maxDuplicate = maxDuplicate;
    }

    public double getMaxHealthScore() {
        return maxHealthScore;
    }

    public void setMaxHealthScore(double maxHealthScore) {
        this.maxHealthScore = maxHealthScore;
    }

    public int getMaxCarbDuplicates() {
        return maxCarbDuplicates;
    }

    public void setMaxCarbDuplicates(int maxCarbDuplicates) {
        this.maxCarbDuplicates = maxCarbDuplicates;
    }
}
