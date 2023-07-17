package servlet;

import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/car/*")
public class CarServlet extends HttpServlet {

    private CarService carService;

    @Override
    public void init() throws ServletException {
        carService = (CarService) getServletContext().getAttribute("carService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carId = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);
        try {
            String responseBody = carService.handleGetRequest(carId).get();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(responseBody);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            carService.handlePostRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            carService.handlePutRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            carService.handleDeleteRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
