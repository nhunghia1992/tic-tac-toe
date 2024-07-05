package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class GUI implements ActionListener{
    private String player = "X";
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private ArrayList<String> winDirect = new ArrayList<String>();
    private int winCond;
    private int length;
    private ArrayList<JButton> buttons = new ArrayList<JButton>();

    public GUI(int length, Game game){
        this.length = length;
        winDirect = game.getWinDirect();
        winCond = game.getWinCond();
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JLabel endGame = new JLabel();
        JLabel label = new JLabel("Player " + player + "'s' turn");
        JPanel containerPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        resetButton.setVisible(false);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.reset();
                resetButton.setVisible(false);
                for (Component comp : buttonPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton) comp).setText("");
                    }
                }
                endGame.setText("");
                player = "X";
            }
        });

        containerPanel.add(resetButton, BorderLayout.SOUTH);

        GridLayout grid = new GridLayout(length,length);
        buttonPanel.setLayout(grid);
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                JButton button = new JButton();
                button.setName(alphabet[i] + Integer.toString(j+1));
                button.setPreferredSize(new Dimension(50, 50));
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(button.getText().equals("X") || button.getText().equals("O")){
                            return;
                        }
                        if (game.gameRunning() == true){
                            button.setText(player);
                            game.playerMove(player, button.getName());
                        }
                        if (game.gameRunning() == false){
                            endGame.setText("Player " + game.getWinner() + " wins");
                            containerPanel.add(endGame, BorderLayout.NORTH);
                            resetButton.setVisible(true);
                            GUI.this.highlightWinner(button, buttonPanel, player, winDirect);
                            disableAllButtons(buttons);
                            return;
                        }
                        if (player.equals("X")){
                            player = "O";
                        } else {
                            player = "X";
                        }
                        label.setText("Player " + player + "'s' turn");
                    }
                });
                buttonPanel.add(button);
                buttons.add(button);
            }
        }
        containerPanel.add(label, BorderLayout.NORTH);
        containerPanel.add(buttonPanel);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));


        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }

    private void changeColor(JButton button){
        button.setBackground(Color.red);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void highlightVerticalWin(JButton button, JPanel buttonPanel, String winner, int x, int y){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < winCond; i++){
            if (tempY > 50 * (length-1)){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
        }
        tempY = y;
        for (int i = 0; i < winCond; i++){
            tempY -= 50;
            if (tempY < 0){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightHorizontallWin(JButton button, JPanel buttonPanel, String winner, int x, int y){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < winCond; i++){
            if (tempX > 50 * (length-1)){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
            tempX += 50;
        }
        tempX = x;
        for (int i = 0; i < winCond; i++){
            tempX -= 50;
            if (tempX < 0){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightDiagDownWin(JButton button, JPanel buttonPanel, String winner, int x, int y){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < winCond; i++){
            if (tempY > 50 * (length-1) || tempX > 50 * (length-1)){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
            tempX += 50;
        }
        tempY = y;
        tempX = x;
        for (int i = 0; i < winCond; i++){
            tempY -= 50;
            tempX -= 50;
            if (tempY < 0 || tempX < 0){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightDiagUpWin(JButton button, JPanel buttonPanel, String winner, int x, int y){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < winCond; i++){
            if (tempY > 50 * (length-1) || tempX < 0){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
            tempX -= 50;
        }
        tempY = y;
        tempX = x;
        for (int i = 0; i < winCond; i++){
            tempY -= 50;
            tempX += 50;
            if (tempY < 0 || tempX > 50 * (length-1)){
                break;
            }
            else if(!((JButton)buttonPanel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)buttonPanel.getComponentAt(tempX, tempY));
            }
        }
    }

    public void highlightWinner(JButton button, JPanel buttonPanel, String winner, ArrayList<String> winDirect){
        int x = button.getX();
        int y = button.getY();
        buttonPanel.getComponentAt(x,y);
        for (int i = 0; i < winDirect.size(); i++){
            if (winDirect.get(i).equals("vertical")){
                highlightVerticalWin(button, buttonPanel, winner, x, y);
            }
            if (winDirect.get(i).equals("horizontal")){
                highlightHorizontallWin(button, buttonPanel, winner, x, y);
            }
            if (winDirect.get(i).equals("diagUp")){
                highlightDiagUpWin(button, buttonPanel, winner, x, y);
            }
            if (winDirect.get(i).equals("diagDown")){
                highlightDiagDownWin(button, buttonPanel, winner, x, y);
            }
        }
    }

    private void disableAllButtons(ArrayList<JButton> buttons) {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }
}