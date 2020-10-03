package ua.kukhtar.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("name");
        CommandUtility.logOut(request, name);
        return "redirect:/jsp/index.jsp";
    }
}
