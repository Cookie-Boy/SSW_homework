package ru.sibsutis.petstore.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sibsutis.petstore.core.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}