package Singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InfoSingleton {
    private static InfoSingleton uniqInstance;
    private static Properties prop;
    private static InputStream input = null;
    private static String BarrierType, ServerIP, TrafficIPENT, TrafficIPEXT,EntVid, MoveVid, ExitVid, AlertVid, RegImg, KeyPos, ImagePath, StillCameraUsername, StillCameraPassword, StillCameraURL;
    private static int EntPort, ExtPort, TrafficPortEnt, TrafficPortExt, KeepAlivePort, vidTimer,keyTimer,trafficTimer, anprHttpPort;

    public InfoSingleton() {
    }

    public InfoSingleton getInstance() {
        if (uniqInstance == null) {
            synchronized(this) {
                if (uniqInstance == null)
                    uniqInstance = new InfoSingleton();
                   readPropertyFile();
            }
        }
        return uniqInstance;
    }

    private static void readPropertyFile() {
        prop = new Properties();
        input = null;

        try {
            String filename = "/Users/nathanapps/Documents/Clients/Channel Ports/CHPBarrierV2/CHPV2SDcard/config.properties";
            //String filename = "/home/pi/Desktop/CHPV2SDcard/config.properties";
            //String filename = "/home/pi/CHPV2SDcard/config.properties";
            input = new FileInputStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value
            BarrierType = prop.getProperty("BARRIERTYPE");
            ServerIP = prop.getProperty("SERVERIP");
            EntPort = Integer.parseInt(prop.getProperty("ENTPORT"));
            ExtPort =  Integer.parseInt(prop.getProperty("EXTPORT"));
            TrafficIPENT = prop.getProperty("TRAFFICLIGHTENT");
            TrafficPortEnt =  Integer.parseInt(prop.getProperty("TRAFFICLIGHTENTPORT"));
            TrafficIPEXT = prop.getProperty("TRAFFICLIGHTEXT");
            TrafficPortExt =  Integer.parseInt(prop.getProperty("TRAFFICLIGHTEXTPORT"));
            EntVid = prop.getProperty("ENTVID");
            MoveVid = prop.getProperty("MOVEVID");
            ExitVid = prop.getProperty("EXITVID");
            AlertVid = prop.getProperty("ALERTVID");
            KeepAlivePort =  Integer.parseInt(prop.getProperty("KEEPALIVEPORT"));
            RegImg = prop.getProperty("REGPLATEIMG");
            vidTimer = Integer.parseInt(prop.getProperty("VIDTIMER"));
            keyTimer = Integer.parseInt(prop.getProperty("KEYTIMER"));
            trafficTimer = Integer.parseInt(prop.getProperty("TRAFFICTIMER"));
            KeyPos = prop.getProperty("KEYPOS");
            ImagePath = prop.getProperty("IMAGE_PATH");
            anprHttpPort = Integer.parseInt(prop.getProperty("ANPR_HTTP_PORT"));
            StillCameraUsername = prop.getProperty("STILL_CAMERA_USERNAME");
            StillCameraPassword = prop.getProperty("STILL_CAMERA_PASSWORD");
            StillCameraURL = prop.getProperty("STILL_CAMERA_URL");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBarrierType(){
        return BarrierType;
    }
    public String getENTVid(){
        return EntVid;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getMoveVid(){
        return MoveVid;
    }

    public String getExtVid() {
        return ExitVid;
    }

    public String getAlertVid(){
        return AlertVid;
    }

    public String getServerIP(){
        return ServerIP;
    }

    public String getTrafficIPENT(){
        return TrafficIPENT;
    }

    public int getTrafficPortEnt(){
        return TrafficPortEnt;
    }

    public int getAnprHttpPort() {return anprHttpPort;}
    public int getEntPort(){
        return EntPort;
    }

    public int getExtPort(){
        return ExtPort;
    }

    public String getStillCameraUsername() {return StillCameraUsername;}

    public String getStillCameraPassword() {return StillCameraPassword;}

    public String getStillCameraURL() {return StillCameraURL;}

    public int getKeepAlivePort(){
        return KeepAlivePort;
    }

    public String getRegImg(){
        return RegImg;
    }

    public String getTrafficIPEXT() {
        return TrafficIPEXT;
    }

    public int getTrafficPortExt() {
        return TrafficPortExt;
    }

    public int getVidTimer(){
        return vidTimer;
    }

    public int getKeyTimer() {
        return keyTimer;
    }

    public  String getKeyPos(){
        return KeyPos;
    }

    public int getTrafficTimer(){
        return trafficTimer;
    }
}
