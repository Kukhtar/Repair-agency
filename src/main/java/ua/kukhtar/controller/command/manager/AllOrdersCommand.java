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

/**
 * Implements Command interface, implements logic of getting all orders of all users,
 * also gives ability to sort and filter this orders
 */
public class AllOrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(AllOrdersCommand.class);
    public AllOrdersCommand(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Gets all orders, then get information about sorting and filtering from the form, and process
     * sorting and filtering, return URL of JSP page with all orders
     * @param request
     * @return
     */
    @Override
    public String execute(HttpServletRequest request) {
        List<Order> orders;
        orders = orderService.getAllOrders();

        logger.debug("Orders list size: {}", orders.size());
        orders = filterOrders(request, orders);

        String sortBy = request.getParameter("sortBy");
        orders.sort(getComparatorFromJspPage(sortBy));

        request.setAttribute("orders", orders);
        request.setAttribute("masters",userService.getMapOfMasters() );
        return "/manager/all_orders.jsp";
    }

    /**
     * Gets parameter for sorting by, and return appropriate Comparator lambda
     * @param sortBy parameter of sorting
     * @return appropriate Comparator
     */
    private Comparator<Order> getComparatorFromJspPage(String sortBy){
        if (sortBy == null){
            logger.debug("sort by value not founded");
            return Comparator.comparing(Order::getDate);
        }
        logger.debug("sortBy value: {}", sortBy);
        switch (sortBy){
            case "price":
                return Comparator.comparing(Order::getPrice);
            case "status":
                return Comparator.comparing(Order::getStatus);
            default:
                return Comparator.comparing(Order::getDate);

        }
    }

    /**
     * Gets filter parameters for master and status fields, operate this filtering and return list of orders
     * @param request HttpServletRequest that contain filter parameters
     * @param orders list that need to be filtered
     * @return filtered list of orders
     */
    private List<Order> filterOrders(HttpServletRequest request,List<Order> orders){
        String statusParam = request.getParameter("statusFilter");
        String masterParam = request.getParameter("masterFilter");
        if (statusParam!=null && !statusParam.equals("none")){
            STATUS status = STATUS.valueOf(statusParam);
            orders = orders.stream().filter(a -> a.getStatus()==status).collect(Collectors.toList());
        }

        if (masterParam!=null){
            logger.debug("default value of filter  {}", masterParam);

            int masterId = Integer.parseInt(masterParam);
            if (masterId < 0){
                return orders;
            }
            orders = orders.stream().filter(a -> a.getMaster().getId()==masterId).collect(Collectors.toList());
        }

        return orders;
    }
}
