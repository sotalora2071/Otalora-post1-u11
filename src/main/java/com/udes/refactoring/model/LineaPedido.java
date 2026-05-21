package com.udes.refactoring.model;

/**
 * Value Object que representa una linea de pedido (producto + cantidad).
 * Elimina las dos listas paralelas List<Long> productosIds + List<Integer> cantidades
 * que eran un Data Clump.
 */
public final class LineaPedido {

    private final Long productoId;
    private final int cantidad;
    private final double precioUnitario;

    public LineaPedido(Long productoId, int cantidad, double precioUnitario) {
        if (productoId == null) {
            throw new IllegalArgumentException("Producto requerido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("Precio no puede ser negativo");
        }
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Long getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
