package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.UserDao;
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

    private Connection getConncetion() throws SQLException {
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
        return user;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_USER_BY_LOGIN)) {

            statement.setString(1, login);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    User user = extractUserFromResultSet(resultSet);
                    logger.debug("User obtained from db {}", user);
                    return Optional.of(user);
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
    public void create(User user) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_ADD_USER)) {

            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getFullName());
            statement.setString(4,user.getPhoneNumber());

            statement.execute();
            logger.debug("User successfully added");

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User read(int id) {
        return null;
    }

    @Override
    public void update(User object) {

    }

    @Override
    public void delete(User object) {

    }

    @Override
    public int getUserID(String name) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_USER_ID_BY_LOGIN)) {

            statement.setString(1, name);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    int id = resultSet.getInt("id");
                    logger.debug("User id for {} = {}", name, id);
                    return id;
                }
            }

            logger.debug("Can't find user by login");
            return -1;

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try(Connection connection = getConncetion();
            Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery(SQLQueryConstant.SQL_FIND_ALL_USERS)){

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
}
