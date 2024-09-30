package clases;

import java.util.concurrent.*;

public class ProcesadorPedidos {

    private final ThreadPoolExecutor procesadorUrgente;
    private final ThreadPoolExecutor procesadorNormal;

    // Constructor que recibe los números de hilos urgentes y normales
    public ProcesadorPedidos(int numHilosUrgentes, int numHilosNormales) {
        // Executor para pedidos urgentes con hilos fijos (sin PriorityBlockingQueue)
        this.procesadorUrgente = (ThreadPoolExecutor) Executors.newFixedThreadPool(numHilosUrgentes);

        // Executor para pedidos normales con ajuste dinámico de hilos
        this.procesadorNormal = new ThreadPoolExecutor(
                numHilosNormales / 2, numHilosNormales,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    public void procesarPedido(Pedido pedido) {
        // Procesar pedido urgente o normal basado en su prioridad
        if (pedido.esUrgente()) {
            procesadorUrgente.submit(() -> procesarPago(pedido));
            procesadorUrgente.submit(() -> empaquetarPedido(pedido));
            procesadorUrgente.submit(() -> enviarPedido(pedido));
        } else {
            procesadorNormal.submit(() -> procesarPago(pedido));
            procesadorNormal.submit(() -> empaquetarPedido(pedido));
            procesadorNormal.submit(() -> enviarPedido(pedido));
        }
    }

    private void procesarPago(Pedido pedido) {
        try {
            if (pedido.getId() == 999) {
                throw new RuntimeException("Error procesando el pedido " + pedido.getId());
            }
            System.out.println("Procesando pago del pedido " + pedido.getId());
            Thread.sleep(200); // Simulación de procesamiento de pago
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void empaquetarPedido(Pedido pedido) {
        try {
            System.out.println("Empaquetando pedido " + pedido.getId());
            Thread.sleep(300); // Simulación de empaquetado
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void enviarPedido(Pedido pedido) {
        try {
            System.out.println("Enviando pedido " + pedido.getId());
            Thread.sleep(100); // Simulación de envío
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void cerrar() {
        procesadorUrgente.shutdown();
        procesadorNormal.shutdown();
    }

    public void esperarCierre() throws InterruptedException {
        procesadorUrgente.awaitTermination(1, TimeUnit.MINUTES);
        procesadorNormal.awaitTermination(1, TimeUnit.MINUTES);
    }
}
