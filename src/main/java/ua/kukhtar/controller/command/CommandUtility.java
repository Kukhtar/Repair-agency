package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * Utility class, that contains some useful methods for Commands classes
 */
public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    /**
     * Erases all information about logged user from session, also delete
     * user name from HashSet of oll logged users, to not prevent user login in future
     * @param request HttpServletRequest object
     */
    public static void logOut(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("name");

        request.getSession().setAttribute("name", null);
        request.getSession().setAttribute("role", null);
        request.getSession().setAttribute("id", null);

        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");

        logger.debug("logOut user: {}", name);

        loggedUsers.remove(name);
    }

    /**
     * Checks if HashSet of all logged users contains userName
     * passed as parameter, if true return true, else
     * set add this name to HashSet and return false
     * @param request
     * @param userName
     * @return boolean result of checking if user is already logged
     */
    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    /**
     * Specifies home page that for given user role
     * @param role Role of user
     * @return URL of home page for this user
     */
    public static String getHomePageForUser(User.ROLE role){
        if (role == User.ROLE.USER){
            return "redirect:/user/index.jsp";
        }else if (role == User.ROLE.MANAGER){
            return "redirect:/manager/index.jsp";
        }else if (role == User.ROLE.MASTER){
            return "redirect:/master/index.jsp";
        }

        return "app/login";
    }
}
