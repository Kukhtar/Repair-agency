package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that check if the current user have access to the page he want to get
 */
public class AuthFilter implements Filter {
    Logger logger = LogManager.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Takes from URL required role, and check if the user have this role,
     * if no, than redirect to log in page, or print error massage
     * @param servletRequest request from user
     * @param servletResponse response
     * @param filterChain filter chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        String sessionRole = (String) request.getSession().getAttribute("role");
        User.ROLE requiredRole;

        if (path.contains("manager")) {
            requiredRole = User.ROLE.MANAGER;
        }else if (path.contains("user")){
            requiredRole = User.ROLE.USER;
        }else if (path.contains("master")){
            requiredRole = User.ROLE.MASTER;
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(isAccessible(sessionRole, requiredRole)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (sessionRole==null || sessionRole.equals("")){
            response.sendRedirect(request.getContextPath() + "/app/login");
            return;
        }
        servletResponse.getWriter().append("AccessDenied");
    }

    private boolean isAccessible(String sessionRole, User.ROLE requiredRole){
        if ((requiredRole.name().equals(sessionRole))) {
            logger.info("Access confirmed");
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
