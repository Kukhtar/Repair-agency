package ua.kukhtar.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

/**
 * Listener that process when session is destroyed and than delete this user
 * from list of HashSet of logged users that is stored in ServletContext
 */
public class SessionListener  implements HttpSessionListener {
    private final Logger logger = LogManager.getLogger(SessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    /**
     * Removes users from list of logged users when session is destroyed
     * @param httpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        String userName = (String) httpSessionEvent.getSession()
                .getAttribute("name");
        loggedUsers.remove(userName);

        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
