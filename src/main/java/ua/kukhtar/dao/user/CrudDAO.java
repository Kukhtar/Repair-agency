package ua.kukhtar.dao.user;

import java.util.Optional;

public interface CrudDAO<T> {
    Optional<T> findOptional(int id);
    void create(T object);
    void update(T object);
}
