package ua.kukhtar.model.dao;

import java.util.Optional;

public interface CrudDao<T> {
    int create(T object);
    Optional<T> read(int id);
    void update(T object);
    void delete(T object);
}
