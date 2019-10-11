package KeyBoardInput;




import GUI.RegGui;
import GUI.VideoGif;
import Session.GateSession;
import Singleton.InfoSingleton;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by nathanapps on 13/04/2016.
 */

/**
 * TODO:
 * Need to set code ArrayList size -NA 22/04/15
 * Check read config property file works - NA 24/04/15
 * */

public class CHPo_Buffer implements Runnable {
    private Thread t;
    private Socket socket, cameraSocket;
    private ArrayList<String> code;
    private PrintWriter out, sendMessage;
    private BufferedReader in, recieveMessage;
    private TCPClientConnection trafficClient;
    private JTextField field;
    private RegGui regGui;
    private static VideoGif videoGif, moveGif, alertGif;
    private InfoSingleton info;
    boolean blink = true;

    public CHPo_Buffer(JTextField field, RegGui regGui) {
        info = new InfoSingleton().getInstance();
        code = new ArrayList<String>();
        t = new Thread(this);
        t.start();
        this.field = field;
        this.regGui = regGui;
    }

    public CHPo_Buffer(JTextField field, VideoGif videoGif) {
        info = new InfoSingleton().getInstance();
        code = new ArrayList<String>(10);
        t = new Thread(this);
        t.start();
        this.field = field;
        this.videoGif = videoGif;
    }

    @Override
    public void run() {

    }

    public boolean addToBuffer(String s) {
        System.out.println(code.size());
        if (code.size() == 11) {
            //Do nothing
        } else {
            code.add(s);
        }
        //if(s.length() > 1){ return false; }
//        try {
//            PrintWriter out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())), true);
//            //out.println(s + "$0d");
//            out.println(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;

    }

    public String setString() {
        String finalReg = "";
        if (code.size() < 1) {
            return "";
        }

        for (int i = 0; i < code.size(); i++) {
            finalReg = finalReg + code.get(i);
        }
        return finalReg;
    }

    public boolean deleteLastInBuffer() {

        if (code.size() < 1) {
            return false;
        }

        code.remove(code.size() - 1);

        return true;

    }

    /*public String sendBuffer(){
        String finalReg = "";
        if(registration.size() < 1){ return ""; }

        for (int i = 0; i < registration.size(); i++){
            finalReg = finalReg + registration.get(i);
        }
        registration.clear();
        registration = new ArrayList<String>();
        return  finalReg;
    }*/

