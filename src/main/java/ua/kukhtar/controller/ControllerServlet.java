package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ua.kukhtar.controller.command.*;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ControllerServlet extends HttpServlet {

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        //todo: logged users shouldn't have access to index page
        //todo: anybody can execute privileged command, fix this, maybe add user role to URL
        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("registration", new RegistrationCommand(new UserService()));
        commands.put("consumers", new UserListCommand(new UserService()));
        commands.put("createOrder", new CreateOrderCommand(new OrderService()));
        commands.put("userOrders", new UserOrdersCommand(new UserService()));
        commands.put("allOrders", new ManagerOrdersCommand(new OrderService()));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        //todo: maybe remove this default value
        Command command = commands.getOrDefault(path ,
                (r)->"/jsp/index.jsp");
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/repair_agency"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
