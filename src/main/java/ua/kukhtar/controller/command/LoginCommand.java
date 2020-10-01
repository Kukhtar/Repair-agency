package ua.kukhtar.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.service.ConsumerService;
import ua.kukhtar.model.user.Consumer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private ConsumerService service ;

    public LoginCommand(ConsumerService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            return "/jsp/login.jsp";
        }
        Optional<Consumer> consumer = service.login(name);
        if( consumer.isPresent() && consumer.get().getAccount().getPassword().equals(pass)){
            request.getSession().setAttribute("consumer" , consumer.get());
            logger.info("consumer "+ name+" logged successfully.");
            return "redirect:/jsp/consumerPage.jsp";

        }
        logger.info("Invalid attempt of login user:'"+ name+"'");
        return "/jsp/login.jsp";
    }
}