    public boolean sendBuffer() {
        connect();
        String finalReg = "";
        String recievedMessage = "";
        if (code.size() < 1) {
            return false;
        }

        for (int i = 0; i < code.size(); i++) {
            finalReg = finalReg + code.get(i);
        }
        System.out.println(finalReg);
        //videoGif.showMove();
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out.printf("CAMERA\r", new Object[0]);
            recievedMessage = this.in.readLine();
            System.out.println("Camera Recieve " + recievedMessage);
            String stillImageName = takePicture();

            if(GateSession.getInstance() != null && GateSession.getInstance().getImageData() != null) {
                File targetFile = new File(info.getImagePath() + GateSession.getInstance().getAnprImageName());
                FileUtils.writeByteArrayToFile(targetFile, GateSession.getInstance().getImageData());
            }

            Date date = new Date();
            String modifiedDate = (new SimpleDateFormat("yyyyMMdd")).format(date);

            String dataLine = "DATA=" + modifiedDate + "," + (new SimpleDateFormat("HHmmss")).format(date) + "," + finalReg + "\r";
            String anprLine = "ANPR=";

            if (GateSession.getInstance() != null && !info.getErrorMode()) {
                anprLine = anprLine + modifiedDate + "," + (new SimpleDateFormat("HHmmss")).format(date) + "," + finalReg + "," + GateSession.getInstance().getRegistration() + "," + GateSession.getInstance().getConfidence() + "," + GateSession.getInstance().getAnprImageName() + "\r";
            }

            if(info.getErrorMode()) {
                anprLine = anprLine + modifiedDate + "," + (new SimpleDateFormat("HHmmss")).format(date) + "," + finalReg + ",Error,1,Error.png\r";
            }
            String stillLine = "STIL=" + modifiedDate + "," + (new SimpleDateFormat("HHmmss")).format(date) + "," + finalReg + "," + stillImageName + "\r";
            this.out.printf(dataLine, new Object[0]);
            recievedMessage = this.in.readLine();
            this.out.printf(stillLine, new Object[0]);
            recievedMessage = this.in.readLine();
            this.out.printf(anprLine, new Object[0]);

            recievedMessage = this.in.readLine();
            System.out.println("Recieved: " + recievedMessage);
            GateSession.clearSession();
            //String[] splitMessage = recievedMessage.split(",");
            out.flush();
            if (recievedMessage.equals("MOVE")) {
                System.out.println("OK");
                //moveGif = new VideoGif();
                //videoGif = new VideoGif();
                //moveGif.showMove();
                //regGui.close();
                //videoGif.showMove();
                if (info.getKeyPos().equals("LEFT")) {
                    if (blink) {
                        field.setText(">>>>>>>>>>>>>");
                        //blink = false;
                    } else {
                        //field.setText("");
                        //blink = true;
                    }
                    //videoGif.showEntrance();
                } else if (info.getKeyPos().equals("RIGHT")) {
                    //videoGif.showExit();
                    field.setText("<<<<<<<<<<<<<");
                }
                trafficClient.sendGreen();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        trafficClient.sendRed();
                        //moveGif.close();
                        field.setText("");

                    }
                }, info.getVidTimer());
            } else if (recievedMessage.equals("RETRY")) {
                System.out.println("RETRY");
                field.setText("RETRY");
            } else if (recievedMessage.equals("ALERT")) {
                field.setText("ALERT");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //trafficClient.sendRed();
                        //moveGif.close();
                        field.setText("");

                    }
                }, info.getVidTimer());
            }
            code.clear();
            code = new ArrayList<String>();
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Connection error");
            field.setText("Network Issue");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());
        } catch (IOException e) {
            e.printStackTrace();
            clearBuffer();
        } catch (IllegalBlockingModeException e) {
            e.printStackTrace();
            System.out.println("Illegal Block error");
            field.setText("TRY AGAIN");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Argument error");
            field.setText("TRY AGAIN");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());
        }
        return true;

    }

    private String takePicture() {
        try {
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(info.getStillCameraUsername(), info.getStillCameraPassword());

            basicCredentialsProvider.setCredentials(AuthScope.ANY, credentials);



            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(basicCredentialsProvider).build();

            HttpResponse response = closeableHttpClient.execute(new HttpGet(info.getStillCameraURL()));

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                String staticImageName = "ST_" + UUID.randomUUID().toString() + ".png";
                File targetFile = new File(info.getImagePath() + staticImageName);
                FileUtils.copyInputStreamToFile(in, targetFile);
                return staticImageName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void clearBuffer() {
        code.clear();
        code = new ArrayList<String>();
    }

    public void connect() {
        try {
            if (info.getBarrierType().equals("ENT")) {
                socket = new Socket(info.getServerIP(), info.getEntPort());
                socket.setSoTimeout(2000);
                trafficClient = new TCPClientConnection(info.getTrafficIPENT(), info.getTrafficPortEnt());
                System.out.println("connection made");
            } else if (info.getBarrierType().equals("EXT")) {
                socket = new Socket(info.getServerIP(), info.getExtPort());
                socket.setSoTimeout(2000);
                trafficClient = new TCPClientConnection(info.getTrafficIPEXT(), info.getTrafficPortExt());
            } else {
                System.out.println("Error");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("connect: Connection error");
            field.setText("Network Issue");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());

        } catch (IllegalBlockingModeException e) {
            e.printStackTrace();
            System.out.println("connect: Illegal Block error");
            field.setText("TRY AGAIN");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("connect: Argument error");
            field.setText("TRY AGAIN");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("connect: connection not made");
            field.setText("TRY AGAIN");
            clearBuffer();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //trafficClient.sendRed();
                    //moveGif.close();
                    field.setText("");

                }
            }, info.getVidTimer());
        }
    }
}


