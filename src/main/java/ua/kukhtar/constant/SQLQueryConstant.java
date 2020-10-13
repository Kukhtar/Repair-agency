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
    public static final String SQL_FIND_USERS_BY_ROLE = "SELECT * FROM users WHERE role=? ";
    //language=SQL
    public static final String SQL_INSERT_ADDRESS = "INSERT INTO address (house_number, flat_number) VALUES (?, ?)";
    //language=SQL
    public static final String SQL_INSERT_ORDER = " INSERT INTO public.orders ( customer_id,  status, date, address_id) VALUES (?, 'WAITING_FOR_RESPONSE', ? , ?)";
    //language=SQL
    public static final String SQL_FIND_ALL_ORDERS_OF_USER = "SELECT orders.id AS order_id, customer_id AS id, users.full_name , status, date, address.id AS address_id, address.flat_number, address.house_number, price  FROM orders JOIN users ON orders.customer_id = users.id JOIN address ON orders.address_id = address.id WHERE users.login = ?";
    //language=SQL
    public static final String SQL_FIND_ALL_ORDERS = "SELECT orders.id AS order_id, customer_id AS id, users.full_name , status, date, address.id AS address_id, address.flat_number, address.house_number, price  FROM orders JOIN users ON orders.customer_id = users.id JOIN address ON orders.address_id = address.id ";
    //language=SQL
    public static final String SQL_FIND_ORDER_BY_ID = "SELECT master_id, orders.id AS order_id, customer_id AS id, users.full_name , status," +
            " date, address.id AS address_id, address.flat_number, address.house_number, price FROM orders JOIN users ON " +
            "orders.customer_id = users.id JOIN address ON orders.address_id = address.id WHERE orders.id = ?";
    //language=SQL
    public static final String SQL_UPDATE_MASTER_ID = "UPDATE orders SET master_id = ?  WHERE id = ?";
    //language=SQL
    public static final String SQL_UPDATE_STATUS = "UPDATE orders SET status = ?  WHERE id = ?";
    //language=SQL
    public static final String SQL_UPDATE_PRICE = "UPDATE orders SET price = ?  WHERE id = ?";
    //language=SQL
    public static final String SQL_UPDATE_CONSUMER = "UPDATE public.users SET bank_account = ? WHERE id = ?";

}
