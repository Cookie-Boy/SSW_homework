package ru.sibsutis.petstore.core.repository;

import org.springframework.stereotype.Repository;
import ru.sibsutis.petstore.core.model.Pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class PetRepository {
    private final HashMap<Long, Pet> pets = new HashMap<>();

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            int i = 1;
            while (pets.containsKey((long) i)) {
                i++;
            }
            pet.setId((long) i);
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    public Pet delete(Pet pet) {
        return pet != null ? pets.remove(pet.getId()) : null;
    }

    public Pet deleteById(Long id) {
        return delete(findById(id));
    }

    public Pet findById(Long id) {
        return pets.getOrDefault(id, null);
    }

    public List<Pet> findAll() {
        return new ArrayList<>(pets.values());
    }
}
