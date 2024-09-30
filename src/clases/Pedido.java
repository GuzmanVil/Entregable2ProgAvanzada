package clases;

public class Pedido implements Comparable<Pedido> {
    private int id;
    private boolean esUrgente;

    public Pedido(int id, boolean esUrgente) {
        this.id = id;
        this.esUrgente = esUrgente;
    }

    public int getId() {
        return id;
    }

    public boolean esUrgente() {
        return esUrgente;
    }

    @Override
    public int compareTo(Pedido otroPedido) {
        return Boolean.compare(otroPedido.esUrgente, this.esUrgente); // Prioridad para urgentes
    }
}