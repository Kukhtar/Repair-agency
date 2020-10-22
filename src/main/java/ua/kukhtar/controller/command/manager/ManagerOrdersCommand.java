package ua.kukhtar.controller.command.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Implements Command interface, implements logic of getting specified count of orders
 * (count of records on one page is set in final variable RECORDS_ON_ONE_PAGE)
 * also send to JSP total count of pages
 * if flag active is true, than display active orders, else - closed.
 */
public class ManagerOrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(ManagerOrdersCommand.class);
    private static final int RECORDS_ON_ONE_PAGE = 8;
    private final boolean activeOrders;
    public ManagerOrdersCommand(OrderService orderService, UserService userService, boolean activeOrders){
        this.orderService = orderService;
        this.userService = userService;
        this.activeOrders = activeOrders;
    }

    /**
     * checks if flag active is true, then gets list of active orders, else list of closed orders
     * and than send this list to the JSP page as an attribute
     * also send count of pages to the JSP page
     * @param request
     * @return URL of the JSP page with one page of orders
     */
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

    /**
     * Calculates position to start read records in table with orders
     * @param page number of page
     * @return position of first record to read
     */
    private int getStartOfOrders(String page) {
        if (page == null || page.equals("")) {
            return 0;
        } else {
            return (Integer.parseInt(page) - 1) * RECORDS_ON_ONE_PAGE;
        }
    }
}
