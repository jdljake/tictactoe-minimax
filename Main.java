package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        runManager();
    }

    public static void runManager(){

        graphicDisplay("Welcome to...", "","The Best TicTacToe!", "(by jakooob)", ":)");
        chooseMode();

    }

    public static String formatGraphicText(String text){
        int internalLength = 24;
        double margins = (internalLength - text.length()) / 2.0;
        int margin1 = (int)margins, margin2;
        if(margins % 1 == 0){margin2 = (int)margins;}else{margin2 = (int)margins + 1;}
        return "=" +
                " ".repeat(Math.max(0, margin1)) +
                text +
                " ".repeat(Math.max(0, margin2)) +
                "=";
    }

    public static void graphicDisplay(String title, String subTitle0, String subTitle1, String subTitle2, String subTitle3){
        String t = formatGraphicText(title);
        String s0 = formatGraphicText(subTitle0);
        String s1 = formatGraphicText(subTitle1);
        String s2 = formatGraphicText(subTitle2);
        String s3 = formatGraphicText(subTitle3);

        System.out.println("==========================");
        System.out.println(t);
        System.out.println(s0);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println("==========================");
        System.out.println();
    }


    public static void chooseMode() {
        System.out.println("Press 0 for PVP mode or 1 for AI mode:");
        int mode = getIntegerInput();
        if (mode == 0) {
            runPVP();
        } else if (mode == 1) {
            chooseDifficulty();
        } else if (mode == 3) {
            System.out.println("This is a secret debugging mode. If you don't know what it is, just ignore it");
//            testMode();
        } else {
            System.out.println("Please enter integer 1 or 0");
            chooseMode();
        }
    }

    public static void chooseDifficulty(){
        System.out.println("Enter 0 for Easy Mode and 1 for Hard Mode");
        int difficulty = getIntegerInput();
        if(difficulty == 0){
            runAI(false);
        } else if(difficulty == 1){
            runAI(true);
        } else {
            chooseDifficulty();
        }
    }

    public static int getIntegerInput(){
        int val;
        while(true) {
            try {
                val = sc.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Please enter an integer");
                sc.next();
            }
        }
        return val;
    }

    public static void runPVP(){
        Board b = new Board();
        Scanner sc = new Scanner(System.in);
        Player p1 = new Player('X');
        Player p2 = new Player('O');
        int r,c;
        b.setActivePlayer(p1);

        graphicDisplay("Welcome to...", "", "PVP MODE!", "where 2 players", "can play together");

        while(!b.isWinner() && !b.isTied()){

            //is it X's or O's turn?
            if(b.activePlayer == p1) {
                System.out.println("X's Turn");
                b.setActivePlayer(p1);

            } else {
                System.out.println("O's Turn");
                b.setActivePlayer(p2);
            }

            //take user input for rows and columns
            System.out.print("Enter row: ");
            r = getIntegerInput();

            System.out.print("Enter column: ");
            c = getIntegerInput();

            if(r < 3 && c < 3){
                //move piece
                if(b.get(r, c) == 'X' || b.get(r, c) == 'O'){
                    System.out.println(b.get(r, c) + " is already there, please choose an empty location");
                } else {
                    b.move(b.activePlayer.marker,r,c);
                    b.print();
                    if(b.activePlayer == p1){b.setActivePlayer(p2);}else{b.setActivePlayer(p1);}
                }
            } else {
                System.out.println("please enter value between 0 and 2");
            }
        }
        if(b.isWinner()){
            System.out.println("GAME OVER!");
            System.out.println("POGGIES, " + b.getWinner() + " is the Winner!");
        }
        if(b.isTied()){
            System.out.println("GAME OVER!");
            System.out.println("GGs, the game was a tie!");
        }

        checkPlayAgain();
    }

    public static void runAI(boolean hardMode){
        int r,c;
        Player human = new Player('X');
        AI daisy = new AI('O', hardMode);
        Board b = new Board(human, daisy);
        Scanner sc = new Scanner(System.in);
        b.setActivePlayer(human);
        String subTitle0="";
        String subTitle1="";
        String subTitle2="";
        String subTitle3="";

        if(hardMode){
            subTitle0 = "YOUR DOOM!";
            subTitle1 = "";
            subTitle2 = "AI MODE!";
            subTitle3 = "HARD MODE ACTIVATED!";
        } else {
            subTitle0 = "";
            subTitle2 = "AI MODE!";
            subTitle1 = "Difficulty: Easy";
            subTitle3 = "try hard mode too!";
        }
        graphicDisplay("Welcome to...", subTitle0, subTitle1, subTitle2, subTitle3);

        while(!b.isWinner() && !b.isTied()){
            Player activePlayer = b.activePlayer;
            //is it X's or O's turn?
            if(activePlayer == human) {
                System.out.println("X's Turn [Player]");
                System.out.print("Enter row: ");
                r = getIntegerInput();
                System.out.print("Enter column: ");
                c = getIntegerInput();
                if(r < 3 && c < 3){
                    //move piece
                    if(b.get(r, c) == human.marker || b.get(r, c) == daisy.marker){
                        System.out.println(b.get(r, c) + " is already there, please choose an   empty location");
                    } else {
                        b.move(human.marker, r,c);
                        b.print();
                        b.setActivePlayer(daisy);
                    }
                } else {
                    System.out.println("please enter value between 0 and 2");
                }
            } else {
                System.out.println("O's Turn [AI]");
                daisy.computerTurn(b);
                b.setActivePlayer(human);
            }
        }

        if(b.isWinner()){
            System.out.println("GAME OVER!");
            System.out.println("POGGIES, " + b.getWinner() + " is the Winner!");
        }
        if(b.isTied()){
            System.out.println("GAME OVER!");
            System.out.println("GGs, the game was a tie!");
        }

        checkPlayAgain();
    }

    public static void checkPlayAgain(){
        System.out.println("Would you like to play again? [y/n]");
        char input = sc.next().charAt(0);
        if(input == 'y'){
            runManager();
        } else if(input == 'n'){
            System.out.println("Thanks for playing!");
            System.exit(0);
        } else {
            System.out.println("Please enter either 'y', 'n'");
            checkPlayAgain();
        }
    }

//    public static void testMode(){
//        char[][] setup = {{' ', ' ','X'},{' ', 'X',' '},{'X', ' ',' '}};
//        Board b = new Board(setup);
//        if(b.isWinner()){
//            b.print();
//            System.out.println("isWinner");
//        } else {
//            b.print();
//            System.out.println("LOSER");
//        }

//        checkPlayAgain();
//    }
}
