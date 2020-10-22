package ua.kukhtar.controller.command.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * CreateOrderCommand class implements Command interface and implements
 * logic of creating new order by current user, also does validation of gotten data,
 * and displaying error massage if data is not valid
 */
public class CreateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CreateOrderCommand.class);
    private final OrderService service ;
    private String massage;
    private static final String WRONG_ADDRESS_FORMAT = "massage.wrongAddressFormat";
    public CreateOrderCommand(OrderService service) {
        this.service = service;
    }

    
    /**
     * This method gets HttpServletRequest object from which it takes data that user enter in
     * form, than does validation, if success: create new Order and return URL of user home page,
     * else send error massage to JSP and return URL to current page
     * @param request HttpServletRequest object
     * @return URL that represent result of creating new Order
     */
    @Override
    public String execute(HttpServletRequest request) {
        String userName = (String)request.getSession().getAttribute("name");
        String flatNumber = request.getParameter("flat_number");
        String houseNumber = request.getParameter("house_number");

        if (!isValid(flatNumber, houseNumber)){
            request.setAttribute("massage", massage);
            return "/user/create_order.jsp";
        }

        Address address = new Address();
        address.setFlatNumber(Integer.parseInt(flatNumber));
        address.setHouseNumber(Integer.parseInt(houseNumber));

        service.createOrder(address, userName);
        return "redirect:/user/index.jsp";
    }

    /**
     * Check if the input data two numbers that is greater then zero, if so return true
     * else set error massage as a attribute and return false
     * @param flatNumber
     * @param houseNumber
     * @return boolean result of validation
     */
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
            massage = WRONG_ADDRESS_FORMAT;
            return false;
        }

        if (flat <=0 || house <=0 ){
            massage = WRONG_ADDRESS_FORMAT;
            return false;
        }

        return true;
    }
}
