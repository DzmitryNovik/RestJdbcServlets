import dao.ConnectionPool;
import dao.impl.CarDaoJDBC;
import dao.impl.ConnectionPoolImpl;
import dao.impl.OwnerDaoJDBC;
import dao.impl.SparePartDaoJDBC;
import mapper.CarMapper;
import mapper.OwnerMapper;
import mapper.SparePartMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import service.CarService;
import service.OwnerService;
import service.SparePartService;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class ServicesTest {
    private static final String DATABASE_NAME = "test";
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("0000")
            .withInitScript("data.sql")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);

    @BeforeAll
    static void start() {
        postgreSQLContainer.start();
        System.setProperty("url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("user", postgreSQLContainer.getUsername());
        System.setProperty("password", postgreSQLContainer.getPassword());
    }

    ConnectionPool connectionPool = new ConnectionPoolImpl();
    CarService carService = new CarService(new CarDaoJDBC(connectionPool),
                            new CarMapper(new OwnerDaoJDBC(connectionPool),
                                    new SparePartDaoJDBC(connectionPool)));
    OwnerService ownerService = new OwnerService(new OwnerDaoJDBC(connectionPool),
                                new OwnerMapper(new CarDaoJDBC(connectionPool)));
    SparePartService sparePartService = new SparePartService(new SparePartDaoJDBC(connectionPool),
                                        new SparePartMapper(new CarDaoJDBC(connectionPool)));

    @Test
    void getOwnersTest() {
        String expectedResponse = "Optional[[{\"id\":1,\"firstName\":\"Volodja\",\"lastName\":\"Muhin\",\"cars\":[{\"id\":2,\"brand\":\"Opel\",\"model\":\"Vectra\",\"owner\":null,\"spareParts\":null}]},{\"id\":2,\"firstName\":\"Petya\",\"lastName\":\"Ivanov\",\"cars\":[{\"id\":9,\"brand\":\"Mersedes\",\"model\":\"190\",\"owner\":null,\"spareParts\":null},{\"id\":10,\"brand\":\"Audi\",\"model\":\"100\",\"owner\":null,\"spareParts\":null}]},{\"id\":3,\"firstName\":\"Dima\",\"lastName\":\"Novik\",\"cars\":[{\"id\":1,\"brand\":\"BMW\",\"model\":\"750\",\"owner\":null,\"spareParts\":null},{\"id\":3,\"brand\":\"Aurus\",\"model\":\"Senat\",\"owner\":null,\"spareParts\":null}]},{\"id\":4,\"firstName\":\"Gena\",\"lastName\":\"Malahov\",\"cars\":[{\"id\":4,\"brand\":\"Lada\",\"model\":\"Granta\",\"owner\":null,\"spareParts\":null},{\"id\":7,\"brand\":\"Honda\",\"model\":\"Civic\",\"owner\":null,\"spareParts\":null}]},{\"id\":5,\"firstName\":\"Mike\",\"lastName\":\"Brown\",\"cars\":[{\"id\":5,\"brand\":\"Volkswagen\",\"model\":\"Golf\",\"owner\":null,\"spareParts\":null}]},{\"id\":6,\"firstName\":\"Sarah\",\"lastName\":\"Lee\",\"cars\":[{\"id\":6,\"brand\":\"Ford\",\"model\":\"Mondeo\",\"owner\":null,\"spareParts\":null}]},{\"id\":7,\"firstName\":\"Tom\",\"lastName\":\"Wilson\",\"cars\":[{\"id\":8,\"brand\":\"Skoda\",\"model\":\"Roomster\",\"owner\":null,\"spareParts\":null}]}]]";
        String response = null;
        try {
            response = ownerService.handleGetRequest(null).toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(expectedResponse, response);
    }

    @Test
    void getCarsTest() {
        String expectedResponse = "Optional[[{\"id\":1,\"brand\":\"BMW\",\"model\":\"750\",\"owner\":{\"id\":3,\"firstName\":\"Dima\",\"lastName\":\"Novik\",\"cars\":null},\"spareParts\":[{\"id\":2,\"name\":\"Detail2\",\"serialNumber\":\"555-5678\",\"cars\":null},{\"id\":3,\"name\":\"Detail3\",\"serialNumber\":\"555-9999\",\"cars\":null}]},{\"id\":2,\"brand\":\"Opel\",\"model\":\"Vectra\",\"owner\":{\"id\":1,\"firstName\":\"Volodja\",\"lastName\":\"Muhin\",\"cars\":null},\"spareParts\":[{\"id\":1,\"name\":\"Detail1\",\"serialNumber\":\"555-1234\",\"cars\":null},{\"id\":2,\"name\":\"Detail2\",\"serialNumber\":\"555-5678\",\"cars\":null},{\"id\":4,\"name\":\"Detail4\",\"serialNumber\":\"555-2468\",\"cars\":null}]},{\"id\":3,\"brand\":\"Aurus\",\"model\":\"Senat\",\"owner\":{\"id\":3,\"firstName\":\"Dima\",\"lastName\":\"Novik\",\"cars\":null},\"spareParts\":[{\"id\":4,\"name\":\"Detail4\",\"serialNumber\":\"555-2468\",\"cars\":null}]},{\"id\":4,\"brand\":\"Lada\",\"model\":\"Granta\",\"owner\":{\"id\":4,\"firstName\":\"Gena\",\"lastName\":\"Malahov\",\"cars\":null},\"spareParts\":[{\"id\":4,\"name\":\"Detail4\",\"serialNumber\":\"555-2468\",\"cars\":null},{\"id\":5,\"name\":\"Detail5\",\"serialNumber\":\"555-1357\",\"cars\":null}]},{\"id\":5,\"brand\":\"Volkswagen\",\"model\":\"Golf\",\"owner\":{\"id\":5,\"firstName\":\"Mike\",\"lastName\":\"Brown\",\"cars\":null},\"spareParts\":[{\"id\":1,\"name\":\"Detail1\",\"serialNumber\":\"555-1234\",\"cars\":null},{\"id\":3,\"name\":\"Detail3\",\"serialNumber\":\"555-9999\",\"cars\":null}]},{\"id\":6,\"brand\":\"Ford\",\"model\":\"Mondeo\",\"owner\":{\"id\":6,\"firstName\":\"Sarah\",\"lastName\":\"Lee\",\"cars\":null},\"spareParts\":[{\"id\":2,\"name\":\"Detail2\",\"serialNumber\":\"555-5678\",\"cars\":null},{\"id\":3,\"name\":\"Detail3\",\"serialNumber\":\"555-9999\",\"cars\":null},{\"id\":5,\"name\":\"Detail5\",\"serialNumber\":\"555-1357\",\"cars\":null}]},{\"id\":7,\"brand\":\"Honda\",\"model\":\"Civic\",\"owner\":{\"id\":4,\"firstName\":\"Gena\",\"lastName\":\"Malahov\",\"cars\":null},\"spareParts\":[{\"id\":6,\"name\":\"Detail6\",\"serialNumber\":\"555-7890\",\"cars\":null},{\"id\":7,\"name\":\"Detail7\",\"serialNumber\":\"555-4321\",\"cars\":null}]},{\"id\":8,\"brand\":\"Skoda\",\"model\":\"Roomster\",\"owner\":{\"id\":7,\"firstName\":\"Tom\",\"lastName\":\"Wilson\",\"cars\":null},\"spareParts\":[{\"id\":7,\"name\":\"Detail7\",\"serialNumber\":\"555-4321\",\"cars\":null},{\"id\":8,\"name\":\"Detail8\",\"serialNumber\":\"555-9876\",\"cars\":null}]},{\"id\":9,\"brand\":\"Mersedes\",\"model\":\"190\",\"owner\":{\"id\":2,\"firstName\":\"Petya\",\"lastName\":\"Ivanov\",\"cars\":null},\"spareParts\":[{\"id\":8,\"name\":\"Detail8\",\"serialNumber\":\"555-9876\",\"cars\":null}]},{\"id\":10,\"brand\":\"Audi\",\"model\":\"100\",\"owner\":{\"id\":2,\"firstName\":\"Petya\",\"lastName\":\"Ivanov\",\"cars\":null},\"spareParts\":[{\"id\":6,\"name\":\"Detail6\",\"serialNumber\":\"555-7890\",\"cars\":null},{\"id\":9,\"name\":\"Detail9\",\"serialNumber\":\"555-3698\",\"cars\":null}]}]]";
        String response = null;
        try {
            response = carService.handleGetRequest(null).toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(expectedResponse, response);
    }

    @Test
    void getSparePartsTest() {
        String expectedResponse = "Optional[[{\"id\":1,\"name\":\"Detail1\",\"serialNumber\":\"555-1234\",\"cars\":[{\"id\":2,\"brand\":\"Opel\",\"model\":\"Vectra\",\"owner\":null,\"spareParts\":null},{\"id\":5,\"brand\":\"Volkswagen\",\"model\":\"Golf\",\"owner\":null,\"spareParts\":null}]},{\"id\":2,\"name\":\"Detail2\",\"serialNumber\":\"555-5678\",\"cars\":[{\"id\":1,\"brand\":\"BMW\",\"model\":\"750\",\"owner\":null,\"spareParts\":null},{\"id\":2,\"brand\":\"Opel\",\"model\":\"Vectra\",\"owner\":null,\"spareParts\":null},{\"id\":6,\"brand\":\"Ford\",\"model\":\"Mondeo\",\"owner\":null,\"spareParts\":null}]},{\"id\":3,\"name\":\"Detail3\",\"serialNumber\":\"555-9999\",\"cars\":[{\"id\":1,\"brand\":\"BMW\",\"model\":\"750\",\"owner\":null,\"spareParts\":null},{\"id\":5,\"brand\":\"Volkswagen\",\"model\":\"Golf\",\"owner\":null,\"spareParts\":null},{\"id\":6,\"brand\":\"Ford\",\"model\":\"Mondeo\",\"owner\":null,\"spareParts\":null}]},{\"id\":4,\"name\":\"Detail4\",\"serialNumber\":\"555-2468\",\"cars\":[{\"id\":2,\"brand\":\"Opel\",\"model\":\"Vectra\",\"owner\":null,\"spareParts\":null},{\"id\":3,\"brand\":\"Aurus\",\"model\":\"Senat\",\"owner\":null,\"spareParts\":null},{\"id\":4,\"brand\":\"Lada\",\"model\":\"Granta\",\"owner\":null,\"spareParts\":null}]},{\"id\":5,\"name\":\"Detail5\",\"serialNumber\":\"555-1357\",\"cars\":[{\"id\":4,\"brand\":\"Lada\",\"model\":\"Granta\",\"owner\":null,\"spareParts\":null},{\"id\":6,\"brand\":\"Ford\",\"model\":\"Mondeo\",\"owner\":null,\"spareParts\":null}]},{\"id\":6,\"name\":\"Detail6\",\"serialNumber\":\"555-7890\",\"cars\":[{\"id\":7,\"brand\":\"Honda\",\"model\":\"Civic\",\"owner\":null,\"spareParts\":null},{\"id\":10,\"brand\":\"Audi\",\"model\":\"100\",\"owner\":null,\"spareParts\":null}]},{\"id\":7,\"name\":\"Detail7\",\"serialNumber\":\"555-4321\",\"cars\":[{\"id\":7,\"brand\":\"Honda\",\"model\":\"Civic\",\"owner\":null,\"spareParts\":null},{\"id\":8,\"brand\":\"Skoda\",\"model\":\"Roomster\",\"owner\":null,\"spareParts\":null}]},{\"id\":8,\"name\":\"Detail8\",\"serialNumber\":\"555-9876\",\"cars\":[{\"id\":8,\"brand\":\"Skoda\",\"model\":\"Roomster\",\"owner\":null,\"spareParts\":null},{\"id\":9,\"brand\":\"Mersedes\",\"model\":\"190\",\"owner\":null,\"spareParts\":null}]},{\"id\":9,\"name\":\"Detail9\",\"serialNumber\":\"555-3698\",\"cars\":[{\"id\":10,\"brand\":\"Audi\",\"model\":\"100\",\"owner\":null,\"spareParts\":null}]}]]";
        String response = null;
        try {
            response = sparePartService.handleGetRequest(null).toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(expectedResponse, response);
    }
}