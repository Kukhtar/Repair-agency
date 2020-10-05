package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService service ;
    private static boolean isInit = true;

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("name") != null){
            logger.debug("logged user trying to access logIn page");
            CommandUtility.logOut(request);
            //todo: print correct error massage
        }

        if (isInit){
            isInit = false;
            return "/jsp/registration.jsp";
        }

        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        String fullName = request.getParameter("full-name");
        String phoneNumber = request.getParameter("phone-number");


        if( name.equals("") ||  pass.equals("")  || phoneNumber.equals("") ){
            logger.debug("some field is not filled");
            request.setAttribute("massage", "Please enter required data");
            return "/jsp/registration.jsp";
        }

        if (!service.isLoginFree(name)){
            logger.debug("name: {} is not available", name);
            request.setAttribute("massage", "This username is not available");
        }


        return "/jsp/registration.jsp";
    }


}
