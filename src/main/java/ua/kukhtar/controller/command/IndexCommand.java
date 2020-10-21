package ua.kukhtar.controller.command;

import ua.kukhtar.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class IndexCommand implements Command {
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
