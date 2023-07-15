package ee.katrina.webshop.dto;

import java.util.ArrayList;

@lombok.Data
public class Nordpool {
    public boolean success;
    public Data data;
}

@lombok.Data
class Data{
    public ArrayList<TimestampPrice> ee;
    public ArrayList<TimestampPrice> fi;
    public ArrayList<TimestampPrice> lv;
    public ArrayList<TimestampPrice> lt;
}

@lombok.Data
class TimestampPrice{
    public int timestamp;
    public double price;
}