package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.AddressDao;
import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.entity.Order;

import java.time.LocalDate;

public class OrderService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private OrderDao orderDao = daoFactory.createOrderDao();
    private UserDao userDao = daoFactory.createUserDao();
    private AddressDao addressDao = daoFactory.createAddressDao();

    public void createOrder(Address address, String userName){
        Order order = new Order();
        //todo: fix this
        address.setId((int)addressDao.createAndReturnId(address));
        order.setAddress(address);
        order.setCustomer(userDao.findByLogin(userName).get());
        order.setDate(LocalDate.now());


        orderDao.create(order);
    }
}
