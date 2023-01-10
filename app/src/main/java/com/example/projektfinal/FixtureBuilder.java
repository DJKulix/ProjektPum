package com.example.projektfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;


public class FixtureBuilder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_builder);
    }

    public void createFixture(){
        File fixture = new File("/data/new.xml");
        try{
            fixture.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        XmlSerializer serializer = Xml.newSerializer();
//        try{
//
//        }
    }
}