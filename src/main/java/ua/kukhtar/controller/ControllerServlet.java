package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.controller.command.LoginCommand;
import ua.kukhtar.model.dao.ConsumerDao;
import ua.kukhtar.model.dao.impl.JDBCDaoFactory;
import ua.kukhtar.model.service.ConsumerService;
import ua.kukhtar.model.user.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerServlet extends HttpServlet {

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    private Map<String, Command> commands = new HashMap<>();

    public void init(){
//        commands.put("logout", new LogOut());
        commands.put("login", new LoginCommand(new ConsumerService()));
//        commands.put("registration", new Registration());
//        commands.put("exception" , new Exception());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("starting doGet method");
//        resp.sendRedirect("/Repair_agency_war/jsp/login.jsp");
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);
        path = path.replaceAll(".*/api/" , "");
        System.out.println(path);
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp)");
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/Repair_agency_war"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
