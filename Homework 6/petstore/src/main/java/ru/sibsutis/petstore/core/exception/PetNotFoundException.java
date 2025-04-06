package ru.sibsutis.petstore.core.exception;

public class PetNotFoundException extends EntityNotFoundException {
    public PetNotFoundException(Long id) {
        super("Pet with ID " + id + " not found");
    }
}
