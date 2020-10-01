package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.AccountDao;
import ua.kukhtar.model.user.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDaoImpl.class);
    private DataSource dataSource;

    public AccountDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConncetion() throws SQLException {
        return dataSource.getConnection();
    }

    public static Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("account_id"));
        account.setLogin(resultSet.getString("login"));
        account.setPassword(resultSet.getString("password"));

        return account;
    }

    @Override
    public Optional<Account> findOptional(int id) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_ACCOUNT_BY_ID)) {

            Account account;
            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    account = extractAccountFromResultSet(resultSet);
                    return Optional.of(account);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void create(Object object) {

    }

    @Override
    public void update(Object object) {

    }
}
