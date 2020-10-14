package ua.kukhtar.controller.command.consumer;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
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

        String name = (String)request.getSession().getAttribute("name");
        userService.setBankAccount(name, bankAccount);

        int id = Integer.parseInt(request.getParameter("id"));

        Order order = new Order();
        order.setId(id);
        orderService.payForOrder(order);
        return "redirect:/app/user/orders";
    }

    private boolean isValid(String number){
        if (number.contains("[^0-9]") || number.length()!=12){
            return false;
        }

        return true;
    }
}
