package mapper;

import dao.OwnerDao;
import dao.SparePartDao;
import dto.CarDto;
import entitiy.Car;

public class CarMapper {

    private OwnerDao ownerDao;
    private SparePartDao sparePartDao;

    public CarMapper(OwnerDao ownerDao, SparePartDao sparePartDao) {
        this.ownerDao = ownerDao;
        this.sparePartDao = sparePartDao;
    }

    public CarDto toCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setOwner(car.getOwner());
        carDto.setSpareParts(car.getSpareParts());
        return carDto;
    }

    public Car toCar(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setOwner(carDto.getOwner());
        car.setSpareParts(carDto.getSpareParts());
        return car;
    }
}
