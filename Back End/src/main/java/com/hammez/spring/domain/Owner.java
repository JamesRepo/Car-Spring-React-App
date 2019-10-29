package com.hammez.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Owner {

// ************************************************************************\
// Static Variables                                                        *
// ************************************************************************/

// ************************************************************************\
// Instance Variables                                                      *
// ************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ownerId;

    private String firstName;
    private String lastName;

    // Cascade defines how cascading affects entities.  ALL means if an owner is deleted, so are all the cars
    // Mapped By means the Car class has the owner field which is the foreign key
    // If using a Many To Many relationship, should use a Set instead of List
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonIgnore
    private List<Car> cars;

// ************************************************************************\
// Constructors                                                            *
// ************************************************************************/

    public Owner() {}

    public Owner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

// ************************************************************************\
// Public Methods                                                          *
// ************************************************************************/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
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