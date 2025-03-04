package ru.sibsutis.petstore.core.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sibsutis.petstore.core.model.Category;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.model.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetRepositoryTest {

    private PetRepository petRepository;
    private Pet testPet;

    @BeforeEach
    void setUp() {
        petRepository = new PetRepository();
        testPet = new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);
    }

    @Test
    void testSave_NewPet_AssignsId() {
        Pet petWithoutId = new Pet(null, "Charlie", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);
        Pet savedPet = petRepository.save(petWithoutId);

        assertNotNull(savedPet.getId());
        assertEquals("Charlie", savedPet.getName());
    }

    @Test
    void testSave_ExistingPet_UpdatesData() {
        petRepository.save(testPet);
        Pet updatedPet = new Pet(1L, "BobbyUpdated", new Category(1L, "Dogs"), List.of(), Status.PENDING);

        Pet savedPet = petRepository.save(updatedPet);

        assertEquals(1L, savedPet.getId());
        assertEquals("BobbyUpdated", savedPet.getName());
        assertEquals(Status.PENDING, savedPet.getStatus());
    }

    @Test
    void testFindById_WhenPetExists() {
        petRepository.save(testPet);

        Pet foundPet = petRepository.findById(1L);

        assertNotNull(foundPet);
        assertEquals("Bobby", foundPet.getName());
    }

    @Test
    void testFindById_WhenPetDoesNotExist() {
        assertNull(petRepository.findById(99L));
    }

    @Test
    void testFindAll_WhenNoPets() {
        List<Pet> pets = petRepository.findAll();

        assertTrue(pets.isEmpty());
    }

    @Test
    void testFindAll_WhenPetsExist() {
        petRepository.save(testPet);
        petRepository.save(new Pet(2L, "Kitty", new Category(2L, "Cats"), List.of(), Status.PENDING));

        List<Pet> pets = petRepository.findAll();

        assertEquals(2, pets.size());
    }

    @Test
    void testDelete_WhenPetExists() {
        petRepository.save(testPet);

        Pet deletedPet = petRepository.delete(testPet);

        assertNotNull(deletedPet);
        assertEquals("Bobby", deletedPet.getName());
        assertNull(petRepository.findById(1L));
    }

    @Test
    void testDelete_WhenPetDoesNotExist() {
        Pet result = petRepository.delete(testPet);

        assertNull(result);
    }

    @Test
    void testDeleteById_WhenPetExists() {
        petRepository.save(testPet);

        Pet deletedPet = petRepository.deleteById(1L);

        assertNotNull(deletedPet);
        assertEquals("Bobby", deletedPet.getName());
        assertNull(petRepository.findById(1L));
    }

    @Test
    void testDeleteById_WhenPetDoesNotExist() {
        assertNull(petRepository.deleteById(99L));
    }
}
