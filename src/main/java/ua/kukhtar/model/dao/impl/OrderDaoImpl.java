package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.entity.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;

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
    public void create(Order order) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_ORDER)){

            statement.setInt(1, order.getCustomer().getId());
            statement.setDate(2, Date.valueOf(order.getDate()));
            statement.setInt(3, order.getAddress().getId());
            logger.debug("order created: {}", order);
            statement.execute();


        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
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
