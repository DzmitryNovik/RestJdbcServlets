package dao.impl;

import dao.ConnectionPool;
import dao.SparePartDao;
import entitiy.Car;
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

public class SparePartDaoJDBC implements SparePartDao {

    private final ConnectionPool connectionPool;

    private final String GET_ALL_SPARE_PARTS = "SELECT * FROM public.spare_part";
    private final String GET_SPARE_PART_BY_ID = "SELECT * FROM public.spare_part WHERE id = ?";
    private final String GET_CARS_BY_SPARE_PART_ID = "SELECT * FROM car JOIN car_spare_part csp on car.id = csp.car_id WHERE spare_part_id = ?";
    private final String INSERT_SPARE_PART = "INSERT INTO public.spare_part (name, serial_number) VALUES (?, ?)";
    private final String UPDATE_SPARE_PART_BY_ID = "UPDATE public.spare_part SET name = ?, serial_number = ? WHERE id = ?";
    private final String DELETE_BY_ID = "DELETE FROM public.spare_part WHERE id = ?";

    public SparePartDaoJDBC(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(SparePart sparePart) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPARE_PART);
        preparedStatement.setString(1, sparePart.getName());
        preparedStatement.setString(2, sparePart.getSerialNumber());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public SparePart readById(Long id) throws SQLException {
        SparePart sparePart = new SparePart();
        Set<Car> cars = new LinkedHashSet();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_SPARE_PART_BY_ID);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            sparePart.setId(resultSet.getLong("id"));
            sparePart.setName(resultSet.getString("name"));
            sparePart.setSerialNumber(resultSet.getString("serial_number"));
            sparePart.setCars(cars);
        }
        preparedStatement = connection.prepareStatement(GET_CARS_BY_SPARE_PART_ID);
        preparedStatement.setLong(1, sparePart.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Car car = new Car();
            car.setId(resultSet.getLong("id"));
            car.setBrand(resultSet.getString("brand"));
            car.setModel(resultSet.getString("model"));
            cars.add(car);
        }

        connectionPool.releaseConnection(connection);
        return sparePart;
    }

    @Override
    public List<SparePart> readAll() throws SQLException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_SPARE_PARTS);
        List<SparePart> spareParts = new ArrayList<>();

        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            SparePart sparePart = readById(id);
            spareParts.add(sparePart);
        }

        connectionPool.releaseConnection(connection);
        return spareParts;
    }

    @Override
    public void update(SparePart sparePart) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SPARE_PART_BY_ID);
        preparedStatement.setString(1, sparePart.getName());
        preparedStatement.setString(2, sparePart.getSerialNumber());
        preparedStatement.setLong(3, sparePart.getId());
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        connectionPool.releaseConnection(connection);
    }
}
