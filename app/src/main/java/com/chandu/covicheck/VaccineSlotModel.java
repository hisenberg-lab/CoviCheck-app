package com.chandu.covicheck;

import java.util.List;

public class VaccineSlotModel {

    private int center_id;
    private String name;
    private String address;
    private String state_name;
    private String district_name;
    private String block_name;
    private int pincode;
    private String from;
    private String to;
    private int lat;
    private int longi;
    private String fee_type;
    private String session_id;
    private String date;
    private int available_capacity;
    private int available_capacity_dose1;
    private int available_capacity_dose2;
    private String fee;
    private int min_age_limit;
    private int max_age_limit;
    private String allow_all_age;
    private String vaccine;
    private List<String> slots;

    public VaccineSlotModel(int center_id, String name, String address, String state_name, String district_name, String block_name, int pincode, String from, String to, int lat, int longi, String fee_type, String session_id, String date, int available_capacity, int available_capacity_dose1, int available_capacity_dose2, String fee, int min_age_limit, int max_age_limit, String allow_all_age, String vaccine, List<String> slots) {
        this.center_id = center_id;
        this.name = name;
        this.address = address;
        this.state_name = state_name;
        this.district_name = district_name;
        this.block_name = block_name;
        this.pincode = pincode;
        this.from = from;
        this.to = to;
        this.lat = lat;
        this.longi = longi;
        this.fee_type = fee_type;
        this.session_id = session_id;
        this.date = date;
        this.available_capacity = available_capacity;
        this.available_capacity_dose1 = available_capacity_dose1;
        this.available_capacity_dose2 = available_capacity_dose2;
        this.fee = fee;
        this.min_age_limit = min_age_limit;
        this.max_age_limit = max_age_limit;
        this.allow_all_age = allow_all_age;
        this.vaccine = vaccine;
        this.slots = slots;
    }

    public VaccineSlotModel() {
    }

    @Override
    public String toString() {
        return "VaccineSlotModel{" +
                "center_id=" + center_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", state_name='" + state_name + '\'' +
                ", district_name='" + district_name + '\'' +
                ", block_name='" + block_name + '\'' +
                ", pincode='" + pincode + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", lat='" + lat + '\'' +
                ", longi='" + longi + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", session_id='" + session_id + '\'' +
                ", date='" + date + '\'' +
                ", available_capacity='" + available_capacity + '\'' +
                ", available_capacity_dose1='" + available_capacity_dose1 + '\'' +
                ", available_capacity_dose2='" + available_capacity_dose2 + '\'' +
                ", fee='" + fee + '\'' +
                ", min_age_limit='" + min_age_limit + '\'' +
                ", max_age_limit='" + max_age_limit + '\'' +
                ", allow_all_age='" + allow_all_age + '\'' +
                ", vaccine='" + vaccine + '\'' +
                ", slots=" + slots +
                '}';
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLongi() {
        return longi;
    }

    public void setLongi(int longi) {
        this.longi = longi;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(int available_capacity) {
        this.available_capacity = available_capacity;
    }

    public int getAvailable_capacity_dose1() {
        return available_capacity_dose1;
    }

    public void setAvailable_capacity_dose1(int available_capacity_dose1) {
        this.available_capacity_dose1 = available_capacity_dose1;
    }

    public int getAvailable_capacity_dose2() {
        return available_capacity_dose2;
    }

    public void setAvailable_capacity_dose2(int available_capacity_dose2) {
        this.available_capacity_dose2 = available_capacity_dose2;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(int min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public int getMax_age_limit() {
        return max_age_limit;
    }

    public void setMax_age_limit(int max_age_limit) {
        this.max_age_limit = max_age_limit;
    }

    public String getAllow_all_age() {
        return allow_all_age;
    }

    public void setAllow_all_age(String allow_all_age) {
        this.allow_all_age = allow_all_age;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }
}
