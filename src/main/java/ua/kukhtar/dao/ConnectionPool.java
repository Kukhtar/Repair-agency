package ua.kukhtar.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private DataSource dataSource;

    public ConnectionPool(){
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/repairAgencyDB");

        } catch (NamingException e) {
            e.printStackTrace();
            //should add error handling!!!
        }

    }


    public Connection getConncetion() throws SQLException {
        return dataSource.getConnection();
    }
}
