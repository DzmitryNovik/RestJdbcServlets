package dao;

import entitiy.Car;
import entitiy.Owner;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {

    void create(Car car) throws SQLException;

    Car readById(Long id) throws SQLException;

    List<Car> readAll() throws SQLException;

    void update(Car car) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
