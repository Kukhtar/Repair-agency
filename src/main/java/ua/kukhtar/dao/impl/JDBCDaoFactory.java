package ua.kukhtar.dao.impl;

import ua.kukhtar.dao.*;

import javax.sql.DataSource;

public class JDBCDaoFactory extends DaoFactory {
    private DataSourceBuilder dataSourceBuilder = new DBCPDataSourceBuilder();
    private DataSource dataSource =  dataSourceBuilder.getDataSource();
    private AccountDaoImpl accountDao = new AccountDaoImpl(dataSource);

    @Override
    public AccountDao createAccountDao() {
        return new AccountDaoImpl(dataSource);
    }

    @Override
    public ConsumerDao createConsumerDao() {
        return new ConsumerDaoImpl(dataSource, accountDao);
    }
}
