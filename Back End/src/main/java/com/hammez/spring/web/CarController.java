package com.hammez.spring.web;

import com.hammez.spring.domain.Car;
import com.hammez.spring.domain.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

// ************************************************************************\
// Static Variables                                                        *
// ************************************************************************/

// ************************************************************************\
// Instance Variables                                                      *
// ************************************************************************/

    @Autowired
    private CarRepository carRepository;

// ************************************************************************\
// Constructors                                                            *
// ************************************************************************/

// ************************************************************************\
// Public Methods                                                          *
// ************************************************************************/

    // Request mapping defines the end point that this method is mapped to.
    // By default handles all REST methods. Can specify with : method = GET
    @RequestMapping("/cars")
    public Iterable<Car> getCars() {
        return carRepository.findAll();
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