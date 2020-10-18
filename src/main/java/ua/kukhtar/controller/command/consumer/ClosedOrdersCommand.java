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

public class ClosedOrdersCommand implements Command {
    private UserService service;
    private Logger logger = LogManager.getLogger(ClosedOrdersCommand.class);

    public ClosedOrdersCommand(UserService userService){
        this.service = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {

        String userName = (String)request.getSession().getAttribute("name");
        List<Order> orders = service.getClosedOrders(userName);

        logger.debug("Order list size: {} ", orders.size());
        request.getSession().setAttribute("orders",orders);
        return "/user/closed_orders.jsp";
    }
}
