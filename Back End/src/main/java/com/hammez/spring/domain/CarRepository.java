package com.hammez.spring.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CarRepository extends CrudRepository <Car, Long> {

    // FIND BY is a keyword for finding stuff, prefix a method with it
    List<Car> findByColor(@Param("color") String color);

    // Create queries using SQL statements
    // @Query("SELECT c FROM Car c WHERE c.brand = ?1")

    List<Car> findByBrand(@Param("brand") String brand);

    @Query("SELECT c FROM Car c WHERE c.brand LIKE %?1")
    List<Car> findByBrandEndsWith(String brand);

    List<Car> findByYear(int year);

    // Can also use other keywords like AND / OR for multiple fields
    List<Car> findByBrandAndModel(String brand, String model);

    List<Car> findByBrandOrColor(String brand, String color);

    // Can also use ORDER BY to sort results
    List<Car> findByBrandOrderByYearAsc(String brand);

}