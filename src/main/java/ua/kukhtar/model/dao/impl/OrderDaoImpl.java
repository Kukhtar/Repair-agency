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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    private final DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * gets connection from Connection Pool
     * @return Connection object
     * @throws SQLException if can't get connection
     */
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * gets data from result, then creates from it new Order object
     * @param resultSet
     * @return Address object
     * @throws SQLException
     */
    public static Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setStatus(STATUS.valueOf(resultSet.getString("status")));
        order.setId(resultSet.getInt("order_id"));
        order.setPrice(resultSet.getInt("price"));
        logger.debug("date = {} OR {}", resultSet.getDate("date"), resultSet.getDate("date").getTime());
        order.setDate(resultSet.getDate("date").toLocalDate());

        return order;
    }

    /**
     * Executes SQL query and return list of active order generated from result set
     * @param start
     * @param count
     * @return
     */
    @Override
    public List<Order> findActive(int start, int count) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_ACTIVE_ORDERS)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                Order order;
                while (resultSet.next()){
                    order = getOrder(resultSet);
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
    public List<Order> findClosed(int start, int count) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_CLOSED_ORDERS)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);

            try(ResultSet resultSet = preparedStatement.executeQuery()){

                Order order;
                while (resultSet.next()){
                    order = getOrder(resultSet);
                    order.setFeedBack(resultSet.getString("feedback"));
                    orders.add(order);
                }
            }

            return orders;

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    private Order getOrder(ResultSet resultSet) throws SQLException {
        Order order;
        User user;
        User master;
        order = extractOrderFromResultSet(resultSet);
        order.setAddress(AddressDaoImpl.extractAddressFromResultSet(resultSet));

        user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFullName(resultSet.getString("full_name"));

        master = new User();
        master.setId(resultSet.getInt("master_id"));

        order.setMaster(master);
        order.setCustomer(user);
        return order;
    }

    @Override
    public int create(Order order) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_INITIAL_ORDER, Statement.RETURN_GENERATED_KEYS)){

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
        try(Connection connection = getConnection();
            PreparedStatement updateMaster = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_ORDER)) {
            updateMaster.setInt(1, order.getMaster().getId());
            updateMaster.setObject(2, order.getStatus(), Types.OTHER);
            updateMaster.setInt(3, order.getPrice());
            updateMaster.setInt(4, order.getId());

            updateMaster.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateFeedback(Order order) {
        try(Connection connection = getConnection();
            PreparedStatement updateFeedback = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_FEEDBACK)) {
            updateFeedback.setString(1, order.getFeedBack());
            updateFeedback.setInt(2, order.getId());

            updateFeedback.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete( Order object) {
    }

    /**
     * Updates status of order in db, and if Status was changed to CLOSED or DONE than call
     * method {@code closeOrder()} to delete this order from table orders, and create it in table closedOrders
     * Implements transaction, to rollback if something go wrong
     * @param order
     */
    @Override
    public void updateStatus(Order order) {
        Connection connection;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_STATUS)){

            statement.setObject(1, order.getStatus(), Types.OTHER);
            statement.setInt(2, order.getId());

            statement.executeUpdate();

            if (order.getStatus()== STATUS.CANCELED || order.getStatus()== STATUS.DONE){
                closeOrder(connection, order);
            }

            connection.commit();
            logger.debug("Transaction was successful");
        } catch (SQLException e) {
            logger.error(e);
            try {
                logger.debug("Transaction is being rolled back");
                connection.rollback();
            } catch (SQLException excep) {
                logger.error(excep);
                throw new IllegalStateException(excep);
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Remove order from table orders, and create it in table closedOrders,
     * this method is part of transaction, so it just take open connection and don;t close it
     * @param connection
     * @param order
     * @throws SQLException
     */
    private void closeOrder(Connection connection, Order order) throws SQLException {
        try(PreparedStatement deleteOrder = connection.prepareStatement(SQLQueryConstant.SQL_DELETE_ORDER_BY_ID);
            PreparedStatement insertOrder = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_CLOSED_ORDER)) {

            connection.setAutoCommit(false);

            deleteOrder.setInt(1, order.getId());

            insertOrder.setInt(1,order.getCustomer().getId());
            insertOrder.setInt(2,order.getMaster().getId());
            insertOrder.setObject(3,order.getStatus(), Types.OTHER);
            insertOrder.setDate(4, Date.valueOf(order.getDate()));
            insertOrder.setInt(5,order.getAddress().getId());
            insertOrder.setInt(6,order.getPrice());

            deleteOrder.executeUpdate();
            insertOrder.executeUpdate();

        }
    }

    @Override
    public int countOfActiveOrders(){
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueryConstant.SQL_GET_COUNT_OF_ACTIVE_ORDERS)){

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt("count");
                }else{
                    logger.error("can't get count of active orders");
                    throw new IllegalStateException("Can't get count of active orders");
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int countOfClosedOrders(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueryConstant.SQL_GET_COUNT_OF_CLOSED_ORDERS)){

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getInt("count");
                }else{
                    logger.error("can't get count of active orders");
                    throw new IllegalStateException("Can't get count of active orders");
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }
}
