package KeepAlive;

import Singleton.InfoSingleton;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by nathanapps on 22/09/2017.
 */
public class KeepAliveServer {
    private InfoSingleton infoSingleton;
    ServerSocket serverSocket;
    private static int PORT;

    public KeepAliveServer(){
        infoSingleton = new InfoSingleton().getInstance();
        PORT = infoSingleton.getKeepAlivePort();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            while(true){
                Thread clientThread = new Thread(new ClientHandler(serverSocket.accept()));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("closing server socket");
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
