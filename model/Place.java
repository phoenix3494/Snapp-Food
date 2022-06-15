package model;

import java.io.Serializable;

public class Place implements Serializable {

    protected String placeName;
    protected String Address;

    public Place(String placeName, String address) {
        this.placeName = placeName;
        Address = address;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

}
