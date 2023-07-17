package servlet;

import service.OwnerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/owner/*")
public class OwnerServlet extends HttpServlet {

    private OwnerService ownerService;

    @Override
    public void init() throws ServletException {
        ownerService = (OwnerService) getServletContext().getAttribute("ownerService");;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        //Long id = Long.parseLong(req.getParameter("id"));
        resp.setContentType("application/json; charset=UTF-8");
        resp.setStatus(200);

        try {
            String getResponse = ownerService.handleGetRequest(id).get();
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(getResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ownerService.handlePostRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ownerService.handlePutRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ownerService.handleDeleteRequest(req);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
