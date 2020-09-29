package ua.kukhtar.dao;

import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> findOptional(int id);
    void create(T object);
    void update(T object);
}
