package ua.kukhtar.controller.command.consumer;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class PaymentCommand implements Command {
    private final OrderService orderService;
    private final UserService userService;
    public PaymentCommand(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {

        String bankAccount = request.getParameter("bankAccount");
        if (!isValid(bankAccount)){
            request.setAttribute("massage", "wrong account format");
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

    private boolean isValid(String number){
        if (number.contains("[^0-9]") || number.length()!=12){
            return false;
        }

        return true;
    }
}
