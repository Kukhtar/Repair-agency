package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.User;

import java.util.Optional;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<User> login(String login){
        Optional<User> result;
        UserDao userDao = daoFactory.createUserDao();
        result = userDao.findByLogin(login);
        return result;
    }
}
