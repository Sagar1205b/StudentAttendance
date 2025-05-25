package com.sahana.StudentAttendance.Model.Recgonition;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
    private Age age;
    private Gender gender;
    private Mask mask;
    private List<Double> embedding;
    private Box box;
    private List<List<Integer>> landmarks;
    private List<Subject> subjects;

    @JsonProperty("execution_time")
    private ExecutionTime executionTime;

    // Getters and setters...

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public List<List<Integer>> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<List<Integer>> landmarks) {
        this.landmarks = landmarks;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public ExecutionTime getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(ExecutionTime executionTime) {
        this.executionTime = executionTime;
    }
}
