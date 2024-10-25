package com.nikhilt.ridematch.entities;

public class Location {
    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getDistance(Location that) {
        int xDel = that.x - this.x;
        int yDel = that.y - this.y;
        return (float) Math.sqrt(xDel * xDel + yDel * yDel);
    }
}
