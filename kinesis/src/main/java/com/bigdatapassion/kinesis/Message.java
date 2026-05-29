package com.bigdatapassion.kinesis;

public class Message {

    private String partitionkey;
    private String currentdate;
    private String name;
    private String phonenumber;
    private String streetname;
    private String number;
    private String city;
    private String country;
    private String animal;

    public String getPartitionkey() {
        return partitionkey;
    }

    public void setPartitionkey(String partitionkey) {
        this.partitionkey = partitionkey;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAnimal() {
        return animal;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
