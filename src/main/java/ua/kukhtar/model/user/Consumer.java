package ua.kukhtar.model.user;

public class Consumer {
    private int id;

    private String bankAccount;
    private Account account;
    private UserInfo userInfo;
    public Consumer(int id, String bankAccount, Account account, UserInfo userInfo) {
        this.id = id;
        //System.out.println("ASD");
        this.bankAccount = bankAccount;
        this.account = account;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "id = " + id + "  Account: " + account + " \nbank_account = " + bankAccount;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getBankAccount() { return bankAccount; }

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
