package com.udes.refactoring.model;

import java.util.Objects;

/**
 * Value Object inmutable que agrupa los datos de un cliente.
 * Elimina el Data Clump de 6 parametros primitivos que viajaban juntos
 * (nombre, email, telefono, calle, ciudad, codigo postal).
 */
public final class DatosCliente {

    private final Long id;
    private final String nombre;
    private final String email;
    private final String telefono;
    private final Direccion direccion;

    public DatosCliente(Long id, String nombre, String email,
                        String telefono, Direccion direccion) {
        if (id == null) {
            throw new IllegalArgumentException("Id requerido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public Direccion getDireccion() { return direccion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosCliente that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
