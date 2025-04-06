package ru.sibsutis.persons.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sibsutis.persons.api.dto.ErrorResponseDto;
import ru.sibsutis.persons.core.model.Person;

import java.util.HashMap;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Long id) {
        HashMap<Long, Person> persons = new HashMap<>();
        persons.put(1L, new Person(1L, "Steve", 25));
        persons.put(2L, new Person(2L, "Лёха", 19));
        persons.put(3L, new Person(3L, "Таня", 27));

        Person person = persons.getOrDefault(id, null);

        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(404, "Человек не найден."));
        }
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        try {
            person.setAge(person.getAge() + 4);
            return ResponseEntity.ok(person);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(400, "Ошибка в переданном JSON."));
        }
    }
}
