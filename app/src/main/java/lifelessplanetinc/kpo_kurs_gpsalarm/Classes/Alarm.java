package lifelessplanetinc.kpo_kurs_gpsalarm.Classes;

/**
 * Created by Andrew on 01.11.2016.
 */

public class Alarm {
    private String title;
    private String destination;
    private boolean isActive;

    public Alarm(String title, String destination, boolean isActive) {
        this.title = title;
        this.destination = destination;
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
