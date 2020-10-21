package ua.kukhtar.controller.command.manager;

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

public class OrderManagingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(OrderManagingCommand.class);
    private final OrderService orderService;
    private final UserService userService;

    private Order managingOrder;
    private Map<Integer, String> masters;
    private static final String SHOULD_SPECIFY_MASTER_AND_PRICE = "massage.youShouldToSpecify";
    public OrderManagingCommand(OrderService orderService, UserService userService)
    {
        this.orderService = orderService;
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter("order_id");
        if (id!=null) {
            initCallManaging(request, id);
            return "/manager/order_manage.jsp?id=" + id;
        }
        if (managingOrder == null){
            logger.debug("didn't choose order before managing");
            throw new IllegalStateException("Order not founded");
        }

        return processOrder(request);
    }

        private void initCallManaging(HttpServletRequest request ,String id){
             managingOrder = orderService.findOrderByID(Integer.parseInt(id));
            logger.debug("Order id = {}", id);
            masters = userService.getMapOfMasters();
        logger.debug("obtained order: {}", managingOrder);
        setJspAttribute(request);
    }

    public String processOrder(HttpServletRequest request){
        if (managingOrder.getStatus() == STATUS.WAITING_FOR_RESPONSE){
            return confirmOrder(request);
        }

        return cancelOrder(request);
    }

    private String confirmOrder(HttpServletRequest request) {
        String  masterIdString = request.getParameter("master");
        //todo:add price validation, NPE is possible in this place
        int price = Integer.parseInt(request.getParameter("price"));

        if (price == 0 || masterIdString == null){
            setJspAttribute(request);
            request.setAttribute("massage", SHOULD_SPECIFY_MASTER_AND_PRICE);
            return "/manager/order_manage.jsp";
        }
        int masterId = Integer.parseInt(masterIdString);

        User master = new User();
        master.setId(masterId);

        managingOrder.setStatus(STATUS.WAITING_FOR_PAYMENT);
        managingOrder.setMaster(master);
        managingOrder.setPrice(price);

        orderService.updateOrder(managingOrder);

        setJspAttribute(request);
        return "redirect:/app/manager/manage_order?order_id=" + managingOrder.getId();
    }

    private String cancelOrder(HttpServletRequest request){
        setJspAttribute(request);

        managingOrder.setStatus(STATUS.CANCELED);
        orderService.updateStatus(managingOrder);

        if (managingOrder.getStatus()==STATUS.WAITING_FOR_PAYMENT){
            return "redirect:/app/manager/active_orders";
        }

        Optional<User> consumer = userService.getUserById(managingOrder.getCustomer().getId());
        if (consumer.isPresent()){
            request.setAttribute("account", consumer.get().getBankAccount());
            request.setAttribute("userName", consumer.get().getFullName());
        }else {
            logger.error("cant find user by id: {}", managingOrder.getCustomer().getId());
            throw new IllegalStateException("User not found");
        }

        return "/manager/return_money.jsp";
    }

    private void setJspAttribute(HttpServletRequest request){
        request.setAttribute("order", managingOrder);
        request.setAttribute("masters", masters);

        if (managingOrder.getStatus() == STATUS.WAITING_FOR_RESPONSE){
            request.setAttribute("formAction", "Confirm order");
        }else {
            request.setAttribute("formAction", "Cancel order");
        }
    }

}
