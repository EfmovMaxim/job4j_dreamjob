package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.*;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.File;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql20CandidateRepositoryTest {

    private static Sql20CandidateRepository sql20CandidateRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static File file;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql20CandidateRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        var dataSourceCfg = new DatasourceConfiguration();
        var connection = dataSourceCfg.connectionPool(
                properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password"));
        var sql2o = dataSourceCfg.databaseClient(connection);

        sql20CandidateRepository = new Sql20CandidateRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = new File("test", "test2");
        sql2oFileRepository.save(file);
    }

    @AfterEach
    public void clearCandidates() {
        var candidates = sql20CandidateRepository.findAll();
        for (var candidate : candidates) {
            sql20CandidateRepository.deleteById(candidate.getId());
        }
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @Test
    public void whenSaveThenGetSame() {
        var candidate = sql20CandidateRepository.save(new Candidate("candidate1", "desc1", file.getId()));
        assertThat(candidate).usingRecursiveComparison().isEqualTo(sql20CandidateRepository.findById(candidate.getId()).get());

    }

    @Test
    public void whenSaveSeveralThenGetAll() {

        List list = new ArrayList();
        list.add(sql20CandidateRepository.save(new Candidate("candidate1", "desc1", file.getId())));
        list.add(sql20CandidateRepository.save(new Candidate("candidate2", "desc2", file.getId())));
        list.add(sql20CandidateRepository.save(new Candidate("candidate3", "desc3", file.getId())));

        assertThat(sql20CandidateRepository.findAll()).isEqualTo(list);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql20CandidateRepository.findAll()).isEqualTo(List.of());
    }
}