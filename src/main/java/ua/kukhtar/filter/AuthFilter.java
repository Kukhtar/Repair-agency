package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    Logger logger = LogManager.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        if (path.contains("consumer")) {
            if (((HttpServletRequest) servletRequest).getSession().getAttribute("consumer") != null) {
                logger.info("Access confirmed");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().append("AccessDenied");
                return;
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


    @Override
    public void destroy() {

    }
}
