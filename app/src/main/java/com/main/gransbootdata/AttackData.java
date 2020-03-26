package com.main.gransbootdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class AttackData extends AppCompatActivity {//Activity for each character's data, follows the same structure as MainActivity. One recyclerview which fills itself with data from the character's xml

    private RecyclerView _rv;
    ArrayList<Data> _data;
    private String _name;
    private boolean _simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._simple = getIntent().getBooleanExtra("simple",false);
        if(_simple) {
            setContentView(R.layout.simple_bar);
            this._rv = findViewById(R.id.simpleatrv);
            Toolbar toolbar = findViewById(R.id.simple_attack_toolbar);
            setSupportActionBar(toolbar);
        }else{
            setContentView(R.layout.activity_attack_data);
            this._rv = findViewById(R.id.atrv);
            Toolbar toolbar = findViewById(R.id.attack_toolbar);
            setSupportActionBar(toolbar);
        }
        this._name = getIntent().getStringExtra("name");
        this.setTitle(_name);
        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.simple_mode){
            toggle_display();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("simple",this._simple);
        setResult(RESULT_OK,resultIntent);
        super.onBackPressed();
    }

    private void toggle_display(){
        this._simple ^= true;
        String t ="";
        if(this._simple){
            t = "Simple Mode activated";
            setContentView(R.layout.simple_bar);
            this.setTitle(_name);
            this._rv = findViewById(R.id.simpleatrv);
            Toolbar toolbar = findViewById(R.id.simple_attack_toolbar);
            setSupportActionBar(toolbar);
        }else{
            t = "Back to Complete Mode";
            setContentView(R.layout.activity_attack_data);
            this.setTitle(_name);
            this._rv = findViewById(R.id.atrv);
            Toolbar toolbar = findViewById(R.id.attack_toolbar);
            setSupportActionBar(toolbar);
        }
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
        setAdapter();
    }
    private void setAdapter() {//Leer xml
        _data = new ArrayList<Data>();
        insertXML();
        RecyclerAdapterAttack ra = new RecyclerAdapterAttack(_data, _name, this,this._simple);
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
