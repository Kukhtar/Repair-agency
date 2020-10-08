package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.AddressDao;
import ua.kukhtar.model.entity.Address;

import javax.sql.DataSource;
import java.sql.*;

public class AddressDaoImpl implements AddressDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final DataSource dataSource;

    public AddressDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public long createAndReturnId(Address address) {
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, address.getHouseNumber());
            statement.setInt(2, address.getFlat_number());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                logger.debug("address {} added", address);
                return resultSet.getLong(1);
            }

            return -1;

        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void create(Address object) {

    }

    @Override
    public Address read(int id) {
        return null;
    }

    @Override
    public void update(Address object) {

    }

    @Override
    public void delete(Address object) {

    }
}
