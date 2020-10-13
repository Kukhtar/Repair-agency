package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return userDao.findByRole(User.ROLE.USER);
    }

    public List<Order> getOrders(String name){
        return userDao.getOrders(name);
    }

    public Map<Integer, String> getMapOfMasters(){
        List<User> masters = userDao.findByRole(User.ROLE.MASTER);

        Map<Integer, String> mastersMap = masters.stream().collect(Collectors.toMap(User::getId, User::getFullName));
        return mastersMap;
    }

    public void setBankAccount(String name, String account){
        userDao.setBankAccount(name, account);
    }
}
