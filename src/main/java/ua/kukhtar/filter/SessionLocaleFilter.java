package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    private final static Logger logger = LogManager.getLogger(SessionLocaleFilter.class);
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        logger.debug("SessionLocaleFilter successfully run");
        if (req.getParameter("sessionLocale") != null) {

            logger.debug("Lang parameter has been changed to: {}", req.getParameter("sessionLocale"));
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }else{
            logger.debug("Lang parameter has not been changed to: {} , {}", req.getParameter("sessionLocale"), req.getContextPath());
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}
    public void init(FilterConfig arg0) throws ServletException {}
}