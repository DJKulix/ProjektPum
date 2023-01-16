package com.example.projektfinal;

import java.util.List;

public class Fixture {
    public int id;
    public String name;
    public int address;
    public String mode;
    public String description;
    public int x;
    public int y;
    public List<String> attributesList;

    /*
        Par LED SLIM Classic 6 DMX
        1. Dimmer
        2. Shutter
        3. Red
        4. Green
        5. Blue
        6. White

     */

    public List<String> getAttributesList() {
        return attributesList;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Fixture(String name, String mode, List<String> attributesList){
        this.name = name;
        this.mode = mode;
        this.attributesList = attributesList;
    }

    public Fixture(int id, String name, int address, String mode, String description, int x, int y) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mode = mode;
        this.description = description;
        this.x = x;
        this.y = y;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fixture(int id, String name, int address, String mode, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mode = mode;
        this.description = description;
    }

    public Fixture(String name, int address, String mode) {
        this.name = name;
        this.address = address;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return
                "Nazwa urządzenia:" + name + '\'' +
                        ", adres: " + address +
                        ", mode: " + mode + '\''
                ;
    }
}
