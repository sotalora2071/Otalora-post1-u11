package com.udes.refactoring.model;

/**
 * Value Object enum que reemplaza los strings "VIP10" / "NEW20".
 * Elimina el Primitive Obsession sobre el codigo de descuento
 * y centraliza la logica de porcentajes.
 */
public enum CodigoDescuento {
    VIP10(0.10),
    NEW20(0.20),
    NINGUNO(0.0);

    private final double porcentaje;

    CodigoDescuento(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public static CodigoDescuento desde(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return NINGUNO;
        }
        try {
            return CodigoDescuento.valueOf(codigo);
        } catch (IllegalArgumentException e) {
            return NINGUNO;
        }
    }
}
