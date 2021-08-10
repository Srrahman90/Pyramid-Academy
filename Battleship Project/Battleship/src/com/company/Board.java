package com.company;

import java.util.ArrayList;

public class Board {
    Field[][] fields;
    private ArrayList<Ship> ships;

    public Board() {
        ships = new ArrayList<>();
        fields = new Field[9][9];
        for (int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                fields[i][j] = new Field(i,j);
            }
        }
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9");
        int r = 1;
        for (Field [] f1: fields) {
            System.out.print(r + " ");
            for (Field f2: f1) {
                System.out.print(f2.toString() + " ");
            }
            System.out.println();
            r++;
        }
    }

    public int shipsSize() {
        return ships.size();
    }

    public Field getBoard(int row, int col)
    {
        return fields[row][col];
    }

    public boolean clearance(Ship ship, int row, int col, String pos) {
        if(pos.equals("V")) {
            if(row + ship.getLength() <= 9) {
                String before = fields[row][col].toString();
                if(before.equals("~")) {
                    int temp = 0;
                    while (temp < ship.getLength()) {
                        if(!(fields[row  + temp][col].toString().equals("~"))) {
                            return false;
                        }
                        temp++;
                    }
                    return true;
                }
            }
            return false;
        }
        if (pos.equals("H")) {
            if (col + ship.getLength() <=9) {
                String before = fields[row][col].toString();
                if(before.equals("~")) {
                    int temp = 0;
                    while (temp < ship.getLength()) {
                        if (!(fields[row][col  + temp].toString().equals("~"))) {
                            return false;
                        }
                        temp++;
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void placement(Ship ship, int row, int col, String pos) {
        fields[row][col].placeShip(ship);
        ships.add(ship);

        if (pos.equals("V")) {
            int temp = 1;
            while (temp <= ship.getLength() - 1) {
                fields[row + temp][col].placeShip(ship);
                temp++;
            }
        }
        if(pos.equals("H")) {
            int temp = 1;
            while (temp <= ship.getLength() - 1) {
                fields[row][col + temp].placeShip(ship);
                temp++;
            }
        }
    }

    public boolean hitmiss(int row, int col, Board board) {
        if(!fields[row][col].toString().equals("~") && !fields.toString().equals("x")) {
            fields[row][col].hit();
            board.getBoard(row, col).hit();
            System.out.println("Nice Kill!");
            return true;
        }
        else {
            fields[row][col].miss();
            board.getBoard(row, col).miss();
            System.out.println("You Missed!");
            return false;
        }
    }
}
