package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements Command interface, implements registration logic, User enter
 * login, password and other information, and if all data is valid new user is created in db,
 * and user is redirected to user home page
 */
public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final UserService service;
    private String massage ;
    private static final String ENTER_ALL_DATA = "message.enterAllData";

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    /**
     * Gets all data from registration form, invokes method {@code isValid()}
     * and result is true creates new user records in db, and send URL of user home page
     * else send error massage
     * @param request HttpServletRequest object that came from controller servlet
     * @return
     */
    @Override
    public String execute(HttpServletRequest request) {

        if (request.getSession().getAttribute("name") != null) {
            logger.debug("logged user trying to access logIn page");
            CommandUtility.logOut(request);
        }

        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        String fullName = request.getParameter("full-name");
        String phoneNumber = request.getParameter("phone-number");


        if (!isValid(name, pass, fullName, phoneNumber)) {
            request.setAttribute("massage", massage);
            return "/jsp/registration.jsp";
        }

        request.getSession().setAttribute("name", name);
        request.getSession().setAttribute("role", User.ROLE.USER.name());
        User user = new User();
        user.setLogin(name);
        user.setPassword(pass);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);

        try {
            int id = service.addUser(user);
            request.getSession().setAttribute("id" , id);

        } catch (Exception e) {
            logger.error(e);
            request.setAttribute("massage","message.userNameNotAvailable");
            return "/jsp/registration.jsp";
        }


        logger.info("created user object: {}", user);
        return "redirect:/user/index.jsp";
    }


    private boolean isValid(String name, String pass, String fullName, String phoneNumber) {
        if (name == null)
            return false;
        else if (name.isEmpty() || pass.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()) {
            massage = ENTER_ALL_DATA;
            return false;
        }

        return true;
    }
}
