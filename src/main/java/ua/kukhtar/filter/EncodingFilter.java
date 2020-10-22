package ua.kukhtar.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter that sets encoding UTF-8 for all pages, for correct displaying of cyrillic text
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * sets encoding UTF-8 for all pages, for correct displaying of cyrillic text
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
