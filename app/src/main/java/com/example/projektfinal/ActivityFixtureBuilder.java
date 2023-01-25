package com.example.projektfinal;

import static com.example.projektfinal.MainActivity.fixtureList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


public class ActivityFixtureBuilder extends AppCompatActivity {

    public static List<String> tempAttrList;
    public TextView attrListTV;

    EditText fixtureNameTV;
    EditText fixtureModeTV;
    EditText fixtureAddressTV;
    EditText fixtureChannelsTV;
    TextView fixtureAttributesTV;
    Button addAttributeButton;
    Button buildFixtureButton;
    Button saveFixtureButton;
    Button deleteAttributesButton;

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

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.about);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.dashboard:
                    startActivity(new Intent(getApplicationContext(), ActivityFixtureList.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.about:
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        tempAttrList = new ArrayList<>();
        attrListTV = findViewById(R.id.attrListTV);
        fixtureNameTV = findViewById(R.id.nameTIBuilder);
        fixtureModeTV = findViewById(R.id.modeTIBuilder);
        fixtureAddressTV = findViewById(R.id.addressTIBuilder);
        fixtureChannelsTV = findViewById(R.id.channelsTIBuilder);
        fixtureAttributesTV = findViewById(R.id.attrListTV);
        addAttributeButton = findViewById(R.id.addAttributeButton);
        buildFixtureButton = findViewById(R.id.buildFixtureButton);
        saveFixtureButton = findViewById(R.id.saveFixtureButton);
        deleteAttributesButton = findViewById(R.id.deleteAttrBtn);

        deleteAttributesButton.setOnClickListener(view -> {
            int i = (int) getIntent().getSerializableExtra("ID");
            tempAttrList.clear();
            fixtureAttributesTV.setText(tempAttrList.toString());
            fixtureList.get(i).attributesList.clear();

        });

        Fixture tempFixture = (Fixture) getIntent().getSerializableExtra("KEY_NAME");
        if (tempFixture != null) {
            fixtureNameTV.setText(tempFixture.getName());
            fixtureModeTV.setText(tempFixture.getMode());
            fixtureAddressTV.setText(String.valueOf(tempFixture.getAddress()));
            fixtureChannelsTV.setText(String.valueOf(tempFixture.getChannels()));
            tempAttrList.addAll(tempFixture.getAttributesList());
            fixtureAttributesTV.setText(tempAttrList.toString());

        }

        addAttributeButton.setOnClickListener(view -> startActivity(new Intent(ActivityFixtureBuilder.this, PopUpWindow.class)));

        buildFixtureButton.setOnClickListener(view -> {
            if (checkAllFields()) {
                Fixture fixture = new Fixture(fixtureNameTV.getText().toString(), fixtureModeTV.getText().toString(), tempAttrList);
                createFixture(fixture);
            }
        });

        saveFixtureButton.setOnClickListener(view ->{
                if(checkAllFields())
                 saveFixture(tempFixture);
        });
    }

    private boolean checkAllFields() {
        if (fixtureNameTV.length() == 0) {
            fixtureNameTV.setError("Podaj nazwę urządzenia");
            return false;
        }

        if (fixtureAddressTV.length() == 0 || Integer.parseInt(fixtureAddressTV.getText().toString()) > 512) {
            fixtureAddressTV.setError("Podaj poprawny adres DMX");
            return false;
        }

        if (fixtureChannelsTV.length() == 0 || Integer.parseInt(fixtureChannelsTV.getText().toString()) > 512) {
            fixtureChannelsTV.setError("Podaj poprawną liczbę kanałów");
            return false;
        }

        if (fixtureModeTV.length() == 0) {
            fixtureModeTV.setError("Podaj nazwę trybu");
            return false;
        }
        if (tempAttrList.isEmpty()) {
            fixtureAttributesTV.setError("Podaj co najmniej 1 atrybut");
            return false;
        }
        if (tempAttrList.size() != Integer.parseInt(fixtureChannelsTV.getText().toString())) {
            showMessage("Liczba atrybutów musi równać się liczbie kanałów");
            return false;
        }

        // after all validation return true.
        return true;
    }

    public void saveFixture(Fixture fixture) {

        if (fixture == null) {
            fixture = new Fixture(
                    fixtureNameTV.getText().toString(),
                    Integer.parseInt(fixtureChannelsTV.getText().toString()),
                    fixtureModeTV.getText().toString(),
                    Integer.parseInt(fixtureAddressTV.getText().toString()),
                    tempAttrList
            );
            fixtureList.add(fixture);
        } else {
            int i = (int) getIntent().getSerializableExtra("ID");
            showMessage(String.valueOf(i));
            fixtureList.get(i).setName(fixtureNameTV.getText().toString());
            fixtureList.get(i).setChannels(Integer.parseInt(fixtureChannelsTV.getText().toString()));
            fixtureList.get(i).setMode(fixtureModeTV.getText().toString());
            fixtureList.get(i).setAddress(Integer.parseInt(fixtureAddressTV.getText().toString()));

            if (fixtureList.get(i).attributesList.size() < tempAttrList.size())
                fixtureList.get(i).attributesList.addAll(tempAttrList);

        }
        tempAttrList.clear();

        finish();
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

            //<Fixture Name, ShortName, Company, Version>
            Element root = document.createElement("Fixture");
            document.appendChild(root);
            String fixtureName = fixture.getName();
            root.setAttribute("Name", fixtureName);
            fixtureName = fixtureName.substring(0, Math.min(fixtureName.length(), 9));
            root.setAttribute("ShortName", fixtureName);
            root.setAttribute("Company", "11FixtureBuilder");
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
                controlChild.setAttribute("Description", "");
                controlChild.setAttribute("Size", "2");
                rootChild.appendChild(controlChild);
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
                        attributeChild.setAttribute("Display", "'%.2f%%',0.00~100.00");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        break;

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
                        attributeChild.setAttribute("Display", "'Strobe %.1f%%',0.0~100.0");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        break;

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
                        attributeChild.setAttribute("Display", "'%.2f%%',0.00~100.00");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        attributeChild.setAttribute("Colour", "255,0,0");
                        break;

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
                        attributeChild.setAttribute("Display", "'%.2f%%',0.00~100.00");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        attributeChild.setAttribute("Colour", "0,255,0");

                        break;
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
                        attributeChild.setAttribute("Display", "'%.2f%%',0.00~100.00");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        attributeChild.setAttribute("Colour", "0,0,255");
                        break;
                    case "White":
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
                        attributeChild.setAttribute("Name", "White C-Mix");
                        attributeChild.setAttribute("Display", "'%.2f%%',0.00~100.00");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        attributeChild.setAttribute("Colour", "255, 255, 255");
                        break;
                    case "Amber":
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
                        attributeChild.setAttribute("Name", "Amber C-Mix");
                        attributeChild.setAttribute("Display", "'%.1f%%',0.0~100.0");
                        attributeChild.setAttribute("Dmx", "0~65535");
                        attributeChild.setAttribute("Colour", "255, 100, 0");
                        break;
//                        </Attribute>
                }
            }

