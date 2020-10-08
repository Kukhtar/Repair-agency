package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserOrdersCommand  implements Command{
    private UserService service;
    private Logger logger = LogManager.getLogger(UserOrdersCommand.class);
    public UserOrdersCommand(UserService userService){
        this.service = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String userName = (String)request.getSession().getAttribute("name");
        logger.debug("Order list size: {} ", service.getOrders(userName).size());
        request.getSession().setAttribute("orders", service.getOrders(userName));
        return "redirect:/user/orders.jsp";
    }
}
