package com.udes.refactoring.repository;

import com.udes.refactoring.model.Pedido;
import com.udes.refactoring.model.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PedidoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Pedido save(Pedido pedido) {
        em.persist(pedido);
        return pedido;
    }

    public Producto findProductoById(Long id) {
        return em.find(Producto.class, id);
    }
}
