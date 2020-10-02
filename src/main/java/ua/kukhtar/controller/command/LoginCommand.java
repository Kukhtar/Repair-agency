package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService service ;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            return "/jsp/login.jsp";
        }
        Optional<User> user = service.login(name);
        if( user.isPresent() && user.get().getPassword().equals(pass)){
            request.getSession().setAttribute("consumer" , user.get());
            logger.info("user "+ name+" logged successfully.");
            return "redirect:/jsp/consumerPage.jsp";

        }
        logger.info("Invalid attempt of login user:'"+ name+"'");
        return "/jsp/login.jsp";
    }
}
