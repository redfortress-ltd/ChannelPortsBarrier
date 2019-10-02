package Http;

import Session.GateSession;
import Singleton.InfoSingleton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AnprHttpHandler implements HttpHandler {
    private InfoSingleton info;

    public AnprHttpHandler() {
        info = new InfoSingleton().getInstance();
    }


    public void handle(final HttpExchange t) throws IOException {
        DiskFileItemFactory d = new DiskFileItemFactory();

        try {
            ServletFileUpload up = new ServletFileUpload(d);
            List<FileItem> result = up.parseRequest(new RequestContext() {
                public String getCharacterEncoding(){
                    return "UTF-8";
                }
                public int getContentLength() { return 0; }
                public String getContentType() { return t.getRequestHeaders().getFirst("Content-type"); }

                public InputStream getInputStream() throws IOException {
                    return t.getRequestBody();
                }
            });

            t.getResponseHeaders().add("Content-type", "text/plain");
            t.sendResponseHeaders(200, 0L);

            String anprImageName = "AN_" + UUID.randomUUID().toString() + ".png";
            String registration = null;
            String confidence = null;
            for (FileItem fi : result) {
                System.out.println("File-Item: " + fi.getFieldName());
                if (fi.getFieldName().equalsIgnoreCase("event")) {
                    JsonParser parser = new JsonParser();
                    JsonElement jsonTree = parser.parse(fi.getString());
                    JsonObject jsonObject = jsonTree.getAsJsonObject();
                    registration = jsonObject.get("plateUTF8").getAsString();
                    BigDecimal bd = jsonObject.get("plateConfidence").getAsBigDecimal();
                    bd = bd.multiply(new BigDecimal(100));
                    confidence = bd.intValue() + ""; continue;
                }  if (fi.getFieldName().equalsIgnoreCase("image")) {
                    fi.write(new File(info.getImagePath() + anprImageName));
                }
            }

            GateSession.createSession(anprImageName, registration, confidence);

            OutputStream os = t.getResponseBody();
            os.write("".getBytes());
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
