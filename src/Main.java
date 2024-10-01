import clases.Pedido;
import clases.ProcesadorPedidos;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        // Ya no es necesario pasar parámetros al constructor
        ProcesadorPedidos procesador = new ProcesadorPedidos();
        // Simulación de pedidos con urgencias
        PriorityQueue<Pedido> colaPedidos = new PriorityQueue<>();
        colaPedidos.add(new Pedido(1, false));
        colaPedidos.add(new Pedido(2, true));  // Pedido urgente
        colaPedidos.add(new Pedido(3, false));
        colaPedidos.add(new Pedido(4, true));  // Pedido urgente
        colaPedidos.add(new Pedido(5, false));
        // Procesar los pedidos
        while (!colaPedidos.isEmpty()) {
            Pedido pedido = colaPedidos.poll();
            procesador.procesarPedido(pedido);
        }
        // Cerrar los servicios de forma ordenada (esto es opcional y no esencial)
        procesador.cerrar();
        System.out.println("Todos los pedidos han sido procesados.");
    }
}
