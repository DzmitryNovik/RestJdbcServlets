package dao.impl;

import dao.ConnectionPool;
import dao.OwnerDao;
import entitiy.Car;
import entitiy.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OwnerDaoJDBC implements OwnerDao {

    private final String GET_OWNER_BY_ID = "SELECT * FROM public.owner WHERE id = ?";
    private final String GET_ALL_OWNERS = "SELECT * FROM public.owner";
    private final String INSERT_OWNER = "INSERT INTO owner (first_name, last_name) VALUES (?, ?)";
    private final String DELETE_CARS_BY_OWNER_ID = "DELETE FROM public.car WHERE owner_id = ?";
    private final String DELETE_OWNER_BY_ID = "DELETE FROM public.owner WHERE id = ?";
    private final String UPDATE_OWNER_BY_ID = "UPDATE public.owner SET first_name = ?, last_name = ? WHERE id = ?";
    private final String GET_CARS_BY_OWNER_ID = "SELECT * FROM public.car WHERE owner_id = ?";

    private final ConnectionPool connectionPool;

    public OwnerDaoJDBC(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Owner owner) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OWNER);
        preparedStatement.setString(1, owner.getFirstName());
        preparedStatement.setString(2, owner.getLastName());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public Owner readById(Long id) throws SQLException {
        Owner owner = new Owner();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_OWNER_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            owner.setId(id);
            owner.setFirstName(resultSet.getString("first_name"));
            owner.setLastName(resultSet.getString("last_name"));
            owner.setCars(cars);
        }

        preparedStatement = connection.prepareStatement(GET_CARS_BY_OWNER_ID);
        preparedStatement.setLong(1, owner.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Car car = new Car();
            car.setId(resultSet.getLong("id"));
            car.setBrand(resultSet.getString("brand"));
            car.setModel(resultSet.getString("model"));
            cars.add(car);
        }
        connectionPool.releaseConnection(connection);
        return owner;
    }

    @Override
    public List<Owner> readAll() throws SQLException {
        List<Owner> owners = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_OWNERS);
        while (resultSet.next()) {
            Owner owner = new Owner();
            List<Car> cars = new ArrayList<>();
            owner.setId(resultSet.getLong("id"));
            owner.setFirstName(resultSet.getString("first_name"));
            owner.setLastName(resultSet.getString("last_name"));
            owner.setCars(cars);

            PreparedStatement preparedStatement = connection.prepareStatement(GET_CARS_BY_OWNER_ID);
            preparedStatement.setLong(1, owner.getId());
            ResultSet carsResultSet = preparedStatement.executeQuery();
            while (carsResultSet.next()) {
                Car car = new Car();
                car.setId(carsResultSet.getLong("id"));
                car.setBrand(carsResultSet.getString("brand"));
                car.setModel(carsResultSet.getString("model"));
                cars.add(car);
            }
            owners.add(owner);
        }
        connectionPool.releaseConnection(connection);
        return owners;
    }

    @Override
    public void update(Owner owner) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OWNER_BY_ID);
        preparedStatement.setString(1, owner.getFirstName());
        preparedStatement.setString(2, owner.getLastName());
        preparedStatement.setLong(3, owner.getId());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE_CARS_BY_OWNER_ID);
        deleteStatement.setLong(1, id);
        deleteStatement.execute();

        deleteStatement = connection.prepareStatement(DELETE_OWNER_BY_ID);
        deleteStatement.setLong(1, id);
        deleteStatement.execute();
        connectionPool.releaseConnection(connection);
    }
}
