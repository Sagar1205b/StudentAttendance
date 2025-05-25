package com.sahana.StudentAttendance.Model.Recgonition;

public class Age {
    private double probability;
    private int high;
    private int low;

    // Getters and setters...

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }
}
