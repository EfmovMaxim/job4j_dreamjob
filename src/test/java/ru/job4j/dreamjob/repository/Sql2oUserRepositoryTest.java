package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;
import java.util.List;
import java.util.Properties;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        Properties properties = new Properties();

        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var cfg = new DatasourceConfiguration();
        var dataSource = cfg.connectionPool(
                properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password"));
        Sql2o sql2o = cfg.databaseClient(dataSource);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteByEmail(user.getEmail());
        }
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var user1 = new User("123@mail.ru", "name1", "pass1");
        var user2 = new User("321@mail.ru", "name2", "pass2");
        var user3 = new User("321@mail.ru", "name3", "pass3");

        sql2oUserRepository.save(user1);
        sql2oUserRepository.save(user2);
        sql2oUserRepository.save(user3);
        var expected = List.of(user1, user2);
        var res = sql2oUserRepository.findAll();

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void whenSaveThenGetByEmailAndPassword() {
        var user1 = new User("123@mail.ru", "name1", "pass1");
        var user2 = new User("321@mail.ru", "name2", "pass2");
        var user3 = new User("321@mail.ru", "name3", "pass3");

        sql2oUserRepository.save(user1);
        sql2oUserRepository.save(user2);
        sql2oUserRepository.save(user3);

        var res = sql2oUserRepository.findByEmailAndPassword("123@mail.ru", "pass1");

        assertThat(res.get()).isEqualTo(user1);
    }
}