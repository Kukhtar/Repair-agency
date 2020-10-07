package ua.kukhtar.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    Logger logger = LogManager.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
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

        //todo: add redirect to correct login page (only for guests )
        servletResponse.getWriter().append("AccessDenied");
    }

    private boolean isAccessible(String sessionRole, User.ROLE requiredRole){
        if ((sessionRole != null && requiredRole.name().equals(sessionRole))) {
            logger.info("Access confirmed");
            return true;
        }

        return false;

    }

    @Override
    public void destroy() {

    }
}
