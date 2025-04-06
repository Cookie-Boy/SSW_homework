package ru.sibsutis.petstore.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibsutis.petstore.core.exception.PetNotFoundException;
import ru.sibsutis.petstore.core.exception.TagNotFoundException;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet updatePet(Pet newPet) {
        Optional<Pet> optionalPet = petRepository.findById(newPet.getId());
        Pet pet = optionalPet.orElseGet(Pet::new);
        pet.setId(newPet.getId());
        pet.setName(newPet.getName());
        pet.setCategory(newPet.getCategory());
        pet.setTags(newPet.getTags());
        pet.setStatus(newPet.getStatus());
        return optionalPet.isEmpty() ? petRepository.save(pet) : pet;
    }

    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }
}
