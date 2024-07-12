package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class GUI {
    /**
     * instant variables
     */
    private JFrame frame;
    private Player player1;
    private Player player2;
    private JTextField nameField;
    private JLabel label = new JLabel("");
    
    /**
     * creates a new GUI
     * @param length
     * @param game
     */
    public GUI(int length, Game game){
        player1 = new Player();
        player2 = new Player();
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 24));
        JLabel nameLabel = new JLabel("Player 1 name: ");
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.setVisible(false);
        
        JPanel containerPanel = new JPanel();

        buttonPanel buttonPanel = new buttonPanel(game, containerPanel, resetButton, label, player1, player2);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.reset();
                resetButton.setVisible(false);
                for (Component comp : buttonPanel.getPanel().getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton) comp).setText("");
                    }
                }
                label.setText(player1.getName() + "'s turn(X)");
                buttonPanel.enableAllButtons();
                buttonPanel.removeAllHighlights();
                player1.setClockTime(0);
                player2.setClockTime(0);
                player1.setTime(System.currentTimeMillis());
                player2.setTime(System.currentTimeMillis());
                player2.setTimeLabel(String.format(player2.getName() + "'s time: %.1f", 0.0));
                player1.startTimer();
                buttonPanel.hideResults();
            }
        });
        containerPanel.add(resetButton, BorderLayout.SOUTH);
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1.getName().equals("")){
                    player1.setName(nameField.getText());
                    nameField.setText("");
                    label.setText(player1.getName() + "'s"+ " turn");
                    nameLabel.setText("Player 2 name: ");
                } else if(player2.getName().equals("")) {
                    player2.setName(nameField.getText());
                    nameField.setText("");
                    nameField.removeActionListener(this);
                    nameField.setVisible(false);
                    nameLabel.setVisible(false);
                    buttonPanel.enableAllButtons();
                    JPanel labelPanel = new JPanel();
                    labelPanel.setLayout(new FlowLayout());
                    player1.setTime(System.currentTimeMillis());
                    player2.setTime(System.currentTimeMillis());
                    player1.setTimeLabel(player1.getName() + "'s time: 0.0");
                    player2.setTimeLabel(player2.getName() + "'s time: 0.0");
                    labelPanel.add(player1.getTimeLabel());
                    labelPanel.add(player2.getTimeLabel());
                    frame.add(labelPanel, BorderLayout.SOUTH);
                    player1.startTimer();
                }
            }
        });
        frame.add(namePanel, BorderLayout.NORTH);
        buttonPanel.disableAllButtons();
        containerPanel.add(label, BorderLayout.NORTH);
        containerPanel.add(buttonPanel.getPanel());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));


        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
