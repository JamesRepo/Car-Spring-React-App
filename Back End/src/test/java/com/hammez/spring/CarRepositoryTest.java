package com.hammez.spring;

import com.hammez.spring.domain.Car;
import com.hammez.spring.domain.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;


    @Test
    public void saveCar() {
        Car car = new Car("Jaguar", "F-Type", "Red", "FFF-123", 2017, 97000);
        entityManager.persistAndFlush(car);
        assertThat(car.getId()).isNotNull();
    }

    @Test
    public void deleteCars() {
        entityManager.persistAndFlush(new Car("Jaguar", "F-Type", "Red", "FFF-123", 2017, 97000));
        entityManager.persistAndFlush(new Car("Mini", "Cooper", "Yellow", "HHH-777", 2015, 12000));
        carRepository.deleteAll();
        assertThat(carRepository.findAll()).isEmpty();
    }

}