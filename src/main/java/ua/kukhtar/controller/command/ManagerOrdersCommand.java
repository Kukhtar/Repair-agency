package ua.kukhtar.controller.command;

import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ManagerOrdersCommand implements Command {
    private final OrderService service;
    public ManagerOrdersCommand(OrderService service){
        this.service = service;
    }
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("orders", service.getAllOrders());
        return "redirect:/manager/orders.jsp";
    }
}
