package ru.sibsutis.petstore.core.service;

import org.springframework.stereotype.Service;
import ru.sibsutis.petstore.core.model.Pet;

@Service
public interface PetService {
    Pet createPet(Pet pet);
    Pet getPet(Long id);
    Pet updatePet(Pet pet);
    Pet deletePet(Long id);
}