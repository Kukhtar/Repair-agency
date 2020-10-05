package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.User;

import java.util.Optional;

public class UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.createUserDao();

    public Optional<User> login(String login){
        Optional<User> result;
        result = userDao.findByLogin(login);
        return result;
    }

    public boolean isLoginFree(String login){
        int id = userDao.getUserID(login);

        return id==-1;
    }
}
