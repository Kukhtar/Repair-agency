package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService service ;
    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        //todo: add validation to the special method

        String name = request.getParameter("name");
        String pass = request.getParameter("password");


        if (!isValid(name, pass, request)){
            return "/jsp/login.jsp";
        }

        Optional<User> user = service.login(name);
        //TODO: add password encrypting
        if( user.isPresent() && user.get().getPassword().equals(pass)){
            if (CommandUtility.checkUserIsLogged(request, name)){
                return "/jsp/error.jsp";
            }

            request.getSession().setAttribute("name" , name);
            request.getSession().setAttribute("role" , user.get().getRole().name());
            logger.info("user "+ name+" logged successfully.");
            return CommandUtility.getHomePageForUser(user.get().getRole());

        }
        logger.info("Invalid attempt of login user:{}", name);
        return "/jsp/login.jsp";
    }

    private boolean isValid(String name, String pass, HttpServletRequest request){
        if (name == null)
            return false;
        else if (name.isEmpty() || pass.isEmpty()){
            request.setAttribute("massage", "Please enter required data");
        }

        return true;
    }
}
