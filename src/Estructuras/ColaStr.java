package Estructuras;

public class ColaStr {

    private NodoStr frente, fin;

    public ColaStr() {
        frente = fin = null;
    }

    public boolean poner(String elem) {
        boolean sePuso = false;
        if (!esVacia()) {
            NodoStr nuevo = new NodoStr(elem);
            fin.setEnlace(nuevo);
            fin = nuevo;
            sePuso = true;
        } else {
            frente = new NodoStr(elem, fin);
            fin = frente;
        }
        return sePuso;
    }

    public String sacar() {
        String elem = null;
        if (!esVacia()) {
            elem = frente.getElem();
            frente = frente.getEnlace();
            if (frente == null) {
                fin = null;
            }
        }
        return elem;
    }

    public String getFrente() {
        return (frente == null) ? null : frente.getElem();
    }

    public boolean esVacia() {
        return frente == null;
    }

    public void vaciar() {
        frente = fin = null;
    }

    public ColaStr clonar() {
        return null;
    }

    @Override
    public String toString() {
        String cad = "";
        if (!esVacia()) {
            NodoStr aux = frente;
            while (aux != null) {
                cad = cad + aux.getElem() + ",";
                aux = aux.getEnlace();
            }
        } else {
            cad = "Vacia!";
        }

        return cad;
    }
}
