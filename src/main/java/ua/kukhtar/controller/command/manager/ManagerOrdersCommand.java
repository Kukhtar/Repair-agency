package ua.kukhtar.controller.command.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerOrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private Logger logger = LogManager.getLogger(ManagerOrdersCommand.class);
    private static final int RECORDS_ON_ONE_PAGE = 8;
    private final boolean activeOrders;
    public ManagerOrdersCommand(OrderService orderService, UserService userService, boolean activeOrders){
        this.orderService = orderService;
        this.userService = userService;
        this.activeOrders = activeOrders;
    }
    @Override
    public String execute(HttpServletRequest request) {
        List<Order> orders;
        int start = getStartOfOrders(request.getParameter("page"));
        int ordersCount;
        if (activeOrders) {
            orders = orderService.getActiveOrders(start, RECORDS_ON_ONE_PAGE);
            ordersCount = orderService.countOfActiveOrders();
        }else {
            orders = orderService.getClosedOrders(start, RECORDS_ON_ONE_PAGE);
            ordersCount = orderService.countOfClosedOrders();
        }
        logger.debug("Orders list size: {}", orders.size());

        request.setAttribute("countOfPages", Math.ceil(ordersCount * 1.0/RECORDS_ON_ONE_PAGE));
        request.setAttribute("orders", orders);
        request.setAttribute("masters",userService.getMapOfMasters() );

        return activeOrders?"/manager/active_orders.jsp":"/manager/closed_orders.jsp";
    }

    private int getStartOfOrders(String page) {
        if (page == null || page == "") {
            return 0;
        } else {
            return (Integer.parseInt(page) - 1) * RECORDS_ON_ONE_PAGE;
        }
    }
}
