package ru.sibsutis.petstore.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public Pet createPet(@Valid @RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @GetMapping("/{petId}")
    public Pet getPet(@PathVariable Long petId) {
        return petService.getPet(petId);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @PutMapping
    public Pet updatePet(@Valid @RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    @DeleteMapping("/{petId}")
    public Pet deletePet(@PathVariable Long petId) {
        return petService.deletePet(petId);
    }
}
