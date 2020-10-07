package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService service ;
    private boolean isInit = true;
    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //todo: make special method for getting locale
        Object lang = request.getSession().getAttribute("lang");
        Locale locale = lang==null?Locale.getDefault():new Locale((String)lang);

        ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

        if (request.getSession().getAttribute("name") != null){
            logger.debug("logged user trying to access logIn page");
            CommandUtility.logOut(request);
            //todo: print correct error massage
        }

        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        String fullName = request.getParameter("full-name");
        String phoneNumber = request.getParameter("phone-number");


        if( name == null ||name.equals("") ||  pass == null ||pass.equals("")  || phoneNumber == null ||phoneNumber.equals("") ){
            logger.debug("some field is not filled");
//            request.setAttribute("massage", "Please enter required data");
            return "/jsp/registration.jsp";
        }

        if (!service.isLoginFree(name)){
            logger.debug("name: {} is not available", name);
            request.setAttribute("massage", rb.getString("message.userNameNotAvailable"));
            return "/jsp/registration.jsp";
        }

        request.getSession().setAttribute("name" , name);
        request.getSession().setAttribute("role" , User.ROLE.USER.name());
        User user = new User();
        user.setLogin(name);
        user.setPassword(pass);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);

        service.addUser(user);
        user.setId(service.getUserId(name));
        logger.info("created user object: {}", user);
        return "redirect:/user/index.jsp";
    }


}
