package ua.kukhtar.model.dao;

import ua.kukhtar.model.entity.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {
   Optional<User> findByLogin(String name);
}
