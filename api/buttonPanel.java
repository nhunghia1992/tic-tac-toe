package api;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class buttonPanel {
    /**
     * instant variables
     */
    private JPanel panel;
    private GridLayout grid;
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String player = "X";
    private JLabel results = new JLabel("");
    private ArrayList<JButton> buttons;

    /**
     * creates a new button panel
     * @param length
     * @param game
     * @param containerPanel
     * @param resetButton
     * @param label
     * @param buttonPanel
     * @param player1
     * @param player2
     */
    public buttonPanel(Game game, JPanel containerPanel, JButton resetButton, JLabel label, Player player1, Player player2){
        GridLayout grid = new GridLayout(game.getSideLength(),game.getSideLength());
        panel = new JPanel();
        panel.setLayout(grid);
        buttons = new ArrayList<JButton>();
        grid = new GridLayout(game.getSideLength(), game.getSideLength());
        for (int i = 0; i < game.getSideLength(); i++){
            for (int j = 0; j < game.getSideLength(); j++){
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
                            player1.stopTimer();
                            player2.stopTimer();
                            if (game.getWinner().equals("X")){
                                results.setText(player1.getName() + "(X) wins");
                            } else if (game.getWinner().equals("O")){
                                results.setText(player2.getName() + "(O) wins");
                            } else {
                                results.setText("Tie");
                            }
                            containerPanel.add(results, BorderLayout.NORTH);
                            resetButton.setVisible(true);
                            highlightWinner(button, player, game);
                            disableAllButtons();
                            return;
                        }
                        if (player.equals("X")){
                            player = "O";
                            label.setText(player2.getName() + "'s turn(O)");
                            player1.stopTimer();
                            player2.setTime(System.currentTimeMillis() - player2.getClockTime());
                            player2.startTimer();
                        } else {
                            player = "X";
                            label.setText(player1.getName() + "'s turn(X)");
                            player2.stopTimer();
                            player1.setTime(System.currentTimeMillis() - player1.getClockTime());
                            player1.startTimer();
                        }
                    }
                });
                panel.add(button);
                buttons.add(button);
            }
        }
    }



    public String getCurrentPlayer(){
        return player;
    }

    public JPanel getPanel(){
        return panel;
    }

    private void changeColor(JButton button){
        button.setBackground(Color.yellow);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * disables all buttons
     * @param buttons
     */
    public void disableAllButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void highlightVerticalWin(JButton button, String winner, int x, int y, Game game){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            if (tempY > 50 * (game.getSideLength()-1)){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
        }
        tempY = y;
        for (int i = 0; i < game.getWinCond(); i++){
            tempY -= 50;
            if (tempY < 0){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightHorizontallWin(JButton button, String winner, int x, int y, Game game){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            if (tempX > 50 * (game.getSideLength()-1)){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
            tempX += 50;
        }
        tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            tempX -= 50;
            if (tempX < 0){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightDiagDownWin(JButton button, String winner, int x, int y, Game game){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            if (tempY > 50 * (game.getSideLength()-1) || tempX > 50 * (game.getSideLength()-1)){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
            tempX += 50;
        }
        tempY = y;
        tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            tempY -= 50;
            tempX -= 50;
            if (tempY < 0 || tempX < 0){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
        }
    }

    private void highlightDiagUpWin(JButton button, String winner, int x, int y, Game game){
        int tempY = y;
        int tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            if (tempY > 50 * (game.getSideLength()-1) || tempX < 0){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
            tempY += 50;
            tempX -= 50;
        }
        tempY = y;
        tempX = x;
        for (int i = 0; i < game.getWinCond(); i++){
            tempY -= 50;
            tempX += 50;
            if (tempY < 0 || tempX > 50 * (game.getSideLength()-1)){
                break;
            }
            else if(!((JButton)panel.getComponentAt(tempX, tempY)).getText().equals(winner)){
                break;
            }
            else{
                changeColor((JButton)panel.getComponentAt(tempX, tempY));
            }
        }
    }

    public void highlightWinner(JButton button, String winner, Game game){
        int x = button.getX();
        int y = button.getY();
        panel.getComponentAt(x,y);
        for (int i = 0; i < game.getWinDirect().size(); i++){
            if (game.getWinDirect().get(i).equals("vertical")){
                highlightVerticalWin(button, winner, x, y, game);
            }
            if (game.getWinDirect().get(i).equals("horizontal")){
                highlightHorizontallWin(button, winner, x, y, game);
            }
            if (game.getWinDirect().get(i).equals("diagUp")){
                highlightDiagUpWin(button, winner, x, y, game);
            }
            if (game.getWinDirect().get(i).equals("diagDown")){
                highlightDiagDownWin(button, winner, x, y, game);
            }
        }
    }

    public void setResults(String result){
        results.setText(result);
    }

    public void enableAllButtons() {
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    public void removeAllHighlights(){
        for (JButton button : buttons){
            if (button.getBackground().equals(Color.yellow)){
                button.setBackground(new JButton().getBackground());
                button.setBorder(new JButton().getBorder());
            }
        }
    }

    public void hideResults(){
        results.setText("");
    }
}

