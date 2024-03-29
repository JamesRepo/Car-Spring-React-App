package com.hammez.spring.domain;

import javax.persistence.*;

@Entity
public class Car {

// ************************************************************************\
// Static Variables                                                        *
// ************************************************************************/

// ************************************************************************\
// Instance Variables                                                      *
// ************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String brand;
    private String model;
    private String color;
    private String registerNumber;

    private int year;
    private int price;

    // Fetch type defines strategy to get data. Lazy means all cars associated will be fetched
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private Owner owner;

// ************************************************************************\
// Constructors                                                            *
// ************************************************************************/

    public Car() {}

    public Car(String brand, String model, String color, String registerNumber, int year, int price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
    }

    public Car(Owner owner, String brand, String model, String color, String registerNumber, int year, int price) {
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
    }

// ************************************************************************\
// Public Methods                                                          *
// ************************************************************************/

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

// ************************************************************************\
// Protected Methods                                                       *
// ************************************************************************/

// ************************************************************************\
// Private Methods                                                         *
// ************************************************************************/

// ************************************************************************\
// Inner Classes                                                           *
// ************************************************************************/

// ************************************************************************\
// Static Methods                                                          *
// ************************************************************************/

}