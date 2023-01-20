package com.example.projektfinal;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fixture implements Serializable {

    public boolean id;
    public String name;
    public int address;
    public int channels;
    public String mode;

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


    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public List<String> getAttributesList() {
        return attributesList;
    }

    public void setAttributesList(List<String> attributesList) {
        this.attributesList.addAll(attributesList);
    }

    public Fixture(String name, String mode, List<String> attributesList){
        this.name = name;
        this.mode = mode;
        this.attributesList = attributesList;
    }

    public Fixture(String name, int address, String mode, int channels) {
        this.name = name;
        this.address = address;
        this.mode = mode;
        this.channels = channels;
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
