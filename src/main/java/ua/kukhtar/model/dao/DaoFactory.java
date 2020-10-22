package ua.kukhtar.model.dao;

import ua.kukhtar.model.dao.impl.JDBCDaoFactory;

/**
 * Abstract Factory, for easy creating different Dao factories
 */
public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract OrderDao createOrderDao();
    public abstract AddressDao createAddressDao();


    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
