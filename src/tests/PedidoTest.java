package tests;
import clases.Pedido;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PedidoTest {
    @Test
    void testPedidoCreacion() {
        Pedido pedido = new Pedido(1, true);
        assertEquals(1, pedido.getId());
        assertTrue(pedido.esUrgente());
    }

    @Test
    void testPedidoNoUrgente() {
        Pedido pedido = new Pedido(2, false);
        assertFalse(pedido.esUrgente());
    }

    @Test
    void testCompareToUrgenteVsNoUrgente() {
        Pedido urgente = new Pedido(1, true);
        Pedido noUrgente = new Pedido(2, false);
        assertTrue(urgente.compareTo(noUrgente) < 0); // Urgente debería ser menor
    }

    @Test
    void testCompareToIguales() {
        Pedido pedido1 = new Pedido(1, true);
        Pedido pedido2 = new Pedido(2, true);
        assertTrue(pedido1.compareTo(pedido2) == 0); // Ambos urgentes
    }

    @Test
    void testCompareToNoUrgenteVsUrgente() {
        Pedido noUrgente = new Pedido(1, false);
        Pedido urgente = new Pedido(2, true);
        assertTrue(noUrgente.compareTo(urgente) > 0); // No urgente debería ser mayor
    }
}
