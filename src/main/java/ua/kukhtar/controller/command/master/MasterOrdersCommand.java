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

public class MasterOrdersCommand implements Command {
    private final OrderService service;
    private final Logger logger = LogManager.getLogger(MasterOrdersCommand.class);
    public MasterOrdersCommand(OrderService service){
        this.service = service;
    }
    @Override
    public String execute(HttpServletRequest request) {
        logger.debug("id obtained from session {}", request.getSession().getAttribute("id"));
        int id = (int)request.getSession().getAttribute("id");
        List<Order> orders = service.getMastersOrders(id);
        request.getSession().setAttribute("orders", orders);

        Map<Integer, String> buttonsDisplay  = orders.stream().collect(Collectors.toMap(Order::getId, x -> (x.getStatus()== STATUS.PAID || x.getStatus()== STATUS.IN_PROCESS)?"table":"none"));;
        request.setAttribute("buttonsDisplay", buttonsDisplay);
        return "/master/orders.jsp";
    }
}
