package ua.kukhtar.model.dao;

import ua.kukhtar.model.user.Consumer;

import java.util.Optional;

public interface ConsumerDao extends CrudDao<Consumer> {
    public Optional<Consumer> findByLogin(String login);
}
