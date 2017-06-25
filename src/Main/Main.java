package Main;

import Estructuras.Diccionario;
import Utiles.ServicioViajero;
import Estructuras.Grafo;
import Utiles.Ciudad;
import Utiles.TecladoIn;
import Estructuras.ListaStr;

public class Main {

    public static void main(String[] args) {
        int opcion;
        boolean corriendo = true;
        //Pruebas();
        ServicioViajero sv = new ServicioViajero();
        sv.cargaTesting();

        while (corriendo) {
            
            ServicioViajero.menu();
            System.out.print("Ingrese numero: ");
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
                case 8://8 - Camino más corto que x kilometros.
                    sv.caminoKilometroLimite();
                    break;
                case 9://9 - Camino que pasa por la menor cantidad de ciudades.
                    sv.caminoMasCortoCantCiudad();
                    break;
                case 10://10 - Camino a traves de ciudades con alojamiento.
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
                    System.out.println("Existian MUCHAS opciones, no seleccionaste ninguna :( ");
                    break;

            }
        }
        /*
            1 - Alta Ciudad.
            2 - Baja Ciudad.
            3 - Alta Ruta.
            4 - Baja Ruta.
            5 - Información Ciudad.
            6 - Listar rango de ciudades.
            7 - Camino más corto.
            8 - Camino más corto que x kilometros.
            9 - Camino que pasa por la menor cantidad de ciudades.
            10 - Camino a traves de ciudades con alojamiento.
            11 - Listar ciudades por orden alfabetico.
            12 - Mostrar diccionario.
            13 - Mostrar grafo.
         */

    }

    public static void Pruebas() {

        Grafo g = new Grafo();
        g.insertarVertice("A");
        g.insertarVertice("B");
        g.insertarVertice("C");
        g.insertarVertice("D");
        g.insertarVertice("E");

        g.insertarArco("A", "B", 2);
        g.insertarArco("A", "D", 3);
        g.insertarArco("A", "C", 3);
        g.insertarArco("B", "C", 1);
        g.insertarArco("B", "E", 1);
        g.insertarArco("C", "E", 3);
        g.insertarArco("D", "E", 5);

        ListaStr list = g.caminoMasCorto("A", "E");

        System.out.println(list.toString());

        System.out.println(g.listarAnchura());
    }
}
