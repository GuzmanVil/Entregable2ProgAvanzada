package clases;
import java.util.concurrent.*;

public class ProcesadorPedidos {
    private ThreadPoolExecutor procesadorUrgente;
    private ThreadPoolExecutor procesadorNormal;

    public ProcesadorPedidos(int numHilosUrgentes, int numHilosNormales) {
        this.procesadorUrgente = (ThreadPoolExecutor) Executors.newFixedThreadPool(numHilosUrgentes);
        int coreHilos = Math.max(1, numHilosNormales / 2); // Aseguramos al menos un hilo
        this.procesadorNormal = new ThreadPoolExecutor(
                coreHilos, numHilosNormales,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    public void procesarPedido(Pedido pedido) {
        if (pedido.isEsUrgente()) {
            procesadorUrgente.submit(() -> procesarPago(pedido));
        } else {
            procesadorNormal.submit(() -> procesarPago(pedido));
        }
    }

    private void procesarPago(Pedido pedido) {
        System.out.println("Procesando pago del pedido: " + pedido.getId());
        try {
            Thread.sleep(100);  // Simula tiempo de procesamiento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Empaquetar y luego enviar el pedido
        empaquetarPedidoEnParalelo(pedido);
    }

    // Paralelizar el empaquetado
    private void empaquetarPedidoEnParalelo(Pedido pedido) {
        ForkJoinPool.commonPool().execute(() -> {
            System.out.println("Empaquetando el pedido: " + pedido.getId());
            try {
                Thread.sleep(100);  // Simula tiempo de empaquetado
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Después de empaquetar, realizar el envío
            enviarPedido(pedido);
        });
    }

    //función de envío
    private void enviarPedido(Pedido pedido) {
        System.out.println("Enviando el pedido: " + pedido.getId());
        try {
            Thread.sleep(100);  // Simula tiempo de envío
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
