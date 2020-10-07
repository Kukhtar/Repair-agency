package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.entity.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void create(Order object) {

    }

    @Override
    public Order read(int id) {
        return null;
    }

    @Override
    public void update(Order object) {

    }

    @Override
    public void delete(Order object) {

    }
}
