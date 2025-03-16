package ru.sibsutis.petstore.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sibsutis.petstore.core.exception.PetNotFoundException;
import ru.sibsutis.petstore.core.model.Category;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.model.Status;
import ru.sibsutis.petstore.core.repository.PetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet testPet;

    @BeforeEach
    void setUp() {
        testPet = new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);
    }

    @Test
    void testCreatePet() {
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        Pet createdPet = petService.createPet(testPet);

        assertNotNull(createdPet);
        assertEquals("Bobby", createdPet.getName());
        verify(petRepository, times(1)).save(testPet);
    }

    @Test
    void testGetPet_WhenPetExists() {
        when(petRepository.findById(1L)).thenReturn(Optional.ofNullable(testPet));

        Pet foundPet = petService.getPet(1L);

        assertNotNull(foundPet);
        assertEquals("Bobby", foundPet.getName());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPet_WhenPetNotFound() {
        when(petRepository.findById(1L)).thenReturn(null);

        assertThrows(PetNotFoundException.class, () -> petService.getPet(1L));
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllPets() {
        List<Pet> pets = List.of(testPet, new Pet(2L, "Kitty", new Category(2L, "Cats"), List.of(), Status.PENDING));

        when(petRepository.findAll()).thenReturn(pets);

        List<Pet> result = petService.getAllPets();

        assertEquals(2, result.size());
        assertEquals("Bobby", result.get(0).getName());
        assertEquals("Kitty", result.get(1).getName());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePet_WhenPetExists() {
        Pet updatedPet = new Pet(1L, "BobbyUpdated", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petRepository.findById(1L)).thenReturn(Optional.ofNullable(testPet));

        Pet result = petService.updatePet(updatedPet);

        assertNotNull(result);
        assertEquals("BobbyUpdated", result.getName());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePet_WhenPetNotExists() {
        Pet newPet = new Pet(3L, "Charlie", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petRepository.findById(3L)).thenReturn(null);
        when(petRepository.save(any(Pet.class))).thenReturn(newPet);

        Pet result = petService.updatePet(newPet);

        assertNotNull(result);
        assertEquals("Charlie", result.getName());
        verify(petRepository, times(1)).save(any(Pet.class));
    }
}
