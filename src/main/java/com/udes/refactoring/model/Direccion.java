package com.udes.refactoring.model;

/**
 * Value Object inmutable que representa una direccion fisica.
 * Implementado como record (Java 17): immutabilidad, equals, hashCode y toString automaticos.
 */
public record Direccion(String calle, String ciudad, String codigoPostal) {

    public Direccion {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("Calle requerida");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("Ciudad requerida");
        }
        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException("Codigo postal requerido");
        }
    }
}
