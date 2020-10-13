package ua.kukhtar.model.dao;

import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {
   Optional<User> findByLogin(String name);

   int getUserID(String name);

   List<User> findByRole(User.ROLE role);

   List<Order> getOrders(String name);

   void setBankAccount(String name, String account);
}
