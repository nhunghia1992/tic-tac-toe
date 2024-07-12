package api;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player {
    /**
     * instant variables
     */
    private String name;
    private Timer playerTimer;
    private long playerTime;
    private long playerClockTime;
    private JLabel playerTimeLabel;

    /**
     * creates a new player
     * @param name
     */
    public Player(){
        name = "";
        playerTimeLabel = new JLabel();
        playerTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            long now = System.currentTimeMillis();
            playerClockTime = now - playerTime;
            if (playerClockTime >= 0) {
                double seconds = playerClockTime / 1000.0;
                playerTimeLabel.setText(String.format(name + "'s time: %.1f" + "   ", seconds));
            }
            }
        });
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Timer getTimer(){
        return playerTimer;
    }

    public void stopTimer(){
        playerTimer.stop();
    }

    public void startTimer(){
        playerTimer.start();
    }

    public void setTime(long time){
        playerTime = time;
    }

    public long getClockTime(){
        return playerClockTime;
    }

    public void setClockTime(long time){
        playerClockTime = time;
    }

    public void setTimeLabel(String label){
        playerTimeLabel.setText(label);
    }

    public JLabel getTimeLabel(){
        return playerTimeLabel;
    }
}
