package ua.kukhtar.controller.command.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements interface Command, implements logic of
 * getting current user active orders and send them to the JSP page
 */
public class UserOrdersCommand  implements Command {
    private final UserService service;
    private final Logger logger = LogManager.getLogger(UserOrdersCommand.class);


    public UserOrdersCommand(UserService userService){
        this.service = userService;
    }

    /**
     * This method gets HttpServletRequest object from which it takes name of user and
     * get list of active orders for this user, than it sets obtained list as an attribute for
     * the JSP page, and return URL of that page
     * @param request HttpServletRequest object
     * @return String of URL of JSP pages with closed orders
     */
    @Override
    public String execute(HttpServletRequest request) {

        String userName = (String)request.getSession().getAttribute("name");
        List<Order> orders = service.getActiveOrders(userName);

        Map<Integer, String> buttonsDisplay  = orders.stream().collect(Collectors.toMap(Order::getId, x -> x.getStatus()==STATUS.WAITING_FOR_PAYMENT?"table":"none"));

        request.getSession().setAttribute("orderButtons", buttonsDisplay);

        logger.debug("Order list size: {} ", orders.size());
        request.setAttribute("orders", service.getActiveOrders(userName));
        return "/user/orders.jsp";
    }
}
