package ua.kukhtar.constant;

public class SQLQueryConstant {
    private SQLQueryConstant(){}

    //language=SQL
    public final static String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";

    //language=SQL
    public final static String SQL_GET_CONSUMER_BY_ID = "SELECT * FROM consumer WHERE id=?";

    //language=SQL
    public final static String SQL_GET_CONSUMER_BY_LOGIN = "SELECT consumer.id AS consumer_id, account_id, bank_account, login, password  FROM account JOIN consumer ON account.id=consumer.account_id WHERE login=?";

    //language=SQL
    public static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
}
