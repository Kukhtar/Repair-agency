package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final UserService service;
    private String massage ;
    private static final String ENTER_ALL_DATA = "message.enterAllData";

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

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
            service.addUser(user);
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
            //todo: add phone number validation
        else if (name.isEmpty() || pass.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()) {
            //todo: add to resource bundle
            massage = ENTER_ALL_DATA;
            return false;
        }

        return true;
    }
}
