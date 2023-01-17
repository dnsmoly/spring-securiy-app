package com.smoly.SpringSecurityApp.util;

import com.smoly.SpringSecurityApp.models.Person;
import com.smoly.SpringSecurityApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> foundPerson = peopleService.findByUsername(person.getUsername());
        if (foundPerson.isPresent()) {
            errors.rejectValue("username", "", "User with this username already exists");
        }
    }
}
