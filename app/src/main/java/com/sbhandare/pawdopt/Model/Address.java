package com.sbhandare.pawdopt.Model;

public class Address
{
    private int addressid;

    private String street1;

    private String street2;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    public void setAddressid(int addressid){
        this.addressid = addressid;
    }
    public int getAddressid(){
        return this.addressid;
    }
    public void setStreet1(String street1){
        this.street1 = street1;
    }
    public String getStreet1(){
        return this.street1;
    }
    public void setStreet2(String street2){
        this.street2 = street2;
    }
    public String getStreet2(){
        return this.street2;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    public String getZipCode(){
        return this.zipCode;
    }
}