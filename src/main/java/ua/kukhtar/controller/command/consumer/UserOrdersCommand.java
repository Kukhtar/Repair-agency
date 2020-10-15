package ua.kukhtar.controller.command.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserOrdersCommand  implements Command {
    private UserService service;
    private Logger logger = LogManager.getLogger(UserOrdersCommand.class);

    public UserOrdersCommand(UserService userService){
        this.service = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {

        String userName = (String)request.getSession().getAttribute("name");
        List<Order> orders = service.getOrders(userName);

        Map<Integer, String> buttonsDisplay  = orders.stream().collect(Collectors.toMap(Order::getId, x -> x.getStatus()==STATUS.WAITING_FOR_PAYMENT?"table":"none"));;
        Order order;

        request.getSession().setAttribute("orderButtons", buttonsDisplay);

        logger.debug("Order list size: {} ", orders.size());
        request.getSession().setAttribute("orders", service.getOrders(userName));
        return "/user/orders.jsp";
    }
}
