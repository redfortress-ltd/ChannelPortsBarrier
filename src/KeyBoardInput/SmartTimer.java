package KeyBoardInput;

import GUI.RegGui;
import GUI.VideoGif;
import KeyBoardInput.CHPo_Buffer;
import Singleton.InfoSingleton;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nathanapps on 18/05/2016.
 */
public class SmartTimer {
    private Timer timer;
    private boolean hasStarted = false;
    private int interval = 30000; //in seconds
    private CHPo_Buffer buffer;
    private JTextField textField;
    private TimerTask timerTask;
    boolean clearedScreen = false;
    private RegGui regGui;
    private VideoGif videoGif;
    InfoSingleton infoSingleton;

    public SmartTimer(CHPo_Buffer buffer, JTextField textField, RegGui regGui){
        infoSingleton = new InfoSingleton().getInstance();
        this.interval = infoSingleton.getKeyTimer();
        this.buffer = buffer;
        this.textField = textField;
        this.regGui = regGui;
    }

    public SmartTimer(CHPo_Buffer buffer, JTextField field, VideoGif videoGif) {
        infoSingleton = new InfoSingleton().getInstance();
        this.interval = infoSingleton.getKeyTimer();
        this.buffer = buffer;
        this.textField = field;
        this.videoGif = videoGif;
    }

    public void TimerStarted(){
        if(hasStarted) {
            timerTask.cancel();
            timer.cancel();
            timer.purge();
            hasStarted = false;
            clearedScreen = false;
            interval = infoSingleton.getKeyTimer();
            TimerRestart(buffer);
        }else{
            stopTimer();
            interval = infoSingleton.getKeyTimer();
            TimerRestart(buffer);
        }
        System.out.println("Timer Started");
    }

    public void stopTimer(){
        if (timerTask == null) {

        }else {
            timerTask.cancel();
            timer.cancel();
            timer.purge();
            hasStarted = false;
            interval = 0;
            System.out.println("Timer stopped");
        }
    }

    private void TimerRestart(final CHPo_Buffer buffer){
        final int delay = 1000;
        final int period = 1000;
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask = new TimerTask() {
            public void run() {
                hasStarted = true;
                //    System.out.println("Screen: " + setInterval(buffer, serialScreen));
                setInterval(buffer, textField);
                System.out.println("Time: " + interval);
            }
        }, delay, period);
    }

    private int setInterval(CHPo_Buffer buffer, JTextField textField){
        if(interval== 1) {
            hasStarted = false;
            timer.cancel();
            timer.purge();
            buffer.clearBuffer();
            textField.setText("");
            clearedScreen = true;
            //regGui.close();
            //videoGif.close();
            //VideoGif videoGif = new VideoGif();
            stopTimer();
            interval = infoSingleton.getKeyTimer();
//            InfoSingleton info = new InfoSingleton().getInstance();
//            if(info.getBarrierType().equals("ENT")){
//                videoGif.showEntrance();
//            }else if(info.getBarrierType().equals("EXT")){
//                videoGif.showExit();
//            }
            //serialScreen.clearScreen();
            //serialScreen.sendMessage("Enter Num:");
        }
        System.out.println(interval);
        return --interval;
    }

    public boolean isClearedScreen(){
        return clearedScreen;
    }
}
