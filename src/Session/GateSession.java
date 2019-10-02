package Session;

import java.io.Serializable;

public class GateSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private static GateSession uniqInstance;
    private String anprImageName;
    private String registration;
    private String confidence;

    public static GateSession getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new GateSession();
        }
        return uniqInstance;
    }


    public static void createSession(String anprImageName, String registration, String confidence) {
        synchronized (uniqInstance) {
            uniqInstance = new GateSession();
            uniqInstance.setAnprImageName(anprImageName);
            uniqInstance.setConfidence(confidence);
            uniqInstance.setRegistration(registration);
        }
    }
    public String getAnprImageName() { return this.anprImageName; }



    public void setAnprImageName(String anprImageName) { this.anprImageName = anprImageName; }



    public String getRegistration() { return this.registration; }



    public void setRegistration(String registration) { this.registration = registration; }



    public String getConfidence() { return this.confidence; }



    public void setConfidence(String confidence) { this.confidence = confidence; }
}
