package ua.kukhtar.controller.command.consumer;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements Command interface, adds user ability to pay for order
 */
public class PaymentCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    private String massage = "massage.";
    private static final String WRONG_BANK_ACCOUNT = "massage.wrongBankAccountFormat";
    public PaymentCommand(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * gets bank account from form and check is it valid, if so
     * set order as PAID and return URL of JSP page with active orders of current user
     * @param request
     * @return
     */
    @Override
    public String execute(HttpServletRequest request) {

        String bankAccount = request.getParameter("bankAccount");
        if (!isValid(bankAccount)){
            request.setAttribute("massage", massage);
            return "/user/payment_page.jsp";
        }

        User user = new User();
        user.setId((Integer)request.getSession().getAttribute("id"));
        user.setBankAccount(bankAccount);
        userService.updateUser(user);

        int id = Integer.parseInt(request.getParameter("id"));

        Order order = new Order();
        order.setId(id);
        order.setStatus(STATUS.PAID);
        orderService.updateStatus(order);
        return "redirect:/app/user/orders";
    }

    /**
     * check if bank account contain 12 digits
     * @param number
     * @return boolean result of validation
     */
    private boolean isValid(String number){
        if (number==null){
            return false;
        }
        if (number.contains("[^0-9]") || number.length()!=12){
            massage = WRONG_BANK_ACCOUNT;
            return false;
        }

        return true;
    }
}
