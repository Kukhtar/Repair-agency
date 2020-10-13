package ua.kukhtar.model.entity;

public class User {
    private int id;
    private String login;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String bankAccount;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }

    private ROLE role;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public enum ROLE {
        USER, MANAGER, MASTER
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ROLE getRole() { return role; }

    public void setRole(ROLE role) { this.role = role; }
}
