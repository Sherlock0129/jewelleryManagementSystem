package entity;

import tool.MyDoubleLinkedList;
import java.io.Serializable;

public class DisplayCase implements Serializable {

    private String id;

    private String type;

    private boolean isLightOn;

    private MyDoubleLinkedList<DisplayTray> trays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public MyDoubleLinkedList<DisplayTray> getTrays() {
        return trays;
    }

    public void setTrays(MyDoubleLinkedList<DisplayTray> trays) {
        this.trays = trays;
    }

    @Override
    public String toString() {
        return "DisplayCase{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", isLightOn=" + isLightOn +
                '}';
    }
}
