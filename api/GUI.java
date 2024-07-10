package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class GUI implements ActionListener{
    private String player = "X";
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private ArrayList<String> winDirect = new ArrayList<String>();
    private int winCond;
    private int length;
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private JTextField nameField;
    private String player1 = "";
    private String player2 = "";
    private JLabel label = new JLabel("");
    private Timer player1Timer;
    private Timer player2Timer;
    private JLabel p1timeLabel;
    private JLabel p2timeLabel;
    private long p1Time;
    private long p2Time;
    private long p1clockTime;
    private long p2clockTime;

    public GUI(int length, Game game){
        JFrame frame = new JFrame("Tic Tac Toe");
        this.length = length;
        player1Timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            p1clockTime = now - p1Time;
            if (p1clockTime >= 0) {
                double seconds = p1clockTime / 1000.0;
                p1timeLabel.setText(String.format(player1 + "'s time: %.1f" + "   ", seconds));
            }
            }
        });
        player2Timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            p2clockTime = now - p2Time;
            if (p2clockTime >= 0) {
                double seconds = p2clockTime / 1000.0;
                p2timeLabel.setText(String.format(player2 + "'s time: %.1f", seconds));
            }
            }
        });
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 24));
        JLabel nameLabel = new JLabel("Player 1 name: ");
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        nameField.addActionListener(new ActionListener() {
            //gets the name of the players and sets the label to the current player
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1.equals("")){
                    player1 = nameField.getText();
                    nameField.setText("");
                    label.setText(player1 + "'s"+ " turn");
                    nameLabel.setText("Player 2 name: ");
                } else if(player2.equals("")) {
                    player2 = nameField.getText();
                    nameField.setText("");
                    nameField.removeActionListener(this);
                    nameField.setVisible(false);
                    nameLabel.setVisible(false);
                    enableAllButtons(buttons);
                    JPanel labelPanel = new JPanel();
                    labelPanel.setLayout(new FlowLayout());
                    p1Time = System.currentTimeMillis();
                    p2Time = System.currentTimeMillis();
                    p1timeLabel = new JLabel(player1 + "'s time: 0.0", JLabel.CENTER);
                    p2timeLabel = new JLabel(player2 + "'s time: 0.0", JLabel.CENTER);
                    labelPanel.add(p1timeLabel);
                    labelPanel.add(p2timeLabel);
                    frame.add(labelPanel, BorderLayout.SOUTH);
                    p2timeLabel.setVisible(true);
                    p1timeLabel.setVisible(true);
                    player1Timer.start();
                }
            }
        });
        
        //Reset button
        winDirect = game.getWinDirect();
        winCond = game.getWinCond();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JLabel endGame = new JLabel();
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
                label.setText(player1 + "'s turn(X)");
                enableAllButtons(buttons);
                removeAllHighlights(buttonPanel);
                p1clockTime = 0;
                p2clockTime = 0;
                p1Time = System.currentTimeMillis();
                p2Time = System.currentTimeMillis();
                p2timeLabel.setText(String.format(player2 + "'s time: %.1f", 0.0));
                player1Timer.start();
            }
        });
        frame.add(namePanel, BorderLayout.NORTH);
        containerPanel.add(resetButton, BorderLayout.SOUTH);

        //makes the buttons for the game
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
                            player1Timer.stop();
                            player2Timer.stop();
                            if (game.getWinner().equals("X")){
                                endGame.setText(player1 + "(X) wins");
                            } else if (game.getWinner().equals("O")){
                                endGame.setText(player2 + "(O) wins");
                            } else {
                                endGame.setText("Tie");
                            }
                            containerPanel.add(endGame, BorderLayout.NORTH);
                            resetButton.setVisible(true);
                            GUI.this.highlightWinner(button, buttonPanel, player, winDirect);
                            disableAllButtons(buttons);
                            return;
                        }
                        if (player.equals("X")){
                            player = "O";
                            label.setText(player2 + "'s turn(O)");
                            player1Timer.stop();
                            p2Time = System.currentTimeMillis() - p2clockTime;
                            player2Timer.start();
                        } else {
                            player = "X";
                            label.setText(player1 + "'s turn(X)");
                            player2Timer.stop();
                            p1Time = System.currentTimeMillis() - p1clockTime;
                            player1Timer.start();
                        }
                    }
                });
                buttonPanel.add(button);
                buttons.add(button);
            }
        }
        disableAllButtons(buttons);
        containerPanel.add(label, BorderLayout.NORTH);
        containerPanel.add(buttonPanel);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));


        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void changeColor(JButton button){
        button.setBackground(Color.yellow);
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

    private void enableAllButtons(ArrayList<JButton> buttons) {
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    private void removeAllHighlights(JPanel buttonPanel){
        for (JButton button : buttons){
            if (button.getBackground().equals(Color.yellow)){
                button.setBackground(new JButton().getBackground());
                button.setBorder(new JButton().getBorder());
            }
        }
    }

}