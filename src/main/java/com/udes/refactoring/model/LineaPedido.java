package com.udes.refactoring.model;

/**
 * Value Object que representa una linea de pedido (producto + cantidad).
 * Implementado como record (Java 17). Elimina las listas paralelas del codigo original.
 */
public record LineaPedido(Long productoId, int cantidad, double precioUnitario) {

    public LineaPedido {
        if (productoId == null) {
            throw new IllegalArgumentException("Producto requerido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("Precio no puede ser negativo");
        }
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
