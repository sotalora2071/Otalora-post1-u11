package com.udes.refactoring.service;

import com.udes.refactoring.model.CodigoDescuento;
import com.udes.refactoring.model.DatosCliente;
import com.udes.refactoring.model.LineaPedido;
import com.udes.refactoring.model.Pedido;
import com.udes.refactoring.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio refactorizado aplicando:
 *  - Extract Method: procesarPedido se divide en metodos privados con responsabilidad unica.
 *  - Extract Class: la notificacion se delega a NotificacionService.
 *  - Value Objects: DatosCliente, LineaPedido, CodigoDescuento eliminan el Primitive Obsession.
 *  - Constructor Injection: reemplaza el @Autowired en campo.
 *
 * CC esperada de procesarPedido: 1 (solo orquesta llamadas).
 */
@Service
public class PedidoService {

    private final PedidoRepository repo;
    private final NotificacionService notificacion;

    public PedidoService(PedidoRepository repo, NotificacionService notificacion) {
        this.repo = repo;
        this.notificacion = notificacion;
    }

    public String procesarPedido(DatosCliente cliente, List<LineaPedido> lineas,
                                  boolean esUrgente, CodigoDescuento descuento) {
        double total = calcularTotal(lineas);
        double totalConDescuento = aplicarDescuento(total, descuento);
        notificacion.notificarPedido(cliente, esUrgente);
        return persistirPedido(cliente, totalConDescuento);
    }

    private double calcularTotal(List<LineaPedido> lineas) {
        return lineas.stream()
                .mapToDouble(LineaPedido::getSubtotal)
                .sum();
    }

    private double aplicarDescuento(double total, CodigoDescuento descuento) {
        return total * (1 - descuento.getPorcentaje());
    }

    private String persistirPedido(DatosCliente cliente, double total) {
        Pedido pedido = new Pedido(cliente.getId(), cliente.getNombre(), total);
        return "OK_" + repo.save(pedido).getId();
    }
}
