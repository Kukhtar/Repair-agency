package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.controller.command.CommandUtility;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter that call logOut() method when logged user tries to get access to pages for guest
 */
public class GuestPagesFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(GuestPagesFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Checks if user is already logged, if so than call logOut() method from CommandUtility class
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getSession().getAttribute("name") != null){
            logger.debug("logged user trying to access guest page");
            CommandUtility.logOut(request);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
