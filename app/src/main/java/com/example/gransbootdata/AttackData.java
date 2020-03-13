package com.example.gransbootdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AttackData extends AppCompatActivity {

    private RecyclerView _rv;
    ArrayList<Data> _data;
    private String _name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_data);
        this._name = getIntent().getStringExtra("name");
        this.setTitle(_name);
        this._rv = findViewById(R.id.atrv);
        setAdapter();

    }

    private void setAdapter() {//Leer xml
        _data = new ArrayList<Data>();
        insertXML();
        RecyclerAdapterAttack ra = new RecyclerAdapterAttack(_data, _name, this);
        _rv.setAdapter(ra);
        _rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void insertXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open(_name.toLowerCase() + ".xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            //Process Parsing
            Data d = null;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String entry = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        entry = parser.getName();

                        if (entry.equals("attack")) {
                            if (d != null) {
                                _data.add(d);
                            }
                            d = new Data();
                            d.set_input(
                                    parser.getAttributeValue(0));
                        } else if (d != null) {
                            if (entry.equals("name")) {
                                d.set_name(parser.nextText());
                            } else if (entry.equals("damage")) {
                                d.set_damage(parser.nextText());
                            } else if (entry.equals("type")) {
                                d.set_type(parser.nextText());
                            } else if (entry.equals("start")) {
                                d.set_start(parser.nextText());
                            } else if (entry.equals("advb")) {
                                d.set_advb(parser.nextText());
                            } else if (entry.equals("advh")) {
                                d.set_advhit(parser.nextText());
                            } else if (entry.equals("desc")) {
                                d.set_desc(parser.nextText());
                            }
                        }
                        break;
                }

                eventType = parser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            Toast.makeText(this, "No data of this character", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
