package com.company;

public class Field {
    private char t;
    private int row;
    private int col;
    private Ship ship;

    public Field(int row, int col) {
        t = '~';
        this.row = row;
        this.col = col;
    }

    public void placeShip(Ship ship)
    {
        this.ship = ship;
        if(ship.getType().equals("C")) {
            t = 'c';
        }
        else {
            t = ship.getType().charAt(0);
        }
    }

    public void hit() {
        t = 'x';
    }

    public void miss() {
        t = 'm';
    }

    public char getT() {
        return t;
    }

    public void setT(char t) {
        this.t = t;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public String toString() {
        return String.valueOf(t);
    }
}
