package ua.kukhtar.dao.impl;

import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.dao.AccountDao;
import ua.kukhtar.dao.ConsumerDao;
import ua.kukhtar.model.user.Account;
import ua.kukhtar.model.user.Consumer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConsumerDaoImpl implements ConsumerDao {
    private DataSource dataSource;
    private AccountDao accountDao;

    public ConsumerDaoImpl(DataSource dataSource, AccountDao accountDao){
        this.dataSource = dataSource;
        this.accountDao = accountDao;
    }

    private Connection getConncetion() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void create(Consumer consumer) {

    }

    @Override
    public void update(Consumer object) {

    }

    @Override
    public Optional<Consumer> findOptional(int id) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_CONSUMER_BY_ID)) {

            Consumer consumer = null;
            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    int accountid = resultSet.getInt("account_id");
                    Optional<Account> accountOptional = accountDao.findOptional(accountid);

                    if (!accountOptional.isPresent()){
                        //should add better error handling
                        //System.out.println("ASD");
                        throw new IllegalStateException("Can't find this account");
                    }

                    Account account = accountOptional.get();

                    consumer = new Consumer(id, resultSet.getString("bank_account"), account, null);
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
