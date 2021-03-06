package ua.kukhtar.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.constant.SQLQueryConstant;
import ua.kukhtar.model.dao.AddressDao;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.enums.STATUS;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

/**
 * Class that implements generic dao for Address class
 */
public class AddressDaoImpl implements AddressDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final DataSource dataSource;

    public AddressDaoImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * get connection from Connection Pool
     * @return Connection object
     * @throws SQLException if can't get connection
     */
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * get data from result, then create from it new Address object
     * @param resultSet
     * @return Address object
     * @throws SQLException
     */
    public static Address extractAddressFromResultSet(ResultSet resultSet) throws SQLException {
        Address address = new Address();

        address.setId(resultSet.getInt("address_id"));
        address.setFlatNumber(resultSet.getInt("flat_number"));
        address.setHouseNumber(resultSet.getInt("house_number"));
        return address;
    }

    @Override
    public int create(Address address) {
        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQueryConstant.SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS)){

            statement.setInt(1, address.getHouseNumber());
            statement.setInt(2, address.getFlat_number());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            logger.debug("address {} added", address);
            return resultSet.getInt(1);


        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }
    }


    @Override
    public Optional<Address> read(int id) {
        return null;
    }

    @Override
    public void update(Address object) {

    }

    @Override
    public void delete(Address object) {

    }
}
