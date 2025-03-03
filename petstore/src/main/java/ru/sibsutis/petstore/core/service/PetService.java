package ru.sibsutis.petstore.core.service;

import ru.sibsutis.petstore.core.model.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);
    Pet getPet(Long id);
    List<Pet> getAllPets();
    Pet updatePet(Pet pet);
    Pet deletePet(Long id);
}