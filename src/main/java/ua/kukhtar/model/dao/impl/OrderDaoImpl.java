package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;
import ua.kukhtar.model.entity.enums.STATUS;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

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
        order.setPrice(resultSet.getInt("price"));
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
    public int create(Order order) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, order.getCustomer().getId());
            statement.setDate(2, Date.valueOf(order.getDate()));
            statement.setInt(3, order.getAddress().getId());
            logger.debug("order created: {}", order);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            logger.debug("order {} added", order);
            return resultSet.getInt(1);

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Order> read(int id) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_ORDER_BY_ID)) {

            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    User master = new User();
                    master.setId(resultSet.getInt("master_id"));

                    User customer = new User();
                    customer.setId(resultSet.getInt("id"));
                    customer.setFullName(resultSet.getString("full_name"));

                    Address address = AddressDaoImpl.extractAddressFromResultSet(resultSet);

                    Order order = extractOrderFromResultSet(resultSet);
                    order.setAddress(address);
                    order.setMaster(master);
                    order.setCustomer(customer);
                    logger.debug("order obtained from db {}", order);
                    return Optional.of(order);
                }
            }

            logger.debug("Can't find user by login");
            return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void update(Order order) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

        try(PreparedStatement updateMaster = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_MASTER_ID);
            PreparedStatement updateStatus = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_STATUS);
            PreparedStatement updatePrice = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_PRICE)) {

            connection.setAutoCommit(false);

            updateMaster.setInt(1, order.getMaster().getId());
            updateMaster.setInt(2, order.getId());

            updateStatus.setObject(1, order.getStatus().name(), Types.OTHER);
            updateStatus.setInt(2, order.getId());

            updatePrice.setInt(1, order.getPrice());
            updatePrice.setInt(2, order.getId());

            updateMaster.executeUpdate();
            updateStatus.executeUpdate();
            updatePrice.executeUpdate();

            connection.commit();
            logger.debug("Transaction was successful");

        } catch (SQLException e) {
            logger.error(e);
            try {
                logger.debug("Transaction is being rolled back");
                connection.rollback();
            } catch (SQLException excep) {
                logger.error(excep);
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e);
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void delete(Order object) {

    }

    @Override
    public void updateStatus(Order order) {
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_STATUS)){

            statement.setObject(1, order.getStatus(), Types.OTHER);
            statement.setInt(2, order.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }
}
