package com.udes.refactoring.model;

/**
 * Value Object inmutable que agrupa los datos de un cliente.
 * Implementado como record (Java 17). Elimina el Data Clump de 6 parametros primitivos.
 *
 * Nota: accesores generados por el compilador (id(), nombre(), email(), etc.).
 * Tambien se mantienen getXxx() para compatibilidad con codigo cliente existente.
 */
public record DatosCliente(Long id, String nombre, String email,
                            String telefono, Direccion direccion) {

    public DatosCliente {
        if (id == null) {
            throw new IllegalArgumentException("Id requerido");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido");
        }
    }

    // Wrappers para mantener compatibilidad con codigo que use getXxx()
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}
