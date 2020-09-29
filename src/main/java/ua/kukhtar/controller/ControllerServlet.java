package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ua.kukhtar.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerServlet extends HttpServlet {

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    private Map<String, Command> commands = new HashMap<>();

//    public void init(){
//        commands.put("logout", new LogOut());
//        commands.put("login", new Login());
//        commands.put("registration", new Registration());
//        commands.put("exception" , new Exception());
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("starting doGet method");
        req.getServletContext().getRequestDispatcher("/jsp/login.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");


    }
}
