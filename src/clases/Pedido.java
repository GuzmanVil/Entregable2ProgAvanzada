package clases;

public class Pedido implements Comparable<Pedido> {
    private int id;
    private boolean esUrgente;

    public boolean isEsUrgente() {
        return esUrgente;
    }

    public void setEsUrgente(boolean esUrgente) {
        this.esUrgente = esUrgente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido(int id, boolean esUrgente) {
        this.id = id;
        this.esUrgente = esUrgente;
    }

    @Override
    public int compareTo(Pedido otroPedido) {
        return Boolean.compare(otroPedido.esUrgente, this.esUrgente); // Prioridad para urgentes
    }
}