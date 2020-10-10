package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setStatus(STATUS.valueOf(resultSet.getString("status")));
        order.setId(resultSet.getInt("order_id"));
        logger.debug("date = {} OR {}", resultSet.getDate("date"), resultSet.getDate("date").getTime());
        order.setDate(resultSet.getDate("date").toLocalDate());

        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery(SQLQueryConstant.SQL_FIND_ALL_ORDERS)){

                Order order;
                User user;
                while (resultSet.next()){
                    order = extractOrderFromResultSet(resultSet);
                    order.setAddress(AddressDaoImpl.extractAddressFromResultSet(resultSet));

                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFullName(resultSet.getString("full_name"));

                    order.setCustomer(user);
                    orders.add(order);
                }
            }

            return orders;

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
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
