package com.example.projektfinal;

public class Fixture {
    public int id;
    public String name;
    public int address;
    public String mode;
    public String description;

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
