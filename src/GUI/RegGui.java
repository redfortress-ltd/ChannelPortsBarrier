package GUI;

import KeyBoardInput.CHPo_KeyListener;
import Singleton.InfoSingleton;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;

public class RegGui extends JFrame {
    InfoSingleton infoSingleton;
    Font defualtFont;
    JTextField field = new JTextField(20);
    ImagePanel panel;
    DocumentFilter filter = new UppercaseDocumentFilter();
    private char test;
    //ImagePanel panel = new ImagePanel(new ImageIcon("/home/pi/Desktop/numberplate.png").getImage());
    public RegGui(char keyChar) {
        super();
        infoSingleton = new InfoSingleton().getInstance();
        panel = new ImagePanel(new ImageIcon(infoSingleton.getRegImg()).getImage());
        this.test = keyChar;
        field.setHighlighter(null);
        //field.requestFocus();

    }

    public RegGui() {
        super();
        infoSingleton = new InfoSingleton().getInstance();
        panel = new ImagePanel(new ImageIcon(infoSingleton.getRegImg()).getImage());
        //this.test = keyChar;
        field.setHighlighter(null);
        //field.requestFocus();

    }

    public void open(){
        setUndecorated(true);
        //Set Size of the Window (WIDTH, HEIGHT)
        //setSize(1500, 800);
//        buffer = new CHPo_Buffer(field,this);
//        smartTimer = new SmartTimer(buffer,field);
        //Exit Property of the Window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Constructing class Font property
        defualtFont = new Font("Arial", Font.BOLD, 100);
        setContentPane(panel);
        setBackground(Color.white);
        Container content = getContentPane();
        //content.setLayout(new GridLayout(0, 1));
        field.setFont(defualtFont);
        //field.setPreferredSize(new Dimension(150, 100));
        field.setBorder(BorderFactory.createLineBorder(Color.white, 0));
        field.setBackground(null);
        field.setOpaque(false);
        ((AbstractDocument) field.getDocument()).setDocumentFilter(filter);
        field.setHorizontalAlignment(JTextField.CENTER);
        //field.setText(String.valueOf(test));
        field.addKeyListener(new CHPo_KeyListener(field, this));
        field.setBounds(40,600,1800,200);
        content.add(field);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setVisible(true);
    }

    public void close(){
        dispose();
    }
}
