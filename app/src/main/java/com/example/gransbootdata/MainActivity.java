package com.example.gransbootdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener {

    // private MyDB _db;//Maybe i'll use it?
    private RecyclerView _rv;
    ArrayList<Keko> _kekos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._rv = findViewById(R.id.rv);
        setAdapter();
    }

    private void setAdapter() {//Adapter for the RecylerView, get all the data from database and create new Data objects
        _kekos = new ArrayList<Keko>();
        getXML();
        RecyclerAdapter ra = new RecyclerAdapter(_kekos, this, this);
        _rv.setAdapter(ra);
        _rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNoteClick(int position) {
        if(_kekos.get(position).get_name().equals("Gt")){
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=U46pFIo5zsg"));
            try {
                MainActivity.this.startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {
            }
        }else {
            Intent i = new Intent(this, AttackData.class);
            i.putExtra("name", _kekos.get(position).get_name());
            startActivity(i);
        }
    }

    private void getXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("kekos.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            //Process Parsing
            Keko k = null;
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {//this method works
                String entry = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        entry = parser.getName();
                        if (entry.equals("name")){
                            _kekos.add(new Keko(parser.nextText()));
                        }

                break;
            }
            eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
