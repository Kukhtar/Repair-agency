package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Gets active connection from connection pool
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Gets data from result set and return new User object
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setFullName(resultSet.getString("full_name"));
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setRole(User.ROLE.valueOf(resultSet.getString("role")));
        user.setBankAccount(resultSet.getString("bank_account"));
        return user;
    }

    @Override
    public int create(User user) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getFullName());
            statement.setString(4,user.getPhoneNumber());
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            logger.debug("user {} added", user);
            return resultSet.getInt(1);


        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String  login) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_USER_BY_LOGIN)) {
            statement.setString(1, login);

            return getUser(statement);

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

    }

    @Override
    public Optional<User> read(int id) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_USER_BY_ID)) {
            statement.setInt(1, id);

            return getUser(statement);

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    private Optional<User> getUser(PreparedStatement statement) throws SQLException {
        try(ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()){
                User user = extractUserFromResultSet(resultSet);
                logger.debug("User obtained from db {}", user);
                return Optional.of(user);
            }
        }
        logger.debug("Can't find user by login");
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        try (Connection connection = getConnection();
             PreparedStatement updateUser = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_CONSUMER)){
            logger.debug("inserting bank account ");

            updateUser.setString(1, user.getBankAccount());
            updateUser.setInt(2, user.getId());
            updateUser.executeUpdate();

            logger.debug("end of inserting account");
        }  catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(User object) {

    }


    @Override
    public List<User> findByRole(User.ROLE role) {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_USERS_BY_ROLE)) {
            statement.setObject(1, role.name(), Types.OTHER);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    users.add(extractUserFromResultSet(resultSet));
                }
            }
            return users;
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Order> getActiveOrders(String name) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_USER_ACTIVE_ORDERS)) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {

                Order order;
                while (resultSet.next()) {
                    order = OrderDaoImpl.extractOrderFromResultSet(resultSet);
                    User user = new User();
                    user.setFullName(resultSet.getString("full_name"));
                    order.setCustomer(user);
                    order.setAddress(AddressDaoImpl.extractAddressFromResultSet(resultSet));
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
    public List<Order> getClosedOrders(String name) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_USER_CLOSED_ORDERS)) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {

                Order order;
                while (resultSet.next()) {
                    order = OrderDaoImpl.extractOrderFromResultSet(resultSet);
                    User user = new User();
                    user.setFullName(resultSet.getString("full_name"));
                    order.setCustomer(user);
                    order.setAddress(AddressDaoImpl.extractAddressFromResultSet(resultSet));
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



}
