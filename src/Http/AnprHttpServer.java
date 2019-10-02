package Http;

import Singleton.InfoSingleton;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;






public class AnprHttpServer
{
    private HttpServer server;
    private InfoSingleton info;

    public AnprHttpServer() {
        info = new InfoSingleton().getInstance();
    }

    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(info.getAnprHttpPort()), 0);
            this.server.createContext("/", new AnprHttpHandler());
            this.server.setExecutor(Executors.newCachedThreadPool());
            this.server.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {}
    }
}
