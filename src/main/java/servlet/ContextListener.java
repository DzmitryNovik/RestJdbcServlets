package servlet;

import dao.CarDao;
import dao.impl.CarDaoJDBC;
import dao.ConnectionPool;
import dao.impl.ConnectionPoolImpl;
import dao.OwnerDao;
import dao.impl.OwnerDaoJDBC;
import dao.SparePartDao;
import dao.impl.SparePartDaoJDBC;
import mapper.CarMapper;
import mapper.OwnerMapper;
import mapper.SparePartMapper;
import service.CarService;
import service.OwnerService;
import service.SparePartService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private ConnectionPool connectionPool;
    private OwnerDao ownerDao;
    private CarDao carDao;
    private SparePartDao sparePartDao;
    private OwnerMapper ownerMapper;
    private CarMapper carMapper;
    private SparePartMapper sparePartMapper;
    private OwnerService ownerService;
    private CarService carService;
    private SparePartService sparePartService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        connectionPool = ConnectionPoolImpl.getConnectionPool();
        ownerDao = new OwnerDaoJDBC(connectionPool);
        carDao = new CarDaoJDBC(connectionPool);
        sparePartDao = new SparePartDaoJDBC(connectionPool);
        ownerMapper = new OwnerMapper(carDao);
        carMapper = new CarMapper(ownerDao, sparePartDao);
        sparePartMapper = new SparePartMapper(carDao);
        ownerService = new OwnerService(ownerDao, ownerMapper);
        carService = new CarService(carDao, carMapper);
        sparePartService = new SparePartService(sparePartDao, sparePartMapper);

        servletContext.setAttribute("ownerService", ownerService);
        servletContext.setAttribute("carService", carService);
        servletContext.setAttribute("sparePartService", sparePartService);
    }
}
