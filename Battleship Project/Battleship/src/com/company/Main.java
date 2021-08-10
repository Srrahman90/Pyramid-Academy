package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board board1 = new Board();
        Board board2 = new Board();

        Scanner read = new Scanner(System.in);
        System.out.println("Battleship Multiplayer");

        System.out.println("Enter Player 1 name: ");
        String p1 = read.nextLine();

        board1.printBoard();
        System.out.println(p1 + ", Please enter the coordinates for your ships");
        System.out.println("Enter the coordinates for your Carrier");
        placeShips(new Carrier(), board1);
        System.out.println("Enter the coordinates for your Battleship");
        placeShips(new Battleship(), board1);

        System.out.println("Enter Player 2 name: ");
        String p2 = read.nextLine();

        board2.printBoard();
        System.out.println(p2 + ", Please enter the coordinates for your ships");
        System.out.println("Enter the coordinates for your Carrier");
        placeShips(new Carrier(), board2);
        System.out.println("Enter the coordinates for your Battleship");
        placeShips(new Battleship(), board2);

        start(board1, board2, p1, p2);
    }

    public static void placeShips(Ship ship, Board board) {
        while (true)
        {
            Scanner read = new Scanner(System.in);
            Scanner read1 = new Scanner(System.in);
            System.out.println("Row: ");
            int row = read.nextInt();
            System.out.println("Column: ");
            int col = read.nextInt();
            System.out.println("Place Horizontally or Vertically? (H or V): ");
            String pos = read1.nextLine();
            if(board.clearance(ship, row, col, pos)) {
                board.placement(ship, row, col, pos);
                break;
            }
            else {
                System.out.println("Carrier is already located there! Try a new location");
            }
        }
    }

    public static void start(Board board1, Board board2, String p1, String p2) {
        Scanner read = new Scanner(System.in);
        Scanner read1 = new Scanner(System.in);

        Board boardP1 = new Board();
        Board boardP2 = new Board();

        int count1 = 0;
        int count2 = 0;

        while ((board1.shipsSize() > 0) && (board2.shipsSize() > 0)) {

            boardP1.printBoard();
            System.out.println(p1 + " enter the coordinates for an attack: ");
            while (true) {
                System.out.println("Row: ");
                int row = read.nextInt();
                System.out.println("Column: ");
                int col = read.nextInt();
                if((row > 0 && row <=9) && (col > 0 && col <= 9)) {
                    if (boardP1.getBoard(row - 1, col - 1).toString().equals("~")) {
                        board2.hitmiss(row - 1, col - 1, boardP1);
                        count1++;
                        break;
                    }
                }
            }

            boardP2.printBoard();
            System.out.println(p2 + " enter the coordinates for an attack: ");
            while (true) {
                System.out.println("Row: ");
                int row = read.nextInt();
                System.out.println("Column: ");
                int col = read.nextInt();
                if((row > 0 && row <=9) && (col > 0 && col <= 9)) {
                    if (boardP2.getBoard(row - 1, col - 1).toString().equals("~")) {
                        board1.hitmiss(row - 1, col - 1, boardP2);
                        count2++;
                        break;
                    }
                }
            }

            if(count1 == 5) {
                System.out.println(p1 + ", You Win!");
            }
            else if (count2 == 5) {
                System.out.println(p2 + ", You Win!");
            }
        }
    }

}
