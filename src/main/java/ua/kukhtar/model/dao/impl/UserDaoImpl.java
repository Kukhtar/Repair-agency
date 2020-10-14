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

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


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
    public List<Order> getOrders(String name) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_FIND_ALL_ORDERS_OF_USER)) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()){

                Order order;
                while (resultSet.next()){
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

    //should remove this, and add to update method
    @Override
    public void setBankAccount(String name, String bankAccount) {
        Connection connection;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

        try (PreparedStatement getUser = connection.prepareStatement(SQLQueryConstant.SQL_GET_USER_ID_BY_LOGIN);
             PreparedStatement updateUser = connection.prepareStatement(SQLQueryConstant.SQL_UPDATE_CONSUMER)){
            logger.debug("inserting bank account ");
            connection.setAutoCommit(false);
            getUser.setString(1, name);

            int id;
            try(ResultSet resultSet = getUser.executeQuery()){
                if (resultSet.next()){
                    id = resultSet.getInt("id");
                }else {
                    throw new IllegalStateException("Can't find this user");
                }
            }
            logger.debug("inserting bank account ID = {}", id);

            updateUser.setString(1, bankAccount);
            updateUser.setInt(2, id);
            updateUser.executeUpdate();

            logger.debug("end of inserting account");
            connection.commit();
        }  catch (SQLException e) {
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
}
