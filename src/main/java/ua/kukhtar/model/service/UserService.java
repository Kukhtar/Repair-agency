package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.dao.impl.UserDaoImpl;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.createUserDao();

    public Optional<User> getUser(String login){
        Optional<User> result;
        result = userDao.findByLogin(login);
        return result;
    }

    public boolean isLoginFree(String login){
        int id = userDao.getUserID(login);
        return id==-1;
    }
    public int getUserId(String login){
        int id = userDao.getUserID(login);
        return id;
    }

    public void addUser(User user){
        userDao.create(user);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public List<Order> getOrders(String name){
        return userDao.getOrders(name);
    }
}
