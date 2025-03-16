package ru.sibsutis.petstore.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sibsutis.petstore.core.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}