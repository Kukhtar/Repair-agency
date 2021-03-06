package ua.kukhtar.model.dao;

import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {
   Optional<User> findByLogin(String name);

   List<User> findByRole(User.ROLE role);

   List<Order> getActiveOrders(String name);

   List<Order> getClosedOrders(String name);

}
