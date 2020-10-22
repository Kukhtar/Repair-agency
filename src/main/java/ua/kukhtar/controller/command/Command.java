package ua.kukhtar.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * General template for classes than should implement logic of
 * processing some HttpRequest
 */
public interface Command {
    /**
     * @param request HttpServletRequest object that came from controller servlet
     * @return return URL of result JSP page
     */
    String execute(HttpServletRequest request);
}
