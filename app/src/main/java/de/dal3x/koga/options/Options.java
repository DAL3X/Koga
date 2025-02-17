package de.dal3x.koga.options;

// A data object encapsulating the selected application options
public class Options {

    private int numberDays;
    private int numberMeat;
    private int maxDuplicate;
    private double minHealthScore;
    private int maxCarbDuplicates;

    public Options() {}

    public Options (int numberDays, int numberMeat, int maxDuplicate, double minHealthScore, int maxCarbDuplicates) {
        setNumberDays(numberDays);
        setNumberMeat(numberMeat);
        setMaxDuplicate(maxDuplicate);
        setMinHealthScore(minHealthScore);
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

    public double getMinHealthScore() {
        return minHealthScore;
    }

    public void setMinHealthScore(double minHealthScore) {
        this.minHealthScore = minHealthScore;
    }

    public int getMaxCarbDuplicates() {
        return maxCarbDuplicates;
    }

    public void setMaxCarbDuplicates(int maxCarbDuplicates) {
        this.maxCarbDuplicates = maxCarbDuplicates;
    }
}
