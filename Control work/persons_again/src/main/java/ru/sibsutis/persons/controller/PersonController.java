package ru.sibsutis.persons.controller;

import org.springframework.web.bind.annotation.*;
import ru.sibsutis.persons.model.Person;

import java.util.HashMap;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        HashMap<Long, Person> persons = new HashMap<>();
        persons.put(1L, new Person(1L, "Steve", 25));
        persons.put(2L, new Person(2L, "Лёха", 19));
        persons.put(3L, new Person(3L, "Таня", 27));

        return persons.getOrDefault(id, null);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        person.setAge(person.getAge() + 4);
        return person;
    }
}
