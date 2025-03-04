package ru.sibsutis.petstore.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sibsutis.petstore.core.model.Category;
import ru.sibsutis.petstore.core.model.Pet;
import ru.sibsutis.petstore.core.model.Status;
import ru.sibsutis.petstore.core.service.PetService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void testCreatePet() throws Exception {
        Pet pet = new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bobby"));
    }

    @Test
    void testGetPet() throws Exception {
        Pet pet = new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.getPet(1L)).thenReturn(pet);

        mockMvc.perform(get("/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bobby"));
    }

    @Test
    void testGetAllPets() throws Exception {
        List<Pet> pets = List.of(
                new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE),
                new Pet(2L, "Kitty", new Category(2L, "Cats"), List.of(), Status.PENDING)
        );

        when(petService.getAllPets()).thenReturn(pets);

        mockMvc.perform(get("/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Bobby"))
                .andExpect(jsonPath("$[1].name").value("Kitty"));
    }

    @Test
    void testUpdatePet() throws Exception {
        Pet pet = new Pet(1L, "BobbyUpdated", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.updatePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BobbyUpdated"));
    }

    @Test
    void testDeletePet() throws Exception {
        Pet pet = new Pet(1L, "Bobby", new Category(1L, "Dogs"), List.of(), Status.AVAILABLE);

        when(petService.deletePet(eq(1L))).thenReturn(pet);

        mockMvc.perform(delete("/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bobby"));
    }
}
