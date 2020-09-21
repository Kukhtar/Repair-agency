package ua.kukhtar.controller;

import ua.kukhtar.dao.DBCPDataSourceBuilder;
import ua.kukhtar.dao.DataSourceBuilder;
import ua.kukhtar.dao.user.AccountDAO;
import ua.kukhtar.dao.user.AccountDAOImpl;
import ua.kukhtar.dao.user.ConsumerDAO;
import ua.kukhtar.dao.user.ConsumerDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    private DataSourceBuilder dataSourceBuilder;
    private ConsumerDAO consumerDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        dataSourceBuilder = new DBCPDataSourceBuilder();
        accountDAO = new AccountDAOImpl(dataSourceBuilder.getDataSource());
        consumerDAO = new ConsumerDAOImpl(dataSourceBuilder.getDataSource(), accountDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("It's working!!!").append("\n").append("what " + consumerDAO.findOptional(1).get());

    }
}
