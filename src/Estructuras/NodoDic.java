package Estructuras;
import Utiles.Ciudad;

public class NodoDic {

    private NodoDic izq, der;
    private int altura;
    private String clave;
    private Ciudad ciudad;

    public NodoDic(String clave, Ciudad ciudad) {
        this.ciudad = ciudad;
        this.clave = clave;
        this.altura = 0;
        
    }

    public NodoDic getIzq() {
        return izq;
    }

    public void setIzq(NodoDic izq) {
        this.izq = izq;
    }

    public NodoDic getDer() {
        return der;
    }

    public void setDer(NodoDic der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
 
}
