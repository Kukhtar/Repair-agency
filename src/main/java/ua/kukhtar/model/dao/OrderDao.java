package ua.kukhtar.model.dao;

import ua.kukhtar.model.dao.CrudDao;
import ua.kukhtar.model.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDao<Order> {
    List<Order> findAll();
}
