package ua.kukhtar.model.dao;

import ua.kukhtar.model.dao.CrudDao;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;

import java.sql.Connection;
import java.util.List;

public interface OrderDao extends CrudDao<Order> {
    List<Order> findActive(int start, int count);

    List<Order> findClosed(int start, int count);

    void updateStatus(Order order);

    void updateFeedback(Order order);

    int countOfActiveOrders();

    int countOfClosedOrders();
}
