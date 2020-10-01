package ua.kukhtar.model.service;

import ua.kukhtar.model.dao.ConsumerDao;
import ua.kukhtar.model.dao.DaoFactory;
import ua.kukhtar.model.user.Consumer;

import java.util.Optional;

public class ConsumerService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<Consumer> login(String login){
        Optional<Consumer> result; //= Optional.empty();
        ConsumerDao teacherDao = daoFactory.createConsumerDao();
        result = teacherDao.findByLogin(login);
        return result;
    }
}
