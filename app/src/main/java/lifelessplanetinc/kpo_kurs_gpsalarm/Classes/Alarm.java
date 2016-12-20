package lifelessplanetinc.kpo_kurs_gpsalarm.Classes;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Andrew on 01.11.2016.
 */

public class Alarm {
    private String title;
    private String destination;
    private LatLng dest_coords;
    private boolean isActive;

    public Alarm(String title, String destination, LatLng coords, boolean isActive) {
        this.title = title;
        this.destination = destination;
        this.dest_coords = coords;
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

    public LatLng getDest_coords() {
        return dest_coords;
    }

    public void setDest_coords(LatLng dest_coords) {
        this.dest_coords = dest_coords;
    }
}
