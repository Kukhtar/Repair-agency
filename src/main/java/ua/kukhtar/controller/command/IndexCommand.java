package ua.kukhtar.controller.command;

import ua.kukhtar.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements Command interface, specifies and send URL of home page,
 * that appropriate for user that send that request
 */
public class IndexCommand implements Command {

    /**
     * Gets user role rom session, and return appropriate home page URL
     * @param request HttpServletRequest object that came from controller servlet
     * @return URL of home page
     */
    @Override
    public String execute(HttpServletRequest request) {
        Object role = request.getSession().getAttribute("role");
        if (role == null){
            return "/jsp/index.jsp";
        }
        String roleName = (String)role;
        return CommandUtility.getHomePageForUser(User.ROLE.valueOf(roleName));
    }
}
