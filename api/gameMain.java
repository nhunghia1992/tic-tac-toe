package api;
import java.util.Scanner;
import java.util.InputMismatchException;

public class gameMain {

    public static void runGame(int boardLength, int winCond){
        Scanner scan = new Scanner(System.in);
        Game game1 = new Game(boardLength, winCond);
        new GUI(boardLength,game1);
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
