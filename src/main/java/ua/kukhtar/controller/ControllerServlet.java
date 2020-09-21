package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
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

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    @Override
    public void init() throws ServletException {
        logger.info("first log {}", new String("test"));
        dataSourceBuilder = new DBCPDataSourceBuilder();
        accountDAO = new AccountDAOImpl(dataSourceBuilder.getDataSource());
        consumerDAO = new ConsumerDAOImpl(dataSourceBuilder.getDataSource(), accountDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("It's working!!!").append("\n").append("what " + consumerDAO.findOptional(1).get());

    }
}
