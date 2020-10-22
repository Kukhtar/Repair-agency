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

/**
 * Implements Command interface, implements logic of managing order,
 * manager can  confirm order: set master and price
 * or           cancel order: return money if order was paid, or just change status to CANCELED
 */
public class OrderManagingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(OrderManagingCommand.class);
    private final OrderService orderService;
    private final UserService userService;

    private Order managingOrder;
    private Map<Integer, String> masters;
    private static final String SHOULD_SPECIFY_MASTER_AND_PRICE = "massage.youShouldToSpecify";
    private static final String WRONG_PRICE = "massage.wrongPrice";
    public OrderManagingCommand(OrderService orderService, UserService userService)
    {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * gets order id from query parameters, if id = null and wasn't set earlier - returns exception
     * if id is obtained then calls {@code initCallManaging()}, sets Order object to the
     * variable {@code managingOrder} and returns URL of this page
     * if Order was set earlier then calls method {@code processOrder()}  to specify operation
     * @param request HttpServletRequest object
     * @return URL of the JSP page for managing order
     */
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

    /** gets HashMap of all master id and master full name,
     *  and sends it to the JSP, also gets Order object by id and sends it to the JSP
     * @param request HttpServletRequest object
     * @param id id of order
     */
        private void initCallManaging(HttpServletRequest request ,String id){
             managingOrder = orderService.findOrderByID(Integer.parseInt(id));
            logger.debug("Order id = {}", id);
            masters = userService.getMapOfMasters();
        logger.debug("obtained order: {}", managingOrder);
        setJspAttribute(request);
    }

    /**
     * Specify operation that should be done by status of managing order
     * @param request HttpServletRequest object
     * @return URL of JSP pages that was obtained depending on operation that was specified
     */
    public String processOrder(HttpServletRequest request){
        if (managingOrder.getStatus() == STATUS.WAITING_FOR_RESPONSE){
            return confirmOrder(request);
        }

        return cancelOrder(request);
    }

    /**
     * gets master id and price from form from JSP page,
     * check if data is valid and if true update order info
     * @param request HttpServletRequest object
     * @return URL to the current JSP
     */
    private String confirmOrder(HttpServletRequest request) {
        String  masterIdString = request.getParameter("master");
        String  priceString = request.getParameter("price");

        if (priceString == null || masterIdString == null){
            setJspAttribute(request);
            request.setAttribute("massage", SHOULD_SPECIFY_MASTER_AND_PRICE);
            return "/manager/order_manage.jsp";
        }
        int price = Integer.parseInt(priceString);

        if (price<=0){
            setJspAttribute(request);
            request.setAttribute("massage", WRONG_PRICE);
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

    /**
     * checks if order was paid already, if true return URL to page for pay back,
     * if false just change status of order to canceled
     * @param request HttpServletRequest object
     * @return URL of JSP page
     */
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
