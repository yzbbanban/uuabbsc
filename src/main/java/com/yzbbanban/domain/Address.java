package com.yzbbanban.domain;

/**
 * Created by ban on 2018/7/6.
 */
public class Address {
    private Integer id;
    private String street;
    private String Contry;
    private String area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getContry() {
        return Contry;
    }

    public void setContry(String contry) {
        Contry = contry;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
