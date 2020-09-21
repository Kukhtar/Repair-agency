package ua.kukhtar.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSourceBuilder implements DataSourceBuilder{

    private DataSource dataSource;

    public DBCPDataSourceBuilder(){
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/repairAgencyDB");

        } catch (NamingException e) {
            e.printStackTrace();
            //should add error handling!!!
        }

    }


    public DataSource getDataSource() {
        return dataSource;
    }
}
