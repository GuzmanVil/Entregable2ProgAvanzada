package clases;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ProcesadorPedidos {
    private ExecutorService procesadorUrgente;
    private ExecutorService procesadorNormal;

    public ProcesadorPedidos(int numHilosUrgentes, int numHilosNormales) {
        // Crear un thread pool fijo para urgentes y normales
        this.procesadorUrgente = Executors.newFixedThreadPool(numHilosUrgentes);
        this.procesadorNormal = Executors.newFixedThreadPool(numHilosNormales);
    }

    public void procesarPedido(Pedido pedido) {
        if (pedido.isEsUrgente()) {
            procesadorUrgente.submit(() -> procesarPago(pedido));
        } else {
            procesadorNormal.submit(() -> procesarPago(pedido));
        }
    }

    // Procesar el pago
    private void procesarPago(Pedido pedido) {
        System.out.println("Procesando pago del pedido: " + pedido.getId());
        try {
            // Simula tiempo de procesamiento del pago
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Paralelizar el empaquetado después del pago
        empaquetarPedidosConForkJoin(List.of(pedido));  // Suponiendo un solo pedido
    }

    // Empaquetar pedidos utilizando ForkJoinPool
    private void empaquetarPedidosConForkJoin(List<Pedido> pedidos) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();  // Utiliza un ForkJoinPool para paralelizar

        forkJoinPool.submit(() -> {
            pedidos.parallelStream().forEach(pedido -> {
                System.out.println("Empaquetando el pedido: " + pedido.getId());
                try {
                    // Simula tiempo de empaquetado
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Luego de empaquetar, enviar el pedido
                enviarPedido(pedido);
            });
        }).join();  // Esperar a que todas las tareas se completen antes de continuar
    }

    // Enviar el pedido
    private void enviarPedido(Pedido pedido) {
        System.out.println("Enviando el pedido: " + pedido.getId());
        try {
            // Simula tiempo de envío
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Metodo para cerrar los pools de hilos
    public void cerrar() {
        procesadorUrgente.shutdown();
        procesadorNormal.shutdown();
    }

    // Esperar hasta que se cierren todos los hilos
    public void esperarCierre() throws InterruptedException {
        procesadorUrgente.awaitTermination(1, TimeUnit.MINUTES);
        procesadorNormal.awaitTermination(1, TimeUnit.MINUTES);
    }
}
