package com.example.gransbootdata;

public class Data {//Object class for each attack's data
    private String _name, _input, _type, _start, _advb, _advhit, _desc,_damage;

    public Data() {
    }

    public Data(/*int _id,*/ String _name, String _input, String _type, String _start, String _advb, String _advhit, String desc, String damage) {
       // this._id = _id;
        this._name = _name;
        this._input = _input;
        this._type = _type;
        this._start = _start;
        this._advb = _advb;
        this._advhit = _advhit;
        this._desc = desc;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_input() {
        return _input;
    }

    public void set_input(String _input) {
        this._input = _input;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_start() {
        return _start;
    }

    public void set_start(String _start) {
        this._start = _start;
    }

    public String get_advb() {
        return _advb;
    }

    public void set_advb(String _advb) {
        this._advb = _advb;
    }

    public String get_advhit() {
        return _advhit;
    }

    public void set_advhit(String _advhit) {
        this._advhit = _advhit;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_damage() {
        return _damage;
    }

    public void set_damage(String _damage) {
        this._damage = _damage;
    }
}
