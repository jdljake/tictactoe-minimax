package com.company;

import java.util.List;
import java.util.ArrayList;

public class Board {
    public char[][] board = new char[3][3];
    private String winner;
    private Player winningPlayer;
    private boolean gameStarted = false;
    Player humanPlayer;
    Player computerPlayer;
    Player activePlayer;
    public Point computerMove;

    public Board(){
        for(int r = 0; r<3;r++){
            for(int c= 0; c<3;c++){
                board[r][c]=' ';
            }
        }
    }

    public Board(Player p1, Player p2){
        humanPlayer = p1;
        computerPlayer = p2;
        for(int r = 0; r<3;r++){
            for(int c= 0; c<3;c++){
                board[r][c]=' ';
            }
        }
    }

    public Board(char[][] setup){
        for(int r = 0; r<3;r++){
            for(int c= 0; c<3;c++){
                board[r][c] = setup[r][c];
            }
        }
    }

    public boolean isValidMove(int r, int c){
        if(board[r][c] == ' '){
            return true;
        }else{
            return false;
        }
    }

    public void setActivePlayer(Player player){
        this.activePlayer = player;
    }

    public void move(char p, int r, int c){
        board[r][c] = p;
        gameStarted = true;
    }

    public void move(Point point, Player player){
        board[point.x][point.y] = player.marker;

    }

    public char[][] get(){
        return board;
    }

    public char get(int r, int c){
        return board[r][c];
    }

    public List<Point> getAvaliableCells(){
        List<Point> avaliableCells = new ArrayList<>();

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(board[r][c] == ' '){
                    avaliableCells.add(new Point(r, c));
                }
            }
        }
        return avaliableCells;
    }

    public void print(){
        for(int r = 0; r<3;r++){
            for(int c= 0; c<3;c++){
                System.out.print("[" + board[r][c]+ "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String toString(){
        String output = "";

        for(int r=0; r<board.length; r++){
            for(int c=0; c<board.length; c++){
                output += "[" + board[r][c] + "]";
            }
            output += "\n";
        }
        return output;
    }

    public boolean isWinner(){

        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != ' '){
            winner = Character.toString(board[0][0]);
            return true;
        } else if(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != ' '){
            winner = Character.toString(board[0][2]);
            return true;
        }

        for(int i = 0; i < 3; i++){
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != ' '){
                winner = Character.toString(board[i][0]);
                return true;
            }
            if(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != ' ') {
                winner = Character.toString(board[0][i]);
                return true;
            }
        }
        return false;
    }

    public boolean isTied(){
        //add your code here
        if(gameStarted){
            boolean tie = true;
            for(int r=0; r<3; r++){
                for(int c=0; c<3; c++){
                    if(board[r][c] == ' '){
                        tie = false;
                    }
                }
            }
            if(tie){
                winner = "tie";
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String getWinner(){
        return winner;
    }

//    public ArrayList<Integer> getAvailableSpaces(){
//        ArrayList<Integer> openSpaces = new ArrayList<Integer>();
//        for(int r=0; r<3; r++){
//            for(int c=0; c<3; c++){
//                if(board[r][c] == ' '){
//                    openSpaces.add(r+c);
//                }
//            }
//        }
//        return openSpaces;
//    }

//    public int minimax(int depth, Player turn){
//        if(isWinner()){
//            if(getWinner().equals("X")) {
////                print();
//                return -1;
//            }
//            if(getWinner().equals("O")) {
////                print();
//                return 1;
//            }
//        }
//
//        List<Point> avaliableCells = getAvaliableCells();
//
//        if(avaliableCells.isEmpty()){
//            return 0;
//        }
//
//        int min = Integer.MAX_VALUE;
//        int max = Integer.MIN_VALUE;
//
//        for(int i = 0; i < avaliableCells.size(); i++){
//            Point point = avaliableCells.get(i);
//
//            if(turn == computerPlayer){
//                move(point, computerPlayer);
//                int currentScore = minimax(depth+1, humanPlayer);
//                max = Math.max(currentScore, max);
//
//                if(depth == 0){
//                    System.out.println("Computer Score for Position " + point + " = " + currentScore);
//                }
//                if(currentScore >= 0){
//                    if(depth == 0){
//                        computerMove = point;
//                    }
//                }
//                if(currentScore == 1){
//                    board[point.x][point.y] = ' ';
//                    break;
//                }
//
//                if(i == avaliableCells.size() - 1 && max < 0){
//                    if(depth == 0){
//                        computerMove = point;
//                    }
//                }
//
//            } else if (turn == humanPlayer){
//                move(point, humanPlayer);
//                int currentScore = minimax(depth+1, computerPlayer);
//                min = Math.min(currentScore, min);
//
//                if(min == -1){
//                    board[point.x][point.y] = ' ';
//                    break;
//                }
//            }
//            board[point.x][point.y] = ' ';
//        }
//        return turn == computerPlayer ? max : min;
//    }


}
