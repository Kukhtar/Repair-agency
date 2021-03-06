package ua.kukhtar.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Implementation for DataSourceBuilder that use Apache DBCP
 * to get DataSource
 */
public class DBCPDataSourceBuilder implements DataSourceBuilder{

    final private DataSource dataSource;
    private static final Logger logger = LogManager.getLogger(DBCPDataSourceBuilder.class);
    public DBCPDataSourceBuilder(){
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/repairAgencyDB");

        } catch (NamingException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        }

    }


    public DataSource getDataSource() {
        return dataSource;
    }
}
