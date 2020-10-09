package ua.kukhtar.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.logOut(request);
        return "redirect:/app/";
    }
}