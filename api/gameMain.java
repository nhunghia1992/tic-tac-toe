package api;
import java.util.Scanner;
import java.util.InputMismatchException;

public class gameMain {

    public static void runGame(int boardLength, int winCond){
        Scanner scan = new Scanner(System.in);
        Game game1 = new Game(boardLength, winCond);
        game1.printBoard();
        while(game1.gameRunning()){
            System.out.print("player X move: ");
            game1.xMove(scan.nextLine());
            System.out.println();
            game1.printBoard();
            if(game1.checkWinner().equals("X")){
                break;
            }
            if(game1.checkTie()){
                break;
            }
            System.out.print("player O move: ");
            game1.oMove(scan.nextLine());
            System.out.println();
            game1.printBoard();
            game1.checkWinner();
            game1.checkTie();
        }
        game1.getResults();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Board length: ");
        int boardLength = scan.nextInt();
        System.out.println();
        System.out.print("Win condition: ");
        int winCond = scan.nextInt();
        runGame(boardLength, winCond);

        
    }
}
