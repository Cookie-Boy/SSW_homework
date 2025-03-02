package ru.sibsutis.petstore.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sibsutis.petstore.core.exception.PetNotFoundException;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.repository.PetRepository;
import ru.sibsutis.petstore.core.service.PetService;

import java.util.Optional;

public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Питомец не найден."));
    }

    @Override
    public Pet updatePet(Pet newPet) {
        Optional<Pet> optionalPet = petRepository.findById(newPet.getId());
        Pet pet = optionalPet.orElseGet(Pet::new);
        pet.setId(newPet.getId());
        pet.setName(newPet.getName());
        pet.setCategory(newPet.getCategory());
        pet.setTags(newPet.getTags());
        pet.setAvailable(newPet.isAvailable());
        return optionalPet.isEmpty() ? petRepository.save(pet) : pet;
    }

    @Override
    public Pet deletePet(Long id) {
        return petRepository.deleteById(id);
    }
}
