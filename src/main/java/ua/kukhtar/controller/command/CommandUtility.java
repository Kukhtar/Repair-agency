package ua.kukhtar.controller.command;

import ua.kukhtar.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class CommandUtility {
    public static void logOut(HttpServletRequest request, String name){
        request.getSession().setAttribute("name", null);
        request.getSession().setAttribute("role", null);

        //todo: delete from list of all logged users
    }

    public static String getHomePageForUser(User.ROLE role){
        if (role == User.ROLE.USER){
            return "redirect:/user/consumerPage.jsp";
        }else if (role == User.ROLE.MANAGER){
            return "redirect:/manager/managerPage.jsp";
        }
        return "redirect:/master/masterPage.jsp";
    }
}
