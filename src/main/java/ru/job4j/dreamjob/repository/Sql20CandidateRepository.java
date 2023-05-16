package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
public class Sql20CandidateRepository implements CandidateRepository {
    private final Sql2o sql2o;

    public Sql20CandidateRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Candidate save(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO candidates (name, description, creation_date, file_id) VALUES (:name, :description, :creation_date, :file_id)");
            query.addParameter("name", candidate.getName());
            query.addParameter("description", candidate.getDescription());
            query.addParameter("creation_date", candidate.getCreationDate());
            query.addParameter("file_id", candidate.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            candidate.setId(generatedId);

            return candidate;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM candidates WHERE id=:id");
            query.addParameter("id", id);
            return query.executeUpdate().getResult() > 0;
        }

    }

    @Override
    public boolean update(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "UPDATE candidates SET name=:name, description=:description, creation_date=:creation_date, file_id=:file_id WHERE id=:id");

            query.addParameter("id", candidate.getId());
            query.addParameter("name", candidate.getName());
            query.addParameter("description", candidate.getDescription());
            query.addParameter("creation_date", candidate.getCreationDate());
            query.addParameter("file_id", candidate.getFileId());
            return query.executeUpdate().getResult() > 0;
        }
    }

    @Override
    public Optional<Candidate> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates WHERE id=:id");
            query.addParameter("id", id);
            return Optional.ofNullable(query.setColumnMappings(Vacancy.COLUMN_MAPPING).executeAndFetchFirst(Candidate.class));
        }
    }

    @Override
    public Collection<Candidate> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates");
            return query.setColumnMappings(Vacancy.COLUMN_MAPPING).executeAndFetch(Candidate.class);
        }
    }
}
