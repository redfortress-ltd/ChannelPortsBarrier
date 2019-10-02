import GUI.RegGui;
import GUI.VideoGif;
import KeepAlive.KeepAliveServer;
import KeyBoardInput.TCPClientConnection;
import Singleton.InfoSingleton;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private Thread t;
    private static Socket socket, cameraSocket;
    private ArrayList<String> code;
    private static PrintWriter out, sendMessage;
    private static BufferedReader in, recieveMessage;
    private TCPClientConnection trafficClient;
    private JTextField field;
    private RegGui regGui;
    private static VideoGif videoGif, moveGif, alertGif;
    private InfoSingleton info;
    boolean blink = true;
    static String message;
    public static void main(String[] args) {
//        System.out.println("HEllo");
//        TCPClientConnection trafficClient = new TCPClientConnection("10.0.1.134",17494);
//        trafficClient.sendGreen();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                trafficClient.sendRed();
//                //moveGif.close();
//
//            }
//        }, 10000);
        RegGui regGui = new RegGui();
        regGui.open();
        KeepAliveServer aliveServer = new KeepAliveServer();
        aliveServer.start();


    }
}