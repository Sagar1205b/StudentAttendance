package com.sahana.StudentAttendance.Model.Recgonition;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Box {
    @JsonProperty("probability")
    private double probability;

    @JsonProperty("x_max")
    private int xMax;

    @JsonProperty("y_max")
    private int yMax;

    @JsonProperty("x_min")
    private int xMin;

    @JsonProperty("y_min")
    private int yMin;

    // Getters and setters...

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getxMax() {
        return xMax;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public int getxMin() {
        return xMin;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public int getyMin() {
        return yMin;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }
}

