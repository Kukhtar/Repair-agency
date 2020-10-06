package ua.kukhtar.constant;

public class SQLQueryConstant {
    private SQLQueryConstant(){}

    //language=SQL
    public static final String SQL_GET_USER_ID_BY_LOGIN = "SELECT users.id FROM users WHERE users.login = ? LIMIT 1";
    //language=SQL
    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    //language=SQL
    public static final String SQL_ADD_USER = "INSERT INTO public.users (login, password, full_name, phone_number, role) VALUES (?, ?, ?, ?, 'USER')";
    //language=SQL
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM users WHERE role='USER' ";
}
