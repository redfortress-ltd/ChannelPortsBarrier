package GUI;

import KeyBoardInput.CHPo_KeyListener;
import Singleton.InfoSingleton;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class VideoGif {
    private static JFrame f;
    private InfoSingleton info;
    private RegGui gui;
    InfoSingleton infoSingleton;
    Font defualtFont;
    JTextField field = new JTextField(20);
    private static ImagePanel panel;
    private static DocumentFilter filter = new UppercaseDocumentFilter();
    private static CHPo_KeyListener CHPo_KeyListener;
    private static KeyListener key;
    private static ImageIcon icon;
    private static  JLabel label;
    private static JPanel movePanel,entPanel, extPanel,alertPanel;
    private static int width = 2000,height = 1000;

    public VideoGif(){
        info = new InfoSingleton().getInstance();
        f = new JFrame("Animation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setUndecorated(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        infoSingleton = new InfoSingleton().getInstance();
        panel = new ImagePanel(new ImageIcon(infoSingleton.getRegImg()).getImage());
        field.setHighlighter(null);
        CHPo_KeyListener = new CHPo_KeyListener(field, this);
    }

    public void showEntrance(){
        entPanel = new JPanel();
        icon = new ImageIcon(info.getENTVid());
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        label = new JLabel(icon);
        f.setContentPane(entPanel);
        f.getContentPane().add(label);
        f.invalidate();
        f.validate();
        f.getContentPane().add(label);
        key = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                regGUI(e.getKeyChar());
                field.setHighlighter(null);
                f.setContentPane(panel);
                f.invalidate();
                f.validate();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        f.addKeyListener(key);
        f.requestFocus();
        //f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /*
    public void showMove(){
        ImageIcon icon = new ImageIcon(info.getMoveVid());
        JLabel label = new JLabel(icon);
        int width = 2000;
        int height = 1000;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        f.getContentPane().add(label);
        f.invalidate();
        f.validate();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void showExit(){
        ImageIcon icon = new ImageIcon(info.getExtVid());
        int width = 2000;
        int height = 1000;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icon);

        f.getContentPane().add(label);
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void showAlert(){
        ImageIcon icon = new ImageIcon(info.getAlertVid());
        int width = 2000;
        int height = 1000;
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icon);

        f.getContentPane().add(label);
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    */

    public void showMove(){
        movePanel = new JPanel();
        icon = new ImageIcon(info.getMoveVid());
        label = new JLabel(icon);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        f.setContentPane(movePanel);
        f.getContentPane().add(label);
        f.invalidate();
        f.validate();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        //f.pack();
        //f.setLocationRelativeTo(null);
        //f.setVisible(true);
    }

    public void showExit(){
        extPanel = new JPanel();
        icon = new ImageIcon(info.getExtVid());
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        label = new JLabel(icon);
        f.setContentPane(extPanel);
        f.getContentPane().add(label);
        f.invalidate();
        f.validate();
        f.getContentPane().add(label);
        key = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                regGUI(e.getKeyChar());
                field.setHighlighter(null);
                f.setContentPane(panel);
                f.invalidate();
                f.validate();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        f.addKeyListener(key);
        f.requestFocus();
        //f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void showAlert(){
        alertPanel = new JPanel();
        icon = new ImageIcon(info.getAlertVid());
        label = new JLabel(icon);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        f.setContentPane(alertPanel);
        f.getContentPane().add(label);
        f.invalidate();
        f.validate();
        //f.pack();
        f.setLocationRelativeTo(null);
    }


    public void regGUI(char keyChar){
        f.removeKeyListener(key);
        field.removeKeyListener(CHPo_KeyListener);
        System.out.println(keyChar);
        defualtFont = new Font("Arial", Font.BOLD, 100);
        f.setContentPane(panel);
        f.setBackground(Color.white);
        Container content = f.getContentPane();
        content.setLayout(new GridLayout(0, 1));
        field.setFont(defualtFont);
        field.setPreferredSize(new Dimension(150, 40));
        field.setBorder(BorderFactory.createLineBorder(Color.white, 0));
        field.setBackground(null);
        field.setOpaque(false);
        ((AbstractDocument) field.getDocument()).setDocumentFilter(filter);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setText(String.valueOf(keyChar));
        CHPo_KeyListener = new CHPo_KeyListener(field, this);
        field.addKeyListener(CHPo_KeyListener);
        content.add(field);
        //f.pack();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        field.setHighlighter(null);
        field.requestFocus();
        //setVisible(true);
    }

    public void close(){
        f.dispose();
    }
}
