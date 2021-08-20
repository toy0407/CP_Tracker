package com.toy0407.cptracker;

public class UpcomingCodeforcesContestsClass {
    private String name;
    private double duration;
    private int startTimeSeconds;

    public UpcomingCodeforcesContestsClass(String name, int duration, int startTimeSeconds) {
        this.name = name;
        this.duration = duration;
        this.startTimeSeconds = startTimeSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(int startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }
}
