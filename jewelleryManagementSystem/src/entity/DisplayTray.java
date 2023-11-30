package entity;

import tool.MyDoubleLinkedList;
import java.io.Serializable;


public class DisplayTray implements Serializable {

    private String id;

    private String color;

    private double width;

    private double height;

    private MyDoubleLinkedList<JewelryItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public MyDoubleLinkedList<JewelryItem> getItems() {
        return items;
    }

    public void setItems(MyDoubleLinkedList<JewelryItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "DisplayTray{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", width=" + width +
                ", height=" + height +
                "\n"+
                '}';
    }
}
