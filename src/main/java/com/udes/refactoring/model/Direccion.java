package com.udes.refactoring.model;

import java.util.Objects;

/**
 * Value Object inmutable que representa una direccion fisica.
 * Elimina el Primitive Obsession sobre los strings calle/ciudad/codigoPostal.
 */
public final class Direccion {

    private final String calle;
    private final String ciudad;
    private final String codigoPostal;

    public Direccion(String calle, String ciudad, String codigoPostal) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("Calle requerida");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("Ciudad requerida");
        }
        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException("Codigo postal requerido");
        }
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direccion that)) return false;
        return Objects.equals(calle, that.calle)
                && Objects.equals(ciudad, that.ciudad)
                && Objects.equals(codigoPostal, that.codigoPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, ciudad, codigoPostal);
    }
}
