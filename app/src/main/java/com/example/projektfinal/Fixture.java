package com.example.projektfinal;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Fixture implements Serializable {

    public boolean id;
    public String name;
    public int address;
    public int channels;
    public String mode;
    public List<String> attributesList;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public List<String> getAttributesList() {
        return attributesList;
    }

    public Fixture(String name, String mode, List<String> attributesList){
        this.name = name;
        this.mode = mode;
        this.attributesList = attributesList;
    }

    public Fixture(String name, int address, String mode, int channels, List<String> attributesList) {
        this.name = name;
        this.address = address;
        this.mode = mode;
        this.channels = channels;
        this.attributesList = attributesList;
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

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "Nazwa urządzenia:" + name + '\'' +
                        ", adres: " + address +
                        ", mode: " + mode + '\'' +
                        ", liczba kanałów: " + channels
                ;
    }
}
