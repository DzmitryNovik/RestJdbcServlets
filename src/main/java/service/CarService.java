package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CarDao;
import dto.CarDto;
import entitiy.Car;
import mapper.CarMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CarDao carDao;
    private CarMapper carMapper;

    public CarService(CarDao carDao, CarMapper carMapper) {
        this.carDao = carDao;
        this.carMapper = carMapper;
    }

    public Optional<String> handleGetRequest(String parameter) throws SQLException {
        if (parameter == null) {
            List<CarDto> cars = carDao.readAll().stream().map(car -> {
                return carMapper.toCarDto(car);
            }).collect(Collectors.toList());
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(cars));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Long id = Long.parseLong(parameter);
            Car car = carDao.readById(id);
            CarDto carDto = carMapper.toCarDto(car);
            try {
                return Optional.ofNullable(objectMapper.writeValueAsString(carDto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void handlePostRequest(HttpServletRequest req) throws SQLException {
        CarDto carDto = getCarDtoFromReqBody(req);
        carDao.create(carMapper.toCar(carDto));
    }

    public void handlePutRequest(HttpServletRequest req) throws SQLException {
        CarDto carDto = getCarDtoFromReqBody(req);
        carDao.update(carMapper.toCar(carDto));
    }

    public void handleDeleteRequest(HttpServletRequest req) throws SQLException {
        Long id = Long.parseLong(req.getParameter("id"));
        carDao.deleteById(id);
    }

    private CarDto getCarDtoFromReqBody(HttpServletRequest req) {
        String reqBody = null;
        try {
            reqBody = req.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(reqBody, CarDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
