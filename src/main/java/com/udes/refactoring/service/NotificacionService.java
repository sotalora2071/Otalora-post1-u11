package com.udes.refactoring.service;

import com.udes.refactoring.model.DatosCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Servicio extraido de PedidoService (Extract Class).
 * Responsabilidad unica: notificar al cliente sobre su pedido.
 * Reemplaza los System.out.println por un logger profesional (SLF4J).
 */
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    public void notificarPedido(DatosCliente cliente, boolean urgente) {
        log.info("Enviando notificacion al cliente: {}", cliente.getEmail());
        if (urgente) {
            log.info("Pedido marcado como URGENTE para: {}", cliente.getNombre());
        }
    }
}
