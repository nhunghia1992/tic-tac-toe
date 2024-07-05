package api;
import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Game {
    private Board board;
    private boolean isRunning;
    private int sideLength;
    private int winCond;
    private String winner;
    private ArrayList<String> winDirection = new ArrayList<String>();

    /**
     * creates a new Tic Tac Toe game
     * @param sideLength
     * @param winCond
     */
    public Game(int sideLength, int winCond){
        iniGame(sideLength, winCond);
    }

    /**
     * checks if the game inputs are valid and request new numbers if pervious input is invalid then sets up the game
     * @param sideLength
     * @param winCond
     */
    private void iniGame(int sideLength, int winCond){
        winner = "";
        Scanner scan = new Scanner(System.in);
        while(sideLength <= 1 || sideLength >=26){
            System.out.print("Invalid board size, please enter a number between 2 and 26 for the board length: ");
            sideLength = scan.nextInt();
            System.out.println();
        }
        this.sideLength = sideLength;
        while(winCond <= 1 || winCond > sideLength){
            System.out.print("Invalid win condition, please enter a number larger than 1 and smaller than of equal to the side length: ");
            winCond = scan.nextInt();
            System.out.println();
        }
        this.winCond = winCond;
        board = new Board(this.sideLength, this.sideLength);
        isRunning = true;
    }

    /**
     * checks vertical lines for a winner
     * @param i
     * @param j
     * @param prevI
     * @return
     */
    private int checkVertical(int i, int j, int prevI){
        if (i < 0 || i > sideLength-1){
            return 0;
        }
        if (!board.atLoc(i,j).equals("X") && !board.atLoc(i,j).equals("O")){
            return 0;
        }
        if (prevI != i){
            if (board.atLoc(i, j).equals(board.atLoc(prevI, j))){
                if (i < prevI){
                    return checkVertical(i - 1, j, prevI) + 1;
                }
                else{
                    return checkVertical(i + 1, j, prevI) + 1;
                }
            }
            else{
                return 0;
            }
        }   
        return checkVertical(i + 1, j, i) + checkVertical( i - 1, j, i) + 1;
    }

    private int checkHorizontal(int i, int j, int prevJ){
        if (j < 0 || j > sideLength-1){
            return 0;
        }
        else if (!board.atLoc(i,j).equals("X") && !board.atLoc(i,j).equals("O")){
            return 0;
        }
        if (prevJ != j){
            if (board.atLoc(i, j).equals(board.atLoc(i, prevJ))){
                if (j < prevJ){
                    return checkHorizontal(i, j - 1, prevJ) + 1;
                }
                else{
                    return checkHorizontal(i, j + 1, prevJ) + 1;
                }
            }
            else{
                return 0;
            }
        }   
        return checkHorizontal(i, j+1, j) + checkHorizontal(i, j-1, j) + 1;
    }

    private int checkDiagDown(int i, int j, int prevI, int prevJ){
        if (i < 0 || i > sideLength-1 || j < 0 || j > sideLength-1){
            return 0;
        }
        else if (!board.atLoc(i,j).equals("X") && !board.atLoc(i,j).equals("O")){
            return 0;
        }
        if (prevI != i && prevJ != j){
            if (board.atLoc(i, j).equals(board.atLoc(prevI, prevJ))){
                if (i < prevI && j < prevJ){
                    return checkDiagDown(i - 1, j - 1, prevI, prevJ) + 1;
                }
                else{
                    return checkDiagDown(i + 1, j + 1, prevI, prevJ) + 1;
                }
            }
            else{
                return 0;
            }
        }   
        return checkDiagDown(i + 1, j + 1, i, j) + checkDiagDown( i - 1, j - 1, i, j) + 1;
    }

    private int checkDiagUp(int i, int j, int prevI, int prevJ){
        if (i < 0 || i > sideLength-1 || j < 0 || j > sideLength-1){
            return 0;
        }
        else if (!board.atLoc(i,j).equals("X") && !board.atLoc(i,j).equals("O")){
            return 0;
        }
        if (prevI != i && prevJ != j){
            if (board.atLoc(i, j).equals(board.atLoc(prevI, prevJ))){
                if (i < prevI && j > prevJ){
                    return checkDiagUp(i - 1, j + 1, prevI, prevJ) + 1;
                }
                else if (i > prevI && j < prevJ){
                    return checkDiagUp(i + 1, j - 1, prevI, prevJ) + 1;
                }
            }
            else{
                return 0;
            }
        }   
        return checkDiagUp(i - 1, j + 1, i, j) + checkDiagUp( i + 1, j - 1, i, j) + 1;
    }

    /**
     * called after player move
     * @return the winner if there is one
     */
    private void checkWinner(int i, int j){
        if (checkVertical(i, j, i) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
            winDirection.add("vertical");
        }
        if (checkHorizontal(i, j, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
            winDirection.add("horizontal");
        }
        if (checkDiagDown(i, j, i, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
            winDirection.add("diagDown");
        }
        if (checkDiagUp(i, j, i, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
            winDirection.add("diagUp");
        }
    }

    /**
     * checks if game is still running
     * @return true if game havent ended yet
     */
    public boolean gameRunning(){
        return isRunning;
    }

    /**
     * @return true if game ened on a tie
     */
    public boolean checkTie(){
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength; j++){
                if (!board.atLoc(i, j).equals("X") && !board.atLoc(i, j).equals("O")){
                    return false;
                }
            }
        }
        isRunning = false;
        return true;
    }

    public void reset(){
        board = new Board(sideLength, sideLength);
        isRunning = true;
        winner = "";
    }

    public String getWinner(){
        return winner;
    }

    /**
     * helper method for player moves
     * @param player
     * @param loc
     */
    public void playerMove(String player, String loc){
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength; j++){
                if (board.atLoc(i,j).equals(loc)){
                    board.setLoc(player, i, j);
                    checkWinner(i , j);
                }
            }
        }
    }

    public ArrayList<String> getWinDirect(){
        return winDirection;
    }

    public int getWinCond(){
        return winCond;
    }
    
}
