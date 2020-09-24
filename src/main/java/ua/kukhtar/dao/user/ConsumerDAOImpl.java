package ua.kukhtar.dao.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.dao.user.ConsumerDAO;
import ua.kukhtar.model.user.Account;
import ua.kukhtar.model.user.Consumer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConsumerDAOImpl implements ConsumerDAO {
    private static final Logger logger = LogManager.getLogger(ConsumerDAOImpl.class);
    private DataSource dataSource;
    private AccountDAO accountDAO;

    public ConsumerDAOImpl(DataSource dataSource, AccountDAO accountDAO){
        this.dataSource = dataSource;
        this.accountDAO = accountDAO;
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
                    Optional<Account> accountOptional = accountDAO.findOptional(accountid);

                    if (!accountOptional.isPresent()){
                        //should add better error handling
                        //System.out.println("ASD");
                        logger.error("Can't find this account by id: {}", accountid);
                        throw new IllegalStateException("Can't find this account");
                    }

                    Account account = accountOptional.get();

                    consumer = new Consumer(id, resultSet.getString("bank_account"), account, null);
                    return Optional.of(consumer);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

}
