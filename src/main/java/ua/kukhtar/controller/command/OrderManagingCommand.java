package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class OrderManagingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(OrderManagingCommand.class);
    private final OrderService orderService;
    private final UserService userService;
    public OrderManagingCommand(OrderService orderService, UserService userService)
    {
        this.orderService = orderService;
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter("order_id");
        logger.debug("Order id = {}", id);

        Order order = orderService.findOrderByID(Integer.parseInt(id));
        Map<Integer, String> masters = userService.getMapOfMasters();
        logger.debug("obtained order: {}", order);
        request.setAttribute("order", order);
        request.setAttribute("masters", masters);

        return "/manager/order_manage.jsp";
    }
}
