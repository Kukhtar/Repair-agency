package ua.kukhtar.controller.command.master;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

public class MastersOrderManagingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MastersOrderManagingCommand.class);
    private final OrderService orderService;
    private final UserService userService;

    private Order managingOrder;
    private String  master;
    public MastersOrderManagingCommand(OrderService orderService, UserService userService)
    {
        this.orderService = orderService;
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter("order_id");
        if (id!=null) {
            initCallManaging(request, id);
            return "/master/order_manage.jsp?id=" + id;
        }
        if (managingOrder == null){
            throw new IllegalStateException("Order not founded");
        }

        return processOrder(request);
    }

        private void initCallManaging(HttpServletRequest request ,String id){
             managingOrder = orderService.findOrderByID(Integer.parseInt(id));
            logger.debug("Order id = {}", id);
            master = userService.getUserById(managingOrder.getMaster().getId()).get().getFullName();
        logger.debug("obtained order: {}", managingOrder);
        setJspAttribute(request);
    }

    public String processOrder(HttpServletRequest request){

        if (managingOrder.getStatus() == STATUS.PAID){
            managingOrder.setStatus(STATUS.IN_PROCESS);
        }else if (managingOrder.getStatus() == STATUS.IN_PROCESS){
            managingOrder.setStatus(STATUS.DONE);
        }else {
            logger.error("master cant handle this order {}", managingOrder);
            throw new IllegalStateException("Wrong status for this action");
        }


        orderService.updateStatus(managingOrder);
        return "redirect:/app/master/orders";
    }



    private void setJspAttribute(HttpServletRequest request){
        request.setAttribute("order", managingOrder);
        request.setAttribute("master", master);
        if (managingOrder.getStatus() == STATUS.PAID){
            request.setAttribute("formAction", "Start working");
        }else {
            request.setAttribute("formAction", "End");
        }
    }
}
