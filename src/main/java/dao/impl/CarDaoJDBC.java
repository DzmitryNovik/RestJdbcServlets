package dao.impl;

import dao.CarDao;
import dao.ConnectionPool;
import entitiy.Car;
import entitiy.Owner;
import entitiy.SparePart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CarDaoJDBC implements CarDao {

    private final ConnectionPool connectionPool;

    private final String GET_CAR_BY_ID = "SELECT * FROM public.car WHERE id = ?";
    private final String GET_OWNER_BY_ID = "SELECT * FROM public.owner WHERE id = ?";
    private final String GET_SPARE_PARTS_BY_CAR_ID = "SELECT * FROM spare_part JOIN car_spare_part csp on spare_part.id = csp.spare_part_id WHERE car_id = ?";
    private final String GET_All_CARS = "SELECT * FROM public.car";
    private final String INSERT_CAR = "INSERT INTO public.car (brand, model, owner_id) VALUES (?, ?, ?)";
    private final String DELETE_CAR_BY_ID = "DELETE FROM public.car WHERE id = ?";
    private final String UPDATE_CAR_BY_ID = "UPDATE public.car SET brand = ?, model = ? WHERE id = ?";

    public CarDaoJDBC(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Car car) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAR);
        preparedStatement.setString(1, car.getBrand());
        preparedStatement.setString(2, car.getModel());
        Long ownerId = Math.round(Math.random() * 4) ;
        preparedStatement.setLong(3, ownerId);
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public Car readById(Long id) throws SQLException {
        Car car = new Car();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_CAR_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Long ownerId = null;
        Owner owner = new Owner();
        Set<SparePart> spareParts = new LinkedHashSet<>();
        while (resultSet.next()) {
            car.setId(resultSet.getLong("id"));
            car.setBrand(resultSet.getString("brand"));
            car.setModel(resultSet.getString("model"));
            ownerId = resultSet.getLong("owner_id");
            car.setOwner(owner);
            car.setSpareParts(spareParts);
        }
        preparedStatement = connection.prepareStatement(GET_OWNER_BY_ID);
        preparedStatement.setLong(1, ownerId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            owner.setId(resultSet.getLong("id"));
            owner.setFirstName(resultSet.getString("first_name"));
            owner.setLastName(resultSet.getString("last_name"));
        }
        preparedStatement = connection.prepareStatement(GET_SPARE_PARTS_BY_CAR_ID);
        preparedStatement.setLong(1, car.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            SparePart sparePart = new SparePart();
            sparePart.setId(resultSet.getLong("id"));
            sparePart.setName(resultSet.getString("name"));
            sparePart.setSerialNumber(resultSet.getString("serial_number"));
            spareParts.add(sparePart);
        }

        connectionPool.releaseConnection(connection);
        return car;
    }

    @Override
    public List<Car> readAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_All_CARS);

        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Car car = readById(id);
            cars.add(car);
        }
        connectionPool.releaseConnection(connection);
        return cars;
    }

    @Override
    public void update(Car car) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Long id = car.getId();
        String brand = car.getBrand();
        String model = car.getModel();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CAR_BY_ID);
        preparedStatement.setString(1, brand);
        preparedStatement.setString(2, model);
        preparedStatement.setLong(3, id);
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAR_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.executeQuery();
        connectionPool.releaseConnection(connection);
    }
}
