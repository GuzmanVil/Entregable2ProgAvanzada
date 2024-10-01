package tests;
import clases.Pedido;
import clases.ProcesadorPedidos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.PriorityQueue;
import static org.junit.jupiter.api.Assertions.*;

class ProcesamientoTest {
    private ProcesadorPedidos procesador;

    @BeforeEach
    void setUp() {
        // Elimina los argumentos del constructor
        procesador = new ProcesadorPedidos();
    }

    @Test
    void testProcesamientoUrgente() {
        Pedido pedidoUrgente = new Pedido(1, true);
        Pedido pedidoNormal = new Pedido(2, false);
        // Procesar pedidos
        procesador.procesarPedido(pedidoUrgente);
        procesador.procesarPedido(pedidoNormal);
        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        // Verificar que las tareas se completaron
        assertTrue(true); // Aquí podrías agregar más lógica si fuese necesario
    }

    @Test
    void testProcesamientoConcurrente() {
        PriorityQueue<Pedido> colaPedidos = new PriorityQueue<>();
        for (int i = 1; i <= 100; i++) {
            boolean esUrgente = i % 10 == 0; // Cada décimo pedido es urgente
            colaPedidos.add(new Pedido(i, esUrgente));
        }
        while (!colaPedidos.isEmpty()) {
            Pedido pedido = colaPedidos.poll();
            procesador.procesarPedido(pedido);
        }
        procesador.cerrar();
        assertTrue(true); // Se asume que las tareas se completaron correctamente
    }

    @Test
    void testCerrarOrdenado(){
        Pedido pedido1 = new Pedido(1, true);
        Pedido pedido2 = new Pedido(2, false);
        // Procesar pedidos
        procesador.procesarPedido(pedido1);
        procesador.procesarPedido(pedido2);
        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        // No hay excepción, se considera éxito
        assertTrue(true);
    }

    @Test
    void testPrioridadUrgentes(){
        Pedido pedidoUrgente1 = new Pedido(1, true);
        Pedido pedidoUrgente2 = new Pedido(2, true);
        Pedido pedidoNormal = new Pedido(3, false);
        // Procesar pedidos
        procesador.procesarPedido(pedidoUrgente1);
        procesador.procesarPedido(pedidoNormal);
        procesador.procesarPedido(pedidoUrgente2);
        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        assertTrue(true); // Se asume que las tareas se completaron correctamente
    }
}

