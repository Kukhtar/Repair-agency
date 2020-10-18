package ua.kukhtar.controller.command.consumer;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;
import ua.kukhtar.model.service.OrderService;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class FeedbackCommand implements Command {
    private final OrderService orderService;
    public FeedbackCommand(OrderService orderService){
        this.orderService = orderService;

    }
    @Override
    public String execute(HttpServletRequest request) {

        String feedback = request.getParameter("feedback");
        int id = Integer.parseInt(request.getParameter("id"));

        Order order = new Order();
        order.setId(id);
        order.setFeedBack(feedback);
        orderService.updateFeedback(order);
        return "redirect:/app/user/closed_orders";

    }
}
