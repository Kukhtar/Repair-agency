package ua.kukhtar.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.kukhtar.model.dao.AddressDao;
import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.dao.OrderDao;
import ua.kukhtar.model.dao.UserDao;
import ua.kukhtar.model.dao.impl.UserDaoImpl;
import ua.kukhtar.model.entity.Address;
import ua.kukhtar.model.entity.Order;
import ua.kukhtar.model.entity.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private OrderDao orderDao = daoFactory.createOrderDao();
    private UserDao userDao = daoFactory.createUserDao();
    private AddressDao addressDao = daoFactory.createAddressDao();

    public void createOrder(Address address, String userName){
        Order order = new Order();
        //todo: fix this, it must be a transaction
        address.setId((int)addressDao.createAndReturnId(address));
        order.setAddress(address);
        order.setCustomer(userDao.findByLogin(userName).get());
        order.setDate(LocalDate.now());


        orderDao.create(order);
    }

    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }

    public Order findOrderByID(int id){
        Optional<Order> optional = orderDao.read(id);

        if (optional.isPresent()){
            return optional.get();
        }else {
            logger.error("Order not found by id {}", id);
            throw new IllegalStateException("Order not found");
        }
    }
}
