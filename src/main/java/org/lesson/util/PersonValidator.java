package org.lesson.util;

import org.lesson.dao.PersonDAO;
import org.lesson.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person targetPerson = (Person) target;
        Optional<Person> person = personDAO.show(targetPerson.getEmail());

        if (person.isPresent() && !Objects.equals(person.get().getId(), targetPerson.getId())) {
            errors.rejectValue("email", "", "Email is already in use");
        }

    }
}
