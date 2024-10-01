package clases;

import java.util.List;

public class ProcesadorPedidos {

    public ProcesadorPedidos() {}

    public void procesarPedido(Pedido pedido) {
        // Lista de tareas a ejecutar para un pedido
        List<Runnable> tareas = List.of(
                () -> procesarPago(pedido),
                () -> empaquetarPedido(pedido),
                () -> enviarPedido(pedido)
        );
        // Usar parallelStream para ejecutar las tareas en paralelo
        tareas.parallelStream().forEach(Runnable::run);
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
        System.out.println("Procesamiento finalizado.");
    }
}

