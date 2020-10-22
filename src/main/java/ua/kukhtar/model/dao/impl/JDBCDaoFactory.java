package ua.kukhtar.model.dao.impl;

import ua.kukhtar.model.dao.*;

import javax.sql.DataSource;

public class JDBCDaoFactory extends DaoFactory {
    private DataSourceBuilder dataSourceBuilder = new DBCPDataSourceBuilder();
    private DataSource dataSource =  dataSourceBuilder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl(dataSource);
    }

    @Override
    public OrderDao createOrderDao() {
        return new OrderDaoImpl(dataSource);
    }

    @Override
    public AddressDao createAddressDao() {
        return new AddressDaoImpl(dataSource);
    }
}
