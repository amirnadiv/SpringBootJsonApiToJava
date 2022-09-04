package com.amirnadiv.xplg;


import java.util.ArrayList;

public class Feature{
    public String type;
    public Properties properties;
    public Geometry geometry;
    public String id;
}


 class Geometry{
    public String type;
    public ArrayList<Double> coordinates;
}

 class Properties{
    public double mag;
    public String place;
    public long time;
    public String dayMonthYear;
    public long updated;
    public Object tz;
    public String url;
    public String detail;
    public Object felt;
    public Object cdi;
    public Object mmi;
    public Object alert;
    public String status;
    public int tsunami;
    public int sig;
    public String net;
    public String code;
    public String ids;
    public String sources;
    public String types;
    public Object nst;
    public Object dmin;
    public double rms;
    public Object gap;
    public String magType;
    public String type;
    public String title;
}