//          <Mode>
            rootChild = document.createElement("Mode");

            root.appendChild(rootChild);

            rootChild.setAttribute("Name", fixture.getMode());
            rootChild.setAttribute("Channels", String.valueOf(fixture.getChannels()));

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
            int i = 1;
            while (attrIterator.hasNext()) {

                String attrName = attrIterator.next();
                Element includeChild = document.createElement("Attribute");
                modePaletteChild.appendChild(includeChild);

                includeChild.setAttribute("ID", attrName);
                includeChild.setAttribute("ChannelOffset", String.valueOf(i));

                if (attrName.equals("Dimmer")) {
                    includeChild.setAttribute("Wheel", String.valueOf(1));
                }
                if (attrName.equals("Shutter")) {
                    includeChild.setAttribute("Wheel", String.valueOf(2));
                } else {
                    includeChild.setAttribute("Wheel", String.valueOf(i + 4));

                }
                i++;
            }
//              </Include>
//            </Mode>


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);


            String fileName = fixture.getName() + "MobileFB.d4";
            fileName = fileName.replaceAll(" ", "_");
            StreamResult streamResult = new StreamResult(new File(Environment.getExternalStorageDirectory(), fileName));

            transformer.transform(domSource, streamResult);

            showMessage("Zapisano do pliku" + fileName);

//            File externalFilesDirectory = this.getExternalFilesDir(String.valueOf(Environment.getExternalStorageDirectory()));/
            File file = new File (Environment.getExternalStorageDirectory() +"/" + fileName);
            sendEmail(file);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(File file) {
        Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.setType("vnd.android.cursor.dir/email");
        String[] to = {"mkula737@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Plik z kreatora");
        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }

}//Nawias konczacy klasę

