package com.example.there4u.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Level {
    private int level;
    private double progress;

    //We only need default constructor as we cannot add new user and give them some other level than 0
    public Level() {
        this.level = 0;
        this.progress = 0;
    }

    //Getters
    public int getLevel() {
        return this.level;
    }

    public double getProgress() {
        return this.progress;
    }

    public void addProgress(double percentage) {
        if(percentage != 0.05 || percentage != 0.1 || percentage != 0.15 || percentage != 0.20)
            throw new IllegalArgumentException(">! Invalid percentage: " + percentage);

        this.progress += percentage;

        while(this.progress > 1) {
            this.level++;
            this.progress -= 1;
        }
    }

    @Override
    public String toString() {
        return "Level: " + this.level + "." + this.progress;
    }
}
