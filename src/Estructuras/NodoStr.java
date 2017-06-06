package Estructuras;

public class NodoStr {

    private String elem;
    private NodoStr enlace;

    public NodoStr(String elem) {
        this.elem = elem;
        this.enlace = null;
    }

    public NodoStr(String elem, NodoStr enlace) {
        this.elem = elem;
        this.enlace = enlace;
    }

    public String getElem() {
        return elem;
    }

    public void setElem(String elem) {
        this.elem = elem;
    }

    public NodoStr getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoStr enlace) {
        this.enlace = enlace;
    }
}
