package com.udes.refactoring.service;

import com.udes.refactoring.model.Pedido;
import com.udes.refactoring.model.Producto;
import com.udes.refactoring.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio con code smells deliberados:
 * - Large Class (demasiadas responsabilidades en una sola clase)
 * - Long Method (procesarPedido hace validacion, calculo, descuento, notificacion y persistencia)
 * - Primitive Obsession (12 parametros primitivos en lugar de objetos de dominio)
 * - Data Clump (los datos del cliente viajan siempre juntos)
 * - Field Injection (uso de @Autowired en campos en lugar de constructor)
 */
@Service
public class PedidoService {

    @Autowired // Code Smell: inyeccion en campo
    private PedidoRepository repo;

    // Long Method: valida, calcula, notifica y persiste en un solo metodo
    public String procesarPedido(Long clienteId, String clienteNombre,
                                  String clienteEmail, String clienteTelefono,
                                  String clienteDireccion, String clienteCiudad,
                                  String clienteCodigoPostal, List<Long> productosIds,
                                  List<Integer> cantidades, String metodoPago,
                                  boolean esUrgente, String codigoDescuento) {

        // Validacion del cliente (deberia ser metodo separado)
        if (clienteId == null || clienteNombre == null
                || clienteNombre.isBlank() || clienteEmail == null
                || !clienteEmail.contains("@")) {
            return "ERROR_CLIENTE";
        }

        // Calculo de total (Long Method smell)
        double total = 0;
        for (int i = 0; i < productosIds.size(); i++) {
            Producto p = repo.findProductoById(productosIds.get(i));
            if (p == null) return "ERROR_PRODUCTO";
            total += p.getPrecio() * cantidades.get(i);
        }

        // Descuento (logica de negocio mezclada)
        if (codigoDescuento != null && codigoDescuento.equals("VIP10")) {
            total = total * 0.90;
        } else if (codigoDescuento != null && codigoDescuento.equals("NEW20")) {
            total = total * 0.80;
        }

        // Notificacion (responsabilidad ajena)
        System.out.println("Enviando email a: " + clienteEmail);
        System.out.println("Pedido urgente: " + esUrgente);

        Pedido pedido = new Pedido(clienteId, clienteNombre, total);
        return "OK_" + repo.save(pedido).getId();
    }
}
