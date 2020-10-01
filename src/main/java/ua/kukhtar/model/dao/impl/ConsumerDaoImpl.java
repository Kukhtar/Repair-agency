package ua.kukhtar.model.dao.impl;

import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.AccountDao;
import ua.kukhtar.model.dao.ConsumerDao;
import ua.kukhtar.model.user.Consumer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConsumerDaoImpl implements ConsumerDao {
    private DataSource dataSource;

    public ConsumerDaoImpl(DataSource dataSource, AccountDao accountDao){
        this.dataSource = dataSource;
    }

    private Connection getConncetion() throws SQLException {
        return dataSource.getConnection();
    }

    static Consumer extractConsumerFromResultSet(ResultSet resultSet) throws SQLException {
        Consumer consumer = new Consumer();
        consumer.setId(resultSet.getInt("consumer_id"));
        consumer.setBankAccount(resultSet.getString("bank_account"));

        return consumer;
    }

    @Override
    public Optional<Consumer> findOptional(int id) {
        return Optional.empty();
    }

    @Override
    public void create(Consumer consumer) {

    }

    @Override
    public void update(Consumer object) {

    }

    @Override
    public Optional<Consumer> findByLogin(String login) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_CONSUMER_BY_LOGIN)) {

            Consumer consumer = null;
            statement.setString(1, login);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){

                    consumer = extractConsumerFromResultSet(resultSet);
                    consumer.setAccount(AccountDaoImpl.extractAccountFromResultSet(resultSet));
                    return Optional.of(consumer);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

}
