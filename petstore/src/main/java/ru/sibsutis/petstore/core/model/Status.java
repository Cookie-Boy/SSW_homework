package ru.sibsutis.petstore.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("available")
    AVAILABLE,

    @JsonProperty("unavailable")
    UNAVAILABLE,

    @JsonProperty("pending")
    PENDING,

    @JsonProperty("none")
    NONE
}
