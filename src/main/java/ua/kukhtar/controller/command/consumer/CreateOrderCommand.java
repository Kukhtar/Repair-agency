package ua.kukhtar.controller.command.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.controller.command.LoginCommand;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.IllegalFormatException;

public class CreateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private OrderService service ;
    public CreateOrderCommand(OrderService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userName = (String)request.getSession().getAttribute("name");
        String flatNumber = request.getParameter("flat_number");
        String houseNumber = request.getParameter("house_number");

        if (!isValid(flatNumber, houseNumber)){
            return "/user/create_order.jsp";
        }

        Address address = new Address();
        address.setFlatNumber(Integer.parseInt(flatNumber));
        address.setHouseNumber(Integer.parseInt(houseNumber));

        service.createOrder(address, userName);
        return "redirect:/user/index.jsp";
    }

    private boolean isValid(String flatNumber, String houseNumber){
        if (flatNumber == null){
            return false;
        }

        int flat;
        int house;

        try {
            flat = Integer.parseInt(flatNumber);
            house = Integer.parseInt(houseNumber);
        }catch (NumberFormatException e){
            logger.error(e);
            //todo: send massage to client that date is invalid
            return false;
        }

        if (flat <=0 || house <=0 ){
            return false;
        }

        return true;
    }
}
