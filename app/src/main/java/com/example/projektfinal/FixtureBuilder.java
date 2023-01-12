package com.example.projektfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.util.Xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class FixtureBuilder extends AppCompatActivity {

    public int attributesQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_builder);

    }

    public void createFixture(Fixture fixture) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            //Root
            //<Fixture Name, ShortName, Company, Version>
            Element root = document.createElement("Fixture");
            document.appendChild(root);
            root.setAttribute("Name", fixture.name);
            root.setAttribute("Shortname", fixture.name);
            root.setAttribute("Company", "AAFixtureBuilder");
            root.setAttribute("Version", "12");

            //<Copyright notice>
            Element rootChild =  document.createElement("Copyright");
            root.appendChild(rootChild);
            rootChild.setAttribute("Notice", "(C) MK Lab3 2023");

            //<Manual Filename, Summary>
            rootChild = document.createElement("Manual");
            root.appendChild(rootChild);
            root.setAttribute("Filename", "");
            root.setAttribute("Summary", "");

            //<Control>

            rootChild = document.createElement("Control");

            for (int i = 0; i < attributesQuantity; i++) {
                Element controlChild = document.createElement("Attribute");
                controlChild.setAttribute("ID", "");
                controlChild.setAttribute("Name", ""); //Tutaj getText z textView
                controlChild.setAttribute("Description", "");
                switch (true) {
                    case "Dimmer": controlChild.setAttribute("Group", "I");
                    case "Shutter": controlChild.setAttribute("Group", "I"); //switch albo enum
                    case "Generic": controlChild.setAttribute("Group", );
                }
            }

            Element fixtureAttributes = document.createElement()


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}