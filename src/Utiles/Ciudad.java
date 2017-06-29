package Utiles;

public class Ciudad {

    private String nombre, provincia;
    private int habitantes;
    private boolean alojamientoDisp;

    public Ciudad() {
        nombre = "N/A";
        provincia = "N/A";
        habitantes = 0;
        alojamientoDisp = false;
    }

    public Ciudad(String nom, String prov, int hab, boolean disp) {
        nombre = nom;
        provincia = prov;
        habitantes = hab;
        alojamientoDisp = disp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(int habitantes) {
        this.habitantes = habitantes;
    }

    public boolean isAlojamientoDisp() {
        return alojamientoDisp;
    }

    public void setAlojamientoDisp(boolean alojamientoDisp) {
        this.alojamientoDisp = alojamientoDisp;
    }

    @Override
    public String toString() {
        String cad;
        if (this != null) {
            cad = "Ciudad: " + nombre + " Prov: " + provincia + " Hab: " + habitantes + " Alojamiento: " + ((alojamientoDisp) ? "Si" : "No");
        } else {
            cad = "No Existe!";
        }
        return cad;
    }
}
