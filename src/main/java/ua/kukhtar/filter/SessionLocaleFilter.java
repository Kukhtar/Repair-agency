package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that process when user want to change language of interface
 */
public class SessionLocaleFilter implements Filter {
    private final static Logger logger = LogManager.getLogger(SessionLocaleFilter.class);

    /**
     * Checks if query string contain lang parameter, if so than take this parameter sets it
     * as a session parameter, so every JSP page can get from session and choose appropriate language
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        logger.debug("SessionLocaleFilter successfully run");
        if (req.getParameter("sessionLocale") != null) {

            logger.debug("Lang parameter has been changed to: {}", req.getParameter("sessionLocale"));
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
            //todo: language shouldn't change on back button pushed
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}
    public void init(FilterConfig arg0) throws ServletException {}
}