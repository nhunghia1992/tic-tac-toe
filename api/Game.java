package api;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean isRunning;
    private int sideLength;
    private int winCond;
    private String winner;

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
     * prints the game board
     */
    public void printBoard(){
        board.printBoard();
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
        }
        if (checkHorizontal(i, j, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
        }
        if (checkDiagDown(i, j, i, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
        }
        if (checkDiagUp(i, j, i, j) >= winCond){
            isRunning = false;
            winner = board.atLoc(i, j);
        }
    }
    /**
     * prints the result of the game
     */
    public void getResults(){
        if(isRunning == false){
            if (checkTie() == true){
                System.out.println("Tie!");
            }
            else if (winner.equals("X")){
                System.out.println("X wins");
            }
            else if (winner.equals("O")){
                System.out.println("O wins");
            }
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

    /**
     * player X's move
     */
    public void xMove(String loc){
        if (isRunning){
            while (checkValidMove(loc) == false){
                System.out.println("Invalid move");
                loc = getValidMove();
            }
            playerMove("X", loc);
        }
    }

    /**
     * player O's move
     * @param loc
     */
    public void oMove(String loc){
        if (isRunning){
            while (checkValidMove(loc) == false){
                System.out.println("Invalid move");
                loc = getValidMove();
            }
            playerMove("O", loc);
        }
    }

    /**
     * checks if the player move is valid
     * @param loc
     * @return
     */
    private boolean checkValidMove(String loc){
        if (loc.equals("X") || loc.equals("O")){
            return false;
        }
        for (String[] a : board.getBoard()){
            for (String b : a){
                if (loc.equals(b)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * helper method to prompt the user for a valid move
     */
    private static String getValidMove(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter a valid move: ");
        String newLoc = scan.nextLine();
        System.out.println();
        return newLoc;
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

}
