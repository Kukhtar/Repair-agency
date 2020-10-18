package ua.kukhtar.model.entity;

import ua.kukhtar.model.entity.enums.STATUS;

import java.time.LocalDate;

//todo: add builder instead of setters

public class Order {
    private int id;
    private User customer;
    private User master;
    private STATUS status;
    private LocalDate date;
    private Address address;
    private int price;
    private String feedBack;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public User getCustomer() { return customer; }

    public void setCustomer(User customer) { this.customer = customer; }

    public User getMaster() { return master; }

    public void setMaster(User master) { this.master = master; }

    public STATUS getStatus() { return status;}

    public void setStatus(STATUS status) { this.status = status; }

    public LocalDate getDate() {return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Address getAddress() { return address;}

    public void setAddress(Address address) { this.address = address; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getFeedBack() { return feedBack; }

    public void setFeedBack(String feedBack) { this.feedBack = feedBack; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", master=" + master +
                ", status=" + status +
                ", date=" + date +
                ", address=" + address +
                ", price=" + price +
                ", feedBack='" + feedBack + '\'' +
                '}';
    }
}
