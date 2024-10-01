import clases.Pedido;
import clases.ProcesadorPedidos;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numHilosUrgentes = 3;  // Número de hilos para procesar urgentes
        int numHilosNormales = 5;  // Número de hilos para procesar normales

        ProcesadorPedidos procesador = new ProcesadorPedidos(numHilosUrgentes, numHilosNormales);

        // Simulación de pedidos con urgencias
        PriorityQueue<Pedido> colaPedidos = new PriorityQueue<>();
        colaPedidos.add(new Pedido(1, false));
        colaPedidos.add(new Pedido(2, true));  // clases.Pedido urgente
        colaPedidos.add(new Pedido(3, false));
        colaPedidos.add(new Pedido(4, true));  // clases.Pedido urgente
        colaPedidos.add(new Pedido(5, false));

        // Procesar los pedidos
        while (!colaPedidos.isEmpty()) {
            Pedido pedido = colaPedidos.poll();
            procesador.procesarPedido(pedido);
        }

        // Cerrar los servicios de forma ordenada
        procesador.cerrar();
        procesador.esperarCierre();

        System.out.println("Todos los pedidos han sido procesados.");
    }
}
