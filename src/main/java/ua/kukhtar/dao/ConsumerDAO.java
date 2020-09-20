package ua.kukhtar.dao;

import ua.kukhtar.model.user.Consumer;

public interface ConsumerDAO {

    void create(Consumer consumer);

    Consumer read(int id);

    void delete(Consumer consumer);
}
