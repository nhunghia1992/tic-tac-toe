package api;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean isRunning;
    private int sideLength;
    private int winCond;

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
     * checks the winner
     * @return " " if game is still running, "X" or "O" depending on the winner
     */
    public String checkWinner(){
        int inARowCount = 1;
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength - 1; j++){
                if (board.atLoc(i, j).equals(board.atLoc(i, j+1))){
                    inARowCount++;
                    if (inARowCount == winCond){
                        isRunning = false;
                        return board.atLoc(i, j);
                    }
                }
                else{
                    inARowCount = 1;
                }
            }
        }
        inARowCount = 1;
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength - 1; j++){
                if (board.atLoc(j, i).equals(board.atLoc(j + 1, i))){
                    inARowCount++;
                    if (inARowCount == winCond){
                        isRunning = false;
                        return board.atLoc(i, j);
                    }
                }
                else{
                    inARowCount = 1;
                }
            }
        }
        for (int i = 0; i <= sideLength - winCond; i++){
            for (int j = 0; j <= sideLength - winCond; j++){
                for (int k = 1; k < winCond; k++){
                    if (board.atLoc(i + k -1,j + k -1).equals(board.atLoc(i+k,j+k))){
                        inARowCount++;
                        if (inARowCount == winCond){
                            isRunning = false;
                            return board.atLoc(i, j);
                        }
                    }
                    else{
                        inARowCount = 1;
                    }
                }
            }
        }
        for (int i = winCond - 1; i < sideLength; i++){
            for (int j = 0; j <= sideLength - winCond; j++){
                for (int k = 1; k < winCond; k++){
                    if (board.atLoc(i-k+1,j+k-1).equals(board.atLoc(i-k,j+k))){
                        inARowCount++;
                        if (inARowCount == winCond){
                            isRunning = false;
                            return board.atLoc(i, j);
                        }
                    }
                    else{
                        inARowCount = 1;
                    }
                }
            }
        }
        return " ";
    }

    /**
     * prints the result of the game
     */
    public void getResults(){
        if(isRunning == false){
            if (checkTie() == true){
                System.out.println("Tie!");
            }
            else if (checkWinner().equals("X")){
                System.out.println("X wins");
            }
            else if (checkWinner().equals("O")){
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

    /**
     * helper method for player moves
     * @param player
     * @param loc
     */
    private void playerMove(String player, String loc){
        for (int i = 0; i < sideLength; i++){
            for (int j = 0; j < sideLength; j++){
                if (board.atLoc(i,j).equals(loc)){
                    board.setLoc(player, i, j);
                }
            }
        }
    }

}
