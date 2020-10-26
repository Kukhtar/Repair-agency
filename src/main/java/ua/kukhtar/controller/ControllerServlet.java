package ua.kukhtar.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ua.kukhtar.controller.command.*;
import ua.kukhtar.controller.command.consumer.*;
import ua.kukhtar.controller.command.manager.AllOrdersCommand;
import ua.kukhtar.controller.command.manager.ManagerOrdersCommand;
import ua.kukhtar.controller.command.manager.OrderManagingCommand;
import ua.kukhtar.controller.command.master.MasterOrdersCommand;
import ua.kukhtar.controller.command.master.MastersOrderManagingCommand;
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

/**
 * Implements Controller layer, this Servlet is mapped to all queries that start with /app,
 * and depends on URL, redirects it to appropriate Command class
 */
public class ControllerServlet extends HttpServlet {

    private final Logger logger = (Logger) LogManager.getLogger(ControllerServlet.class);

    private Map<String, Command> commands = new HashMap<>();

    /**
     * Creates HashMap that contained URL as keys, and Command objects that used
     * to process this query, as an value,
     * @param servletConfig
     */
    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        commands.put("index", new IndexCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("registration", new RegistrationCommand(new UserService()));
        commands.put("manager/active_orders", new ManagerOrdersCommand(new OrderService(), new UserService(), true));
        commands.put("manager/closed_orders", new ManagerOrdersCommand(new OrderService(), new UserService(), false));
        commands.put("manager/all_orders", new AllOrdersCommand(new OrderService(), new UserService()));
        commands.put("manager/manage_order", new OrderManagingCommand(new OrderService(), new UserService()));
        commands.put("user/createOrder", new CreateOrderCommand(new OrderService()));
        commands.put("user/orders", new UserOrdersCommand(new UserService()));
        commands.put("user/closed_orders", new ClosedOrdersCommand(new UserService()));
        commands.put("user/feedback", new FeedbackCommand(new OrderService()));
        commands.put("user/payment", new PaymentCommand(new OrderService(), new UserService()));
        commands.put("master/orders", new MasterOrdersCommand(new OrderService()));
        commands.put("master/manage_order", new MastersOrderManagingCommand(new OrderService(), new UserService()));

    }

    /**
     * redirect all Get request to method {@code processRequest()}
     * @param req request from user
     * @param resp response that user will get
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * redirect all Get request to method {@code processRequest()}
     * @param request request from user
     * @param response response that user will get
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Injects from URL query key for getting appropriate Command object from HashMap
     * @param request request from user
     * @param response response that user will get
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");

        Command command = commands.getOrDefault(path ,
                commands.get("index"));
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", request.getContextPath()));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
