package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class GUI implements ActionListener{
    private String player = "X";
    
    public GUI(int length, Game game){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
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
        // TODO Auto-generated method stub
    }
}