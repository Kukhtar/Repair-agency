package ua.kukhtar.dao.user;

import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.user.Account;
import ua.kukhtar.model.user.Consumer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO{

    private DataSource dataSource;

    public AccountDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConncetion() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Optional<Account> findOptional(int id) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_GET_ACCOUNT_BY_ID)) {

            Account account;
            statement.setInt(1,id);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    account = new Account(id, resultSet.getString("login"), resultSet.getString("password"));
                    return Optional.of(account);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
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
