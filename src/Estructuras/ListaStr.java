package Estructuras;

public class ListaStr {

    private NodoStr cabecera;

    public ListaStr() {
        this.cabecera = null;
    }

    public boolean insertarAlFinal(String elem) {
        boolean seInserto = false;
        NodoStr aux = cabecera;
        if (aux != null) {
            while (aux.getEnlace() != null) {
                aux = aux.getEnlace();
            }
            aux.setEnlace(new NodoStr(elem));
        } else {
            cabecera = new NodoStr(elem);
        }
        return seInserto;
    }

    public boolean insertar(String elem, int pos) {
        boolean seInserto = false;
        if (pos >= 0 || pos < this.longitud()) {
            if (pos != 0) {
                NodoStr aux = cabecera;
                int i = 0;
                while (i < pos - 1) {
                    i++;
                    aux = aux.getEnlace();
                }
                aux.setEnlace(new NodoStr(elem, aux.getEnlace()));
                seInserto = true;
            } else {
                cabecera = new NodoStr(elem, this.cabecera);
                seInserto = true;
            }
        }
        return seInserto;
    }

    public boolean eliminar(int pos) {
        boolean seElimino = false;
        if (pos >= 0 && pos < this.longitud()) {
            if (pos != 0) {
                NodoStr aux = cabecera;
                int i = 0;
                while (i < pos - 1) {
                    i++;
                    aux = aux.getEnlace();
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                seElimino = true;
            } else {
                cabecera = cabecera.getEnlace();
                seElimino = true;
            }
        }
        return seElimino;
    }

    public int getPos(String elem) {
        int cont = 0;
        int pos = -1;
        boolean corte = false;
        NodoStr nodoAux = this.cabecera;
        while (!corte && nodoAux != null) {
            if (nodoAux.getElem().equals(elem)) {
                corte = true;
                pos = cont;
            }
            cont++;
            nodoAux = nodoAux.getEnlace();
        }
        return pos;
    }

    public String recuperar(int pos) {
        String elem = "";
        if (cabecera != null && (pos > 0 || pos < this.longitud())) {
            if (pos == 0) {
                elem = cabecera.getElem();
            } else {
                NodoStr aux = cabecera;
                int cont = 0;
                while (aux != null) {
                    if (cont != pos) {
                        aux = aux.getEnlace();
                        cont++;
                    } else {
                        elem = aux.getElem();
                        aux = null;
                    }
                }
            }
        }
        return elem;
    }

    public boolean pertenece(String elem) {
        boolean pertenece = false;
        NodoStr aux = cabecera;
        while (aux != null && !pertenece) {
            if (!aux.getElem().equals(elem)) {
                aux = aux.getEnlace();
            } else {
                pertenece = true;
            }
        }
        return pertenece;
    }

    public void vaciar() {
        cabecera = null;
    }

    public boolean esVacia() {
        return cabecera == null;
    }

    public ListaStr clonar() {
        ListaStr clon = new ListaStr();
        NodoStr aux = cabecera;
        while (aux != null) {
            clon.insertarAlFinal(aux.getElem());
            aux = aux.getEnlace();
        }
        return clon;
    }

    public int longitud() {
        NodoStr aux = cabecera;
        int longitud = 0;
        if (!esVacia()) {
            longitud++;
            while (aux.getEnlace() != null) {
                longitud++;
                aux = aux.getEnlace();
            }
        }

        return longitud;
    }

    @Override
    public String toString() {
        String cad = "";
        if (!esVacia()) {
            NodoStr aux = cabecera;
            for (int i = 0; i < this.longitud(); i++) {
                cad = cad + aux.getElem() + " ";
                aux = aux.getEnlace();
            }
        } else {
            cad = "Vacia!";
        }

        return cad;
    }
}
