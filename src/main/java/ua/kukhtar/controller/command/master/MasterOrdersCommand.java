package ua.kukhtar.controller.command.master;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements Command interface, and implements logic of getting
 * all active orders, where master is current user
 */
public class MasterOrdersCommand implements Command {
    private final OrderService service;
    private final Logger logger = LogManager.getLogger(MasterOrdersCommand.class);
    public MasterOrdersCommand(OrderService service){
        this.service = service;
    }

    /**
     * Gets master id from the session, then gets list of active orders where master id is equals to the one
     * we get from the session, send list of orders to the JSP page
     * @param request HttpServletRequest object
     * @return URL of the JSP page with list of orders for current master
     */
    @Override
    public String execute(HttpServletRequest request) {
        int id = (int)request.getSession().getAttribute("id");
        logger.debug("id obtained from session {}", id);
        List<Order> orders = service.getMastersOrders(id);
        request.setAttribute("orders", orders);

        Map<Integer, String> buttonsDisplay  = orders.stream().collect(Collectors.toMap(Order::getId, x -> (x.getStatus()== STATUS.PAID || x.getStatus()== STATUS.IN_PROCESS)?"table":"none"));
        request.setAttribute("buttonsDisplay", buttonsDisplay);
        return "/master/orders.jsp";
    }
}
