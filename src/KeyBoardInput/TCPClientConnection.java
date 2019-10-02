package KeyBoardInput;

import java.io.*;
import java.net.Socket;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class TCPClientConnection {
    private String IP;
    private int PORT;
    private Socket socket;
    PrintWriter out;
    byte[] greenMessage = new byte[] { 0x20, 0x01, 0x00, 0x0d};
    byte[] redMessage = new byte[] {0x21,0x01,0x00, 0x0d};
    public TCPClientConnection(String ip, int port){
        this.IP = ip;
        this.PORT = port;
    }

    public void connect(){
        try {
            //readPropertyFile();
            socket = new Socket(IP, PORT);
            System.out.println("connection made");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("connection not made");
        }
    }
    public boolean sendGreen(){
        DataOutputStream out;
        connect();
        try {
            //create write stream to send information
            out=new DataOutputStream(socket.getOutputStream());
            out.write(greenMessage);
            out.flush();
            TimeUnit.MILLISECONDS.sleep(500);
            CloseSocket();
        } catch (IOException e) {
            //Bail out
            System.out.println("Green light issue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendRed(){
        DataOutputStream out;
        connect();
        try {
            //create write stream to send information
            out=new DataOutputStream(socket.getOutputStream());
            out.write(redMessage);
            out.flush();
            TimeUnit.MILLISECONDS.sleep(500);
            CloseSocket();
        } catch (IOException e) {
            //Bail out
            System.out.println("Red light issue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void CloseSocket(){
        try {
            socket.close();
            System.out.println("Socket Closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean sendBMessage(String message) {
        connect();
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(message);
            out.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalBlockingModeException e) {
            e.printStackTrace();
            System.out.println("Illegal Block error");
        }
        return true;

    }

}
