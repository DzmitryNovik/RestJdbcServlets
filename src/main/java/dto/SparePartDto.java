package dto;

import entitiy.Car;

import java.util.Set;

public class SparePartDto {

    Long id;

    private String name;

    private String serialNumber;

    private Set<Car> cars;

    public SparePartDto() {
    }

    public SparePartDto(Long id, String name, String serialNumber, Set<Car> cars) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.cars = cars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
