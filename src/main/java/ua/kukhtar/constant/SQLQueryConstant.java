package ua.kukhtar.constant;

public class SQLQueryConstant {
    private SQLQueryConstant(){}

    //language=SQL
    public static final String SQL_GET_USER_ID_BY_LOGIN = "SELECT users.id FROM users WHERE users.login = ? LIMIT 1";
    //language=SQL
    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    //
}
