package Estructuras;

public class ColaStr {

    private NodoStr frente, fin;

    public ColaStr() {
        frente = fin = null;
    }

    public boolean poner(String elem) {
        boolean sePuso = true;
        if (this.frente != null) {
            NodoStr nuevo = new NodoStr(elem);
            fin.setEnlace(nuevo);
            fin = nuevo;
        } else {
            frente = new NodoStr(elem, fin);
            fin = frente;
        }
        return sePuso;
    }

    public String sacar() {
        String elem = null;
        if (this.frente != null) {
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

    @Override
    public String toString() {
        String cad = "";
        if (this.frente != null) {
            NodoStr aux = frente;
            while (aux != null) {
                cad += aux.getElem() + " ";
                aux = aux.getEnlace();
            }
        } else {
            cad = "Vacia!";
        }

        return cad.trim();
    }
}
