package ru.sibsutis.petstore.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibsutis.petstore.core.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
