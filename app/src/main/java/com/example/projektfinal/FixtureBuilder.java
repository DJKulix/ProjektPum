package com.example.projektfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class FixtureBuilder extends AppCompatActivity {

    public static List<String> tempAttrList = new ArrayList<>();
    public TextView attrListTV;

    public static void addAttr(String attr) {
        tempAttrList.add(attr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        attrListTV.setText(tempAttrList.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_builder);

        attrListTV = findViewById(R.id.attrListTV);
        EditText fixtureNameTV = findViewById(R.id.nameTextInput);
        EditText fixtureModeTV = findViewById(R.id.modeTextInput);
        Button addAttributeButton = findViewById(R.id.addAttributeButton);
        Button saveButton = findViewById(R.id.saveFixtureButton);


        addAttributeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FixtureBuilder.this, Pop.class));

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fixture fixture = new Fixture(fixtureNameTV.getText().toString(), fixtureModeTV.getText().toString(), tempAttrList);
//                showMessage(fixture.toString());
//                showMessage(tempAttrList.toString());
                createFixture(fixture);
            }
        });

    }

    public void showMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
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
            root.setAttribute("Name", fixture.getName());
            root.setAttribute("Shortname", fixture.getName());
            root.setAttribute("Company", "AAFixtureBuilder");
            root.setAttribute("Version", "1");

            //<Copyright notice>
            Element rootChild = document.createElement("Copyright");
            root.appendChild(rootChild);
            rootChild.setAttribute("Notice", "(C) MK Lab3 2023");

            //<Manual Filename, Summary> PUSTE
            rootChild = document.createElement("Manual");
            root.appendChild(rootChild);
            root.setAttribute("Filename", "manual.txt");
            root.setAttribute("Summary", "summary.txt");

            //<Control> --> <Attrbiutes> --> <Locate> + <Function>

            Element attributeChild;

            rootChild = document.createElement("Control");
            root.appendChild(rootChild);

            Iterator<String> attrIterator = fixture.attributesList.iterator();
            while (attrIterator.hasNext()) {
                Element controlChild = document.createElement("Attribute");
                String attrName = attrIterator.next();
                controlChild.setAttribute("ID", attrName);
                controlChild.setAttribute("Name", attrName);
                controlChild.setAttribute("Description", "Fixture stworzony w aplikacji");
                switch (attrName) {
                    //<Attribute>
                    case "Dimmer":
                        controlChild.setAttribute("Group", "I");

                        //<Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:0");

                        //<Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Dimmer");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~65535");

                    case "Shutter":
                        controlChild.setAttribute("Group", "I");

                        //<Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:0");
                        attributeChild.setAttribute("PowerOn", "1:0");

                        //<Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Strobe HZ");
                        attributeChild.setAttribute("Display", "'Strobe %.f Hz',0~50");
                        attributeChild.setAttribute("DMX", "0~255");

                    case "Red":
                        controlChild.setAttribute("Group", "C");

                        //<Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:100");
                        attributeChild.setAttribute("Highlight", "1:0");
                        attributeChild.setAttribute("Lowlight", "1:0");

                        //<Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Red C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~255");
                        attributeChild.setAttribute("Colour", "255, 0, 0");

                    case "Green":
                        controlChild.setAttribute("Group", "C");

                        //<Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:100");
                        attributeChild.setAttribute("Highlight", "1:0");
                        attributeChild.setAttribute("Lowlight", "1:0");

//                        <Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Green C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~255");
                        attributeChild.setAttribute("Colour", "0, 255, 0");

                    case "Blue":
                        controlChild.setAttribute("Group", "C");

//                        <Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:100");
                        attributeChild.setAttribute("Highlight", "1:0");
                        attributeChild.setAttribute("Lowlight", "1:0");

//                        <Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Blue C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~255");
                        attributeChild.setAttribute("Colour", "0, 0, 255");

                    case "White":
                        controlChild.setAttribute("Group", "S");

//                        <Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:100");
                        attributeChild.setAttribute("Highlight", "1:0");
                        attributeChild.setAttribute("Lowlight", "1:0");

//                        <Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "White C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~255");
                        attributeChild.setAttribute("Colour", "255, 255, 255");

                    case "Amber":
                        controlChild.setAttribute("Group", "S");

//                        <Locate>
                        attributeChild = document.createElement("Locate");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("Locate", "1:100");
                        attributeChild.setAttribute("PowerOn", "1:100");
                        attributeChild.setAttribute("Highlight", "1:0");
                        attributeChild.setAttribute("Lowlight", "1:0");

//                        <Function>
                        attributeChild = document.createElement("Function");
                        controlChild.appendChild(attributeChild);
                        attributeChild.setAttribute("ID", "1");
                        attributeChild.setAttribute("Name", "Amber C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("DMX", "0~255");
                        attributeChild.setAttribute("Colour", "255, 100, 0");

//                        </Attribute>
                }
            }

//          <Mode>
            rootChild = document.createElement("Mode");

            root.appendChild(rootChild);

            rootChild.setAttribute("Name", fixture.getMode());
            rootChild.setAttribute("Channels", String.valueOf(fixture.attributesList.size()));

//              <Import>
            Element modePaletteChild = document.createElement("Import");

            rootChild.appendChild(modePaletteChild);

            modePaletteChild.setAttribute("PearlRef", "");
            modePaletteChild.setAttribute("DiamondRef", "");
            modePaletteChild.setAttribute("WysiwygRef", "");

//              </Import>
//              <Physical>
            modePaletteChild = document.createElement("Physical");
            rootChild.appendChild(modePaletteChild);
//              </Physical>

//              <Include>
            modePaletteChild = document.createElement("Include");
            rootChild.appendChild(modePaletteChild);
            attrIterator = fixture.attributesList.iterator();
            int i = 7;
            while (attrIterator.hasNext()) {

                String attrName = attrIterator.next();
                Element includeChild = document.createElement("Attribute");
                modePaletteChild.appendChild(includeChild);

                includeChild.setAttribute("ID", attrName);
                includeChild.setAttribute("ChannelOffset", String.valueOf(i));

                if (attrName.equals("Dimmer")){
                    includeChild.setAttribute("Wheel", String.valueOf(4));
                }
                if (attrName.equals("Shutter")){
                    includeChild.setAttribute("Wheel", String.valueOf(5));
                }
                else {
                    includeChild.setAttribute("Wheel", String.valueOf(i));
                    i++;
                }
            }
//              </Include>
//            </Mode>



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);


            String fileName = fixture.getName() + "MobileFB.d4";
            StreamResult streamResult = new StreamResult(new File(Environment.getExternalStorageDirectory(), fileName));

            transformer.transform(domSource, streamResult);

            showMessage("Zapisano do pliku" + fileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}