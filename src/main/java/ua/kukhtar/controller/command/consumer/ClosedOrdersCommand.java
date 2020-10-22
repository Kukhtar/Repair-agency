package ua.kukhtar.controller.command.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClosedOrdersCommand class implements Command interface and implements
 * logic of getting closed orders for current user and send them to the JSP
 */
public class ClosedOrdersCommand implements Command {
    private final UserService service;
    private final Logger logger = LogManager.getLogger(ClosedOrdersCommand.class);

    public ClosedOrdersCommand(UserService userService){
        this.service = userService;
    }

    /**
     * This method gets HttpServletRequest object from which it takes name of user and
     * get list of closed orders for this user, than it sets obtained list as an attribute for
     * JSP page, and return URL of this page
     * @param request HttpServletRequest object
     * @return String of URL of JSP pages with closed orders
     */
    @Override
    public String execute(HttpServletRequest request) {

        String userName = (String)request.getSession().getAttribute("name");
        List<Order> orders = service.getClosedOrders(userName);

        logger.debug("Order list size: {} ", orders.size());
        request.setAttribute("orders",orders);
        return "/user/closed_orders.jsp";
    }
}
