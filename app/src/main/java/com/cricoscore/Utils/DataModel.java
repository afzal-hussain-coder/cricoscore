package com.cricoscore.Utils;

public class DataModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private String Id;

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    private int idd;

    public DataModel(String name) {
        this.name = name;
    }

    public DataModel(String name, String Id) {
        this.name = name;
        this.Id = Id;
    }

    public DataModel(String name, int Id) {
        this.name = name;
        this.idd = Id;
    }

}
