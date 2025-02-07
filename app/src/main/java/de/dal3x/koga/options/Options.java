package de.dal3x.koga.options;

//A data object encapsulating the selected application options
public class Options {

    private int numberDays;
    private int numberMeat;
    private int maxDuplicate;
    private double maxCalorieScore;
    private int maxCarbDuplicates;

    // Constructor with default values
    public Options () {
        this.numberDays = 7;
        this.numberMeat = 3;
        this.maxDuplicate = 1;
        this.maxCalorieScore = 2.2;
        this.maxCarbDuplicates = 3;
    }

    // Constructor for set options
    public Options (int numberDays, int numberMeat, int maxDuplicate, double maxCalorieScore, int maxCarbDuplicates) {
        this.numberDays = numberDays;
        this.numberMeat = numberMeat;
        this.maxDuplicate = maxDuplicate;
        this.maxCalorieScore = maxCalorieScore;
        this.maxCarbDuplicates = maxCarbDuplicates;
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

    public double getMaxCalorieScore() {
        return maxCalorieScore;
    }

    public void setMaxCalorieScore(double maxCalorieScore) {
        this.maxCalorieScore = maxCalorieScore;
    }

    public int getMaxCarbDuplicates() {
        return maxCarbDuplicates;
    }

    public void setMaxCarbDuplicates(int maxCarbDuplicates) {
        this.maxCarbDuplicates = maxCarbDuplicates;
    }
}
