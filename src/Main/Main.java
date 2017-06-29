package Main;
import Utiles.TecladoIn;

public class Main {

    public static void main(String[] args) {
        int opcion;
        boolean corriendo = true;
        ServicioViajero sv = new ServicioViajero();
        //sv.cargaTesting1();
        //sv.cargaTesting2();
        sv.cargaInicial();
        
        while (corriendo) {
            ServicioViajero.mostrarOpciones();
            System.out.print("Ingrese un numero por favor: ");
            opcion = TecladoIn.readLineInt();
            System.out.println("");

            switch (opcion) {
                //1-13
                case 1:
                    sv.altaCiudad();
                    break;
                case 2:
                    sv.bajaCiudad();
                    break;
                case 3:
                    sv.altaRuta();
                    break;
                case 4:
                    sv.bajaRuta();
                    break;
                case 5:
                    sv.informacionCiudad();
                    break;
                case 6:
                    sv.listarRangoCiudades();
                    break;
                case 7:
                    sv.caminoMasCortoKilometros();
                    break;
                case 8:
                    sv.caminoKilometroLimite();
                    break;
                case 9:
                    sv.caminoMasCortoCantCiudad();
                    break;
                case 10:
                    sv.caminoConAlojamiento();
                    break;
                case 11:
                    sv.listarCiudadesAlfab();
                    break;
                case 12:
                    sv.mostrarDic();
                    break;
                case 13:
                    sv.mostrarGrafo();
                    break;
                case 0:
                    corriendo = false;
                    System.out.println("¡Buen Viaje!");
                    break;
                default:
                    System.out.println("Existian MUCHAS opciones, no seleccionaste ninguna :( ¡SHAME!");
                    break;
            }
        }
    }
}
