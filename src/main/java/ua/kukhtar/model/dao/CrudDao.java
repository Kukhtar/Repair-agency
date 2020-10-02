package ua.kukhtar.model.dao;

import java.util.Optional;

public interface CrudDao<T> {
    void create(T object);
    T read(int id);
    void update(T object);
    void delete(T object);
}
