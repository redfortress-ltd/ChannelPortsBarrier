package KeyBoardInput;

import GUI.RegGui;
import GUI.VideoGif;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by nathanapps on 13/04/2016.
 */
public class CHPo_KeyListener implements KeyListener {
    CHPo_Buffer buffer;
    static SmartTimer smartTimer;
    JTextField field;
    RegGui regGui;
    VideoGif videoGif;
    boolean state = false;
    private static int counter;
    public CHPo_KeyListener(JTextField field, RegGui regGui){

        buffer = new CHPo_Buffer(field,regGui);
        this.field = field;
        buffer.addToBuffer(this.field.getText());
        //buffer.setString();
        smartTimer = new SmartTimer(buffer,field, regGui);

    }

    public CHPo_KeyListener(JTextField field, VideoGif videoGif) {
        buffer = null;
        buffer = new CHPo_Buffer(field,videoGif);
        this.field = field;
        buffer.addToBuffer(this.field.getText());
        buffer.setString();
        smartTimer = null;
        smartTimer = new SmartTimer(buffer,field, videoGif);
    }

    public static int getNumOfInstances() {
        return counter;
    }

    public void setState(boolean state){
        this.state = state;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(field.getText().equals("RETRY")){
            field.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //smartTimer.TimerStarted();
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            buffer.deleteLastInBuffer();
            //buffer.setString();
        } else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            field.setText("");
            smartTimer.stopTimer();
            buffer.sendBuffer();
        }else if(e.getKeyCode() == KeyEvent.VK_DELETE){
            field.setText("");
            buffer.clearBuffer();
        }else{
            smartTimer.TimerStarted();
            String letter = String.valueOf(e.getKeyChar());
            System.out.println("KeyPressed " + letter);
            buffer.addToBuffer("" + e.getKeyChar());
            buffer.setString();
            field.setText(buffer.setString());

        }
    }
}




