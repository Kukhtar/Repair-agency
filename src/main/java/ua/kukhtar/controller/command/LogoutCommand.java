package ua.kukhtar.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements Command interface, implements logic of log out process
 */
public class LogoutCommand implements Command{
    /**
     * invoke {@code logOut()} method from CommandUtility
     * @param request HttpServletRequest object that came from controller servlet
     * @return
     */
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.logOut(request);
        return "redirect:/app/";
    }
}
