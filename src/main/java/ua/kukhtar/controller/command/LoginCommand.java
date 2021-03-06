package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Implements Command interface, implements logic of logging in,
 * User enter his login and password and if he was registered earlier he gets access to pages
 * that are specified for his role
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService service ;
    private String massage ;
    private static final String ENTER_ALL_DATA = "message.enterAllData";
    private static final String WRONG_LOGIN_OR_PASSWORD = "message.wrongLogin";


    public LoginCommand(UserService service) {
        this.service = service;
    }

    /**
     * Gets login and password from session and tries to find match in db,
     * if success return home page for this user, else return error massage
     * @param request HttpServletRequest object that came from controller servlet
     * @return URL of JSP page
     */
    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter("name");
        String pass = request.getParameter("password");


        if (!isValid(name, pass)){
            request.setAttribute("massage", massage);
            return "/jsp/login.jsp";
        }

        Optional<User> user = service.getUserByLogin(name);
        //TODO: add password encrypting
        if( user.isPresent() && user.get().getPassword().equals(pass)){
            if (CommandUtility.checkUserIsLogged(request, name)){
                return "/jsp/error.jsp";
            }

            request.getSession().setAttribute("name" , name);
            request.getSession().setAttribute("id" , user.get().getId());
            logger.debug("logged user id {}", user.get().getId());
            request.getSession().setAttribute("role" , user.get().getRole().name());
            logger.info("user "+ name+" logged successfully.");
            return CommandUtility.getHomePageForUser(user.get().getRole());

        }
        logger.info("Invalid attempt of login user:{}", name);
        request.setAttribute("massage", WRONG_LOGIN_OR_PASSWORD);
        return "/jsp/login.jsp";
    }

    /**
     * Checks if login and password is valid
     * @param name user's login
     * @param pass user's password
     * @return boolean result of validation
     */
    private boolean isValid(String name, String pass){
        if (name == null)
            return false;
        else if (name.isEmpty() || pass.isEmpty()){
            massage = ENTER_ALL_DATA;
            return false;
        }

        return true;
    }
}
