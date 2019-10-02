package KeepAlive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nathanapps on 22/09/2017.
 */
public class ClientHandler implements Runnable {
    private static int numConnections;
    private int connectionId = 0;
    Socket clientSocket;

    public ClientHandler(Socket s) {
        connectionId = numConnections++;
        System.out.println("handling connection, #" + connectionId);
        clientSocket = s;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                outputLine = inputLine;
                System.out.println("received: " + outputLine);
                //out.write(outputLine + "\n");
                //out.flush();
                if(outputLine.equals("HELLO")){
                    //out.write("OK\r");
                    out.printf("OK\r");
                    out.flush();
                    System.out.println("sent: Ok\r");
                    break;
                }else if(outputLine.equals("RESET")){
                    String[] args = new String[] {"/bin/bash", "-c", "sudo reboot"};
                    Process proc = new ProcessBuilder(args).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
                clientSocket.close();
                System.out.println("closing connection, #" + connectionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

