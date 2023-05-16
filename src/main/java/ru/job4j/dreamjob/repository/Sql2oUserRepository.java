package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.dreamjob.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users(name, email, password) VALUES (:name, :email, :password)", true);
            query.addParameter("name", user.getName());
            query.addParameter("email", user.getEmail());
            query.addParameter("password", user.getPassword());
            var res = query.executeUpdate();
                if (res.getResult() > 0) {
                    user.setId(res.getKey(Integer.class));
                    return Optional.ofNullable(user);
                }
            } catch (Exception ex) {
                return Optional.empty();
            }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT id, name, email, password FROM users WHERE email = :email AND password = :password");
            query.addParameter("email", email);
            query.addParameter("password", password);
            return Optional.ofNullable(query.executeAndFetchFirst(User.class));
        }
    }

    @Override
    public boolean deleteByEmail(String email) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE email = :email");
            query.addParameter("email", email);
            return query.executeUpdate().getResult() > 0;
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.executeAndFetch(User.class);
        }
    }
}
