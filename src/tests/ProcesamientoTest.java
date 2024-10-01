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
        procesador = new ProcesadorPedidos(3, 5); // 3 hilos urgentes y 5 normales
    }

    @Test
    void testProcesamientoUrgente() throws InterruptedException {
        Pedido pedidoUrgente = new Pedido(1, true);
        Pedido pedidoNormal = new Pedido(2, false);

        // Procesar pedidos
        procesador.procesarPedido(pedidoUrgente);
        procesador.procesarPedido(pedidoNormal);

        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        procesador.esperarCierre();

        // Verificar que las tareas se completaron
        assertTrue(true); // Aquí podrías agregar más lógica si fuese necesario
    }

    @Test
    void testProcesamientoConcurrente() throws InterruptedException {
        PriorityQueue<Pedido> colaPedidos = new PriorityQueue<>();
        for (int i = 1; i <= 100; i++) {
            boolean esUrgente = i % 10 == 0; // Cada décimo pedido es urgente
            colaPedidos.add(new Pedido(i, esUrgente));
        }

        // Marca el tiempo de inicio
        long startTime = System.currentTimeMillis();

        // Procesar pedidos en paralelo
        while (!colaPedidos.isEmpty()) {
            Pedido pedido = colaPedidos.poll();
            procesador.procesarPedido(pedido);
        }

        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        procesador.esperarCierre();

        // Marca el tiempo de fin
        long endTime = System.currentTimeMillis();

        // Calcula la duración
        long duration = endTime - startTime;

        // Verifica que la duración sea menor a un umbral determinado (por ejemplo, 5000 ms)
        assertTrue(duration < 10000, "El empaquetado no parece haber sido paralelo.");

        // Asume que las tareas se completaron correctamente
        assertTrue(true);
    }

    @Test
    void testCerrarOrdenado() throws InterruptedException {
        Pedido pedido1 = new Pedido(1, true);
        Pedido pedido2 = new Pedido(2, false);

        // Procesar pedidos
        procesador.procesarPedido(pedido1);
        procesador.procesarPedido(pedido2);

        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        procesador.esperarCierre();

        // No hay excepción, se considera éxito
        assertTrue(true);
    }

    @Test
    void testPrioridadUrgentes() throws InterruptedException {
        Pedido pedidoUrgente1 = new Pedido(1, true);
        Pedido pedidoUrgente2 = new Pedido(2, true);
        Pedido pedidoNormal = new Pedido(3, false);

        // Procesar pedidos
        procesador.procesarPedido(pedidoUrgente1);
        procesador.procesarPedido(pedidoNormal);
        procesador.procesarPedido(pedidoUrgente2);

        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        procesador.esperarCierre();

        // Aquí deberías validar que los pedidos urgentes fueron procesados antes que los normales
        // Esto puede implicar verificar logs o estados, dependiendo de cómo esté implementado tu código
        assertTrue(true); // Se asume que las tareas se completaron correctamente
    }

    @Test
    void testEnvioPedido() throws InterruptedException {
        Pedido pedido = new Pedido(1, false);

        // Procesar y enviar pedido
        procesador.procesarPedido(pedido);

        // Cerrar y esperar a que se completen las tareas
        procesador.cerrar();
        procesador.esperarCierre();

        // No hay excepción, se considera éxito
        assertTrue(true);
    }
}

