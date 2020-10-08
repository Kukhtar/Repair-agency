package ua.kukhtar.constant;

public class SQLQueryConstant {
    private SQLQueryConstant(){}

    //language=SQL
    public static final String SQL_GET_USER_ID_BY_LOGIN = "SELECT users.id FROM users WHERE users.login = ? LIMIT 1";
    //language=SQL
    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    //language=SQL
    public static final String SQL_INSERT_USER = "INSERT INTO public.users (login, password, full_name, phone_number, role) VALUES (?, ?, ?, ?, 'USER')";
    //language=SQL
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM users WHERE role='USER' ";
    //language=SQL
    public static final String SQL_INSERT_ADDRESS = "INSERT INTO address (house_number, flat_number) VALUES (?, ?)";
    //language=SQL
    public static final String SQL_INSERT_ORDER = " INSERT INTO public.orders ( customer_id,  status, date, address_id) VALUES (?, 'WAITING_FOR_RESPONSE', ? , ?)";
    //language=SQL
    public static final String SQL_FIND_ALL_ORDERS_OF_USER = "SELECT * FROM orders WHERE customer_id = (SELECT id FROM users WHERE login = 'q')";
}
