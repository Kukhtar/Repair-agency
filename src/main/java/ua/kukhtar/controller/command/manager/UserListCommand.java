package ua.kukhtar.controller.command.manager;

import ua.kukhtar.controller.command.Command;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserListCommand implements Command {
    private final UserService service;
    public UserListCommand(UserService service){
        this.service = service;
    }
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("users", service.getAllUsers());
        return "redirect:/manager/consumers.jsp";
    }
}
