package ua.kukhtar.model.user;

public class Consumer {
    private String bankAccount;
    private Account account;
    private UserInfo userInfo;

    public Consumer(String bankAccount, Account account, UserInfo userInfo) {
        //add id field!!!!!
        //System.out.println("ASD");
        this.bankAccount = bankAccount;
        this.account = account;
        this.userInfo = userInfo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
