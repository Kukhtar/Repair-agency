package ua.kukhtar.controller;

import ua.kukhtar.dao.ConsumerDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConsumerDAOImpl consumerDAO = new ConsumerDAOImpl();
        resp.getWriter().append("It's working!!!").append("\n").append(consumerDAO.read(1).getBankAccount());

    }
}
