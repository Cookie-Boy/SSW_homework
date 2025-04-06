package ru.sibsutis.petstore.core.exception;

public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(Long id) {
        super("Tag with ID " + id + " not found");
    }
}
