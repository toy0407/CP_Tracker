package com.toy0407.cptracker;

public class PastCodeforcesContestsClass {
    private int id;
    private String name;
    private int rank,oldRating,newRating;

    public PastCodeforcesContestsClass(int id,String name, int rank, int oldRating, int newRating) {
        this.id=id;
        this.name = name;
        this.rank = rank;
        this.oldRating = oldRating;
        this.newRating = newRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getOldRating() {
        return oldRating;
    }

    public void setOldRating(int oldRating) {
        this.oldRating = oldRating;
    }

    public int getNewRating() {
        return newRating;
    }

    public void setNewRating(int newRating) {
        this.newRating = newRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
