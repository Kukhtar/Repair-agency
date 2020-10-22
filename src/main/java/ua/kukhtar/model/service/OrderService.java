package ua.kukhtar.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.dao.AddressDao;
import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private OrderDao orderDao = daoFactory.createOrderDao();
    private UserDao userDao = daoFactory.createUserDao();
    private AddressDao addressDao = daoFactory.createAddressDao();

    public int createOrder(Address address, String userName) {
        Order order = new Order();
        Optional<User> customer = userDao.findByLogin(userName);
        if (customer.isPresent()) {
            order.setCustomer(customer.get());
        } else {
            throw new IllegalStateException("Can't find user in dataBase");
        }
        order.setDate(LocalDate.now());

        address.setId(addressDao.create(address));
        order.setAddress(address);

        return orderDao.create(order);
    }

    public List<Order> getActiveOrders(int start, int count) {
        return orderDao.findActive(start, count);
    }

    public List<Order> getClosedOrders(int start, int count) {
        return orderDao.findClosed(start, count);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = getActiveOrders(0, Integer.MAX_VALUE);
        orders.addAll(getClosedOrders(0, Integer.MAX_VALUE));
        return orders;
    }



    public Order findOrderByID(int id) {
        Optional<Order> optional = orderDao.read(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            logger.error("Order not found by id {}", id);
            throw new IllegalStateException("Order not found");
        }
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public List<Order> getMastersOrders(int masterId) {
        List<Order> orders = orderDao.findActive(0, Integer.MAX_VALUE).stream().filter(x -> x.getMaster().getId() == masterId).collect(Collectors.toList());
        logger.debug("obtained list: {}", orders);
        return orders;
    }

    public void updateStatus(Order order) {
        orderDao.updateStatus(order);
    }

    public void updateFeedback(Order order) {
        orderDao.updateFeedback(order);
    }

    public int countOfActiveOrders(){
        return orderDao.countOfActiveOrders();
    }

    public int countOfClosedOrders(){
        return orderDao.countOfClosedOrders();
    }

}
