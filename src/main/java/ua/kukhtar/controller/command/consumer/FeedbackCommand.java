package ua.kukhtar.controller.command.consumer;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * FeedbackCommand class implements Command interface and adds
 * ability for user to leave a feedback for chosen order
 */
public class FeedbackCommand implements Command {
    private final OrderService orderService;
    public FeedbackCommand(OrderService orderService){
        this.orderService = orderService;

    }

    /**
     * Gets feedback string and id of order and invoke
     * method updateFeedBack on OrderService object
     * @param request
     * @return URL of JSP page with closed orders of this user
     */
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
