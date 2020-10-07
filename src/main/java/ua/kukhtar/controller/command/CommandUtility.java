package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);
    public static void logOut(HttpServletRequest request){
        String name = (String) request.getSession().getAttribute("name");

        request.getSession().setAttribute("name", null);
        request.getSession().setAttribute("role", null);

        //todo: should be in servlet context
        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");

        logger.debug("logOut user: {}", name);

        loggedUsers.remove(name);
    }

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

    public static String getHomePageForUser(User.ROLE role){
        if (role == User.ROLE.USER){
            return "redirect:/user/index.jsp";
        }else if (role == User.ROLE.MANAGER){
            return "redirect:/manager/index.jsp";
        }
        return "redirect:/master/masterPage.jsp";
    }
}
