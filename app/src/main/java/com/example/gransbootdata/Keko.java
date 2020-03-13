package com.example.gransbootdata;

public class Keko {
    private String _name;
    private String _imagename;

    public Keko(String _name) {
        this._name = _name;
        this._imagename = _name.toLowerCase();
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_imagename() {
        return _imagename;
    }

    public void set_imagename(String _imagename) {
        this._imagename = _imagename;
    }
}
