package ru.sibsutis.petstore.core.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import ru.sibsutis.petstore.core.model.Pet;

import java.util.List;
import java.util.Optional;

@Getter
@Repository
public class PetRepository {
    private List<Pet> pets;

    public Pet save(Pet pet) {
        pets.add(pet);
        return pet;
    }

    public Pet delete(Pet pet) {
        pets.remove(pet);
        return pet;
    }

    public Pet deleteById(Long id) {
        return delete(findById(id).orElse(null));
    }

    public Optional<Pet> findById(Long id) {
        return pets.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
