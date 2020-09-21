package ua.kukhtar.constant;

public class SQLQueryConstant {
    private SQLQueryConstant(){}

    //language=SQL
    public final static String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";

    //language=SQL
    public final static String SQL_GET_CONSUMER_BY_ID = "SELECT * FROM consumer WHERE id=?";
}
