package servlet;

import service.SparePartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/sparePart/*")
public class SparePartServlet extends HttpServlet {

    SparePartService sparePartService;

    @Override
    public void init() throws ServletException {
        sparePartService = (SparePartService) getServletContext().getAttribute("sparePartService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);

        String getResponse = null;
        try {
            getResponse = sparePartService.handleGetRequest(parameter).get();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(getResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            sparePartService.handlePostRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            sparePartService.handlePutRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            sparePartService.handleDeleteRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
