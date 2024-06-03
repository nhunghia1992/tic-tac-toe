package api;
public class Board {
    private int row;
    private int col;
    private String[][] board;
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * creats a new board
     * @param row
     * @param col
     * @param winCondition
     */
    public Board(int row, int col){
        this.row = row;
        this.col = col;
        this.board = new String[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                board[i][j] = alphabet[i] + Integer.toString(j+1);
            }
        }
    }

    /**
     * prints the board
     */
    public void printBoard(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                System.out.print("[" + board[i][j] + "]");
            }
            System.out.println();
        }
    }

    /**
     * returns whatever is in a location on the board
     * @param i
     * @param j
     * @return
     */
    public String atLoc(int i, int j){
        return board[i][j];
    }

    /**
     * @returns the array representing the board
     */
    public String[][] getBoard(){
        return board;
    }

    /**
     * sets the player mark at the location
     * @param player
     * @param i
     * @param j
     */
    public void setLoc(String player, int i, int j){
        board[i][j] = player;
    }

}
