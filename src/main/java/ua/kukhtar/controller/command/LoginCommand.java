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
        //todo: refactor this s**t

        if (request.getSession().getAttribute("name") != null){
            logger.debug("logged user trying to access logIn page");
            CommandUtility.logOut(request);
            //todo: print correct error massage
        }

        String name = request.getParameter("name");
        String pass = request.getParameter("password");


        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            logger.debug("first init of logIn page");
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

}
