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

    public Optional<User> getUserByLogin(String login){
        Optional<User> result;
        result = userDao.findByLogin(login);
        return result;
    }

    public Optional<User> getUserById(int id){
        Optional<User> result;
        result = userDao.read(id);
        return result;
    }


    public int addUser(User user) {
        return userDao.create(user);
    }

    public List<Order> getActiveOrders(String name){
        return userDao.getActiveOrders(name);
    }

    public List<Order> getClosedOrders(String name){
        return userDao.getClosedOrders(name);
    }

    public Map<Integer, String> getMapOfMasters(){
        List<User> masters = userDao.findByRole(User.ROLE.MASTER);

        Map<Integer, String> mastersMap = masters.stream().collect(Collectors.toMap(User::getId, User::getFullName));
        return mastersMap;
    }

    public void updateUser(User user){
        userDao.update(user);
    }
}
