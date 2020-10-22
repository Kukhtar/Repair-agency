package ua.kukhtar.controller.command.master;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements Command interface, also implements logic of order managing by master.
 * Master can only change status from PAID to IN_PROCESS, and from IN_PROCESS to DONE
 */
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

    /**
     * Gets gets order id from query parameters, if
     * id not null, then call method {@code initCallManaging()}
     * else, specify operation that need to be done, depends on current status of order
     * @param request HttpServletRequest object
     * @return URL of the JSP page depends on result of updating order
     */
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

        return processOrder();
    }

    /**
     *Gets Order object by id, also gets full name of master
     * @param request HttpServletRequest object
     * @param id order id
     */
    private void initCallManaging(HttpServletRequest request ,String id){
             managingOrder = orderService.findOrderByID(Integer.parseInt(id));
            logger.debug("Order id = {}", id);
            master = userService.getUserById(managingOrder.getMaster().getId()).get().getFullName();
        logger.debug("obtained order: {}", managingOrder);
        setJspAttribute(request);
    }

    /**
     * Specifies operation that need to be done depends on status of current order
     * @return URL of JSP page
     */
    public String processOrder(){

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
