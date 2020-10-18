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

public class AllOrdersCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private Logger logger = LogManager.getLogger(AllOrdersCommand.class);
    public AllOrdersCommand(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }
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

    private Comparator<Order> getComparatorFromJspPage(String sortBy){
        if (sortBy == null){
            logger.debug("sort by value not founded");
            return (o1, o2) -> o1.getDate().compareTo(o2.getDate());
        }
        logger.debug("sortBy value: {}", sortBy);
        switch (sortBy){
            case "date":
                return Comparator.comparing(Order::getDate);
            case "price":
                return Comparator.comparing(Order::getPrice);
            case "status":
                return Comparator.comparing(Order::getStatus);
            default: {
                logger.debug("default statement worked");
                return Comparator.comparing(Order::getDate);
            }
        }
    }

    private List<Order> filterOrders(HttpServletRequest request,List<Order> orders){
        String statusParam = request.getParameter("statusFilter");
        String masterParam = request.getParameter("masterFilter");
        if (statusParam!=null && !statusParam.equals("none")){
            logger.debug("default value of filter = none? {}", statusParam.equals("none"));
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
