package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AI extends Player{
    public boolean hardMode;

    public AI(char marker, boolean hardMode){
        super(marker);
        this.hardMode = hardMode;
    }

    public void tester(){
        System.out.println(marker);
    }

    public void computerTurn(Board b){
        if(!hardMode){
            guess(b);
        } else {
            smartMove(b);
        }
    }

    public void guess(Board b){
        double row, column;
        int r,c;
        row = (Math.random()*((2-0)+1))+0;
        column = (Math.random()*((2-0)+1))+0;

        ArrayList<Integer> compGuess = new ArrayList<>();
        compGuess.add(0, (int)row);
        compGuess.add(1, (int)column);

        Integer[] theGuess = compGuess.toArray(new Integer[0]);
        r = theGuess[0];
        c = theGuess[1];
        System.out.println("(" + r + ", " + c + ")");
        if (b.isValidMove(r, c)){
            b.move(marker, r, c);
            b.print();
        } else {
            guess(b);
        }
    }

    public void smartMove(Board b){
        minimax(0, this, b);
        System.out.println("Computer chooses position: " + b.computerMove);

        if(b.isValidMove(b.computerMove.x, b.computerMove.y)){
            b.move(b.computerMove, this);
            b.print();
        }
    }

    public int minimax(int depth, Player turn, Board b){
        if(b.isWinner()){
            if(b.getWinner().equals("X")) {
                b.print();
                return -1;
            }
            if(b.getWinner().equals("O")) {
                b.print();
                return 1;
            }
        }

        List<Point> avaliableCells = b.getAvaliableCells();

        if(avaliableCells.isEmpty()){
            return 0;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for(int i = 0; i < avaliableCells.size(); i++){
            Point point = avaliableCells.get(i);

            if(turn == b.computerPlayer){
                b.move(point, b.computerPlayer);
                int currentScore = minimax(depth+1, b.humanPlayer, b);
                max = Math.max(currentScore, max);

                if(depth == 0){
                    System.out.println("Computer Score for Position " + point + " = " + currentScore);
                }
                if(currentScore >= 0){
                    if(depth == 0){
                        b.computerMove = point;
                    }
                }
                if(currentScore == 1){
                    b.board[point.x][point.y] = ' ';
                    break;
                }

                if(i == avaliableCells.size() - 1 && max < 0){
                    if(depth == 0){
                        b.computerMove = point;
                    }
                }

            } else if (turn == b.humanPlayer){
                b.move(point, b.humanPlayer);
                int currentScore = minimax(depth+1, b.computerPlayer, b);
                min = Math.min(currentScore, min);

                if(min == -1){
                    b.board[point.x][point.y] = ' ';
                    break;
                }
            }
            b.board[point.x][point.y] = ' ';
        }
        return turn == b.computerPlayer ? max : min;
    }


}
