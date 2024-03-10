package org.lesson.dao;

import org.lesson.models.Person;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Person> personRowMapper;

    public PersonDAO(JdbcTemplate jdbcTemplate, RowMapper<Person> personRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRowMapper = personRowMapper;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", personRowMapper);
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id = ?", new Object[]{id}, personRowMapper)
                .stream().findFirst().orElse(null);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email = ?",
                new Object[]{email}, personRowMapper).stream().findFirst();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (full_name, age, email, address) VALUES (?,?,?,?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name = ?, age = ?, email = ?, address = ? WHERE person_id = ?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id = ?",id);
    }

    /*
      Testing Batch Update performance
     */

    public void testMultipleUpdate() {
        List<Person> people = create1000People();

        long start = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person (full_name, age, email, address) VALUES (?,?,?,?);",
                    person.getName(), person.getAge(), person.getEmail(), person.getAddress());
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();

        long start = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person (full_name, age, email) VALUES (?,?,?,?);",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getEmail());
                        ps.setString(4, people.get(i).getAddress());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(0, "Name" + i, 30, "test" + i + "@mail.co", "some address"));
        }
        return people;
    }
}
