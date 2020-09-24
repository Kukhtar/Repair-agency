package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ua.kukhtar.dao.DBCPDataSourceBuilder;
import ua.kukhtar.dao.DataSourceBuilder;
import ua.kukhtar.dao.user.AccountDAO;
import ua.kukhtar.dao.user.AccountDAOImpl;
import ua.kukhtar.dao.user.ConsumerDAO;
import ua.kukhtar.dao.user.ConsumerDAOImpl;
import ua.kukhtar.model.user.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class ControllerServlet extends HttpServlet {
    private DataSourceBuilder dataSourceBuilder;
    private ConsumerDAO consumerDAO;
    private AccountDAO accountDAO;

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    @Override
    public void init() throws ServletException {
        logger.info("Servlet was initialized");
        dataSourceBuilder = new DBCPDataSourceBuilder();
        accountDAO = new AccountDAOImpl(dataSourceBuilder.getDataSource());
        consumerDAO = new ConsumerDAOImpl(dataSourceBuilder.getDataSource(), accountDAO);
        logger.info("DAO objects successfully created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("WTF");
        logger.debug("starting doGet method");
        throw new IllegalArgumentException();
        //req.getServletContext().getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Optional<Account> acc = accountDAO.findOptional(1);
        // если пользователь есть в системе
        if (acc.isPresent()) {
            // создаем для него сессию
            HttpSession session = req.getSession();
            // кладем в атрибуты сессии атрибут user с именем пользователя
            session.setAttribute("user", name);
            // перенаправляем на страницу home
            resp.getWriter().append("<h4>Hello name, your bank account </h4>");
        } else {
            resp.getWriter().append("<h4>No such user </h4>");
        }
    }
}
