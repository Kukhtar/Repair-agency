package ua.kukhtar.dao;

import ua.kukhtar.model.user.Consumer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumerDAOImpl implements ConsumerDAO{
    private ConnectionPool connectionPool;

    private final static String SQL_GETCONSUMER_BY_ID = "SELECT * FROM consumer WHERE id=?";
    public ConsumerDAOImpl(){
        connectionPool = new ConnectionPool();
    }

    private Connection getConncetion() throws SQLException {
        return connectionPool.getConncetion();
    }

    @Override
    public void create(Consumer consumer) {

    }
    //add Optoinal returning type
    @Override
    public Consumer read(int id) {
        try(Connection connection = getConncetion();
            PreparedStatement statement = connection.prepareStatement(SQL_GETCONSUMER_BY_ID)) {

            Consumer consumer = null;
            statement.setInt(1,1);

            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()){
                    consumer = new Consumer(resultSet.getString("bank_account"), null, null);
                }
            }

            return consumer;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Consumer consumer) {

    }
}
