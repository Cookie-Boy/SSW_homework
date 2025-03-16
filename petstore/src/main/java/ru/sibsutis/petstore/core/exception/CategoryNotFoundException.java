package ru.sibsutis.petstore.core.exception;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id + " not found");
    }
}
