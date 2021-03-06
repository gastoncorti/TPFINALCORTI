package Utiles;

import Estructuras.Diccionario;
import Estructuras.Grafo;
import Estructuras.ListaStr;

public class ServicioViajero {

    private Diccionario diccionario;
    private Grafo conexiones;

    public ServicioViajero() {
        diccionario = new Diccionario();
        conexiones = new Grafo();
    }

    public void altaCiudad() {
        char aloj;
        boolean correcto = false;
        Ciudad nueva = new Ciudad();
        System.out.println("Ingrese Nombre: ");
        nueva.setNombre(TecladoIn.readLine().toUpperCase());
        System.out.println("Ingrese Prov: ");
        nueva.setProvincia(TecladoIn.readLine().toUpperCase());
        System.out.println("Ingrese hab: ");
        nueva.setHabitantes(TecladoIn.readLineInt());
        while (!correcto) {
            System.out.println("Tien alojamiento? (S/N): ");
            aloj = TecladoIn.readNonwhiteChar();
            if (aloj == 'S' || aloj == 's') {
                correcto = true;
                nueva.setAlojamientoDisp(true);
            } else if (aloj == 'N' || aloj == 'n') {
                nueva.setAlojamientoDisp(false);
                correcto = true;
            }
        }
        if (diccionario.insertar(nueva.getNombre(), nueva) && conexiones.insertarVertice(nueva.getNombre())) {
            System.out.println("Se cargo correctamente.");
        } else {
            System.out.println("La ciudad ya se encuentra cargada");
        }
    }

    public void bajaCiudad() {
        String bajaCiudad = "";
        System.out.println("Nombre ciudad para dar de baja: ");
        bajaCiudad = TecladoIn.readLine().toUpperCase();
        if (diccionario.eliminar(bajaCiudad) && conexiones.eliminarVertice(bajaCiudad)) {
            System.out.println("Se dio de baja correctamente.");
        } else {
            System.out.println("La ciudad no existe.");
        }

    }

    public void altaRuta() {
        String origen, destino;
        int km = 0;
        System.out.println("Ingrese origen: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese destino: ");
        destino = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese Kilometraje: ");
        km = Math.abs(TecladoIn.readLineInt());

        if (conexiones.insertarArco(origen, destino, km)) {
            System.out.println("Se dio el alta correctamente.");
        } else {
            System.out.println("Esta ruta ya se encuentra cargada o no se encontro la ciudad de origen/destino.");
        }
    }

    public void bajaRuta() {
        String origen = "", destino = "";
        System.out.println("Nombre del origen de la ruta: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Nombre del destino de la ruta: ");
        destino = TecladoIn.readLine().toUpperCase();
        if (conexiones.eliminarAdyacente(origen, destino)) {
            System.out.println("Se dio de baja correctamente.");
        } else {
            System.out.println("La ciudad origen/destino no existe o no existe la ruta.");
        }
    }

    public void informacionCiudad() {
        Ciudad ciudad;
        System.out.println("Ingrese ciudad: ");
        ciudad = diccionario.recuperarElemento(TecladoIn.readLine().toUpperCase());
        if (ciudad != null) {
            System.out.println(ciudad.toString());
        } else {
            System.out.println("No se encontro la ciudad ingresada.");
        }

    }

    public void listarRangoCiudades() {
        String origen = "", destino = "";
        System.out.println("Nombre del inicio del rango: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Nombre del fin del rango: ");
        destino = TecladoIn.readLine().toUpperCase();
        ListaStr rango = diccionario.listarClavesRango(origen, destino);
        System.out.println(rango.toString());

    }

    /*
5.  Dadas 2 ciudades A y B, devolver la menor cantidad de kilómetros a realizar para llegar desde A
    hasta B.
6.  Dadas 2 ciudades A y B y una cantidad de kilómetros K, verificar si existe algún camino para ir
    desde A hasta B que no supere K kilómetros en total.
7.  Dadas 2 ciudades A y B, devolver el camino que pasa por menos ciudades que va desde A hasta
    B. El camino que debe devolver será una lista de ciudades.
8.  Dadas 2 ciudades A y B, devolver un camino desde A hasta B que pase sólo por ciudades que
    tienen alojamiento disponible (excepto A y B).
     */
    public void caminoMasCortoCantCiudad() {
        String origen = "", destino = "";
        System.out.println("Partida: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Destino: ");
        destino = TecladoIn.readLine().toUpperCase();
        ListaStr camino = conexiones.caminoMenorCantCiudades(origen, destino);
        System.out.println(camino.toString());
    }

    public void caminoMasCortoKilometros() {
        String origen, destino;
        System.out.println("Ingrese origen: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese destino: ");
        destino = TecladoIn.readLine().toUpperCase();
        //System.out.println("Menor KM: " + conexiones.menorKilometraje(origen, destino));
        ListaStr camino = conexiones.dijkstra(origen, destino);
        System.out.println(camino.toString());
    }

    public void caminoKilometroLimite() {
        double kmMaximo = -1;
        String origen, destino;
        System.out.println("Ingrese origen: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese destino: ");
        destino = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese kilometraje máximo: ");
        kmMaximo = TecladoIn.readLineDouble();
        double kmMinimoConseguido = conexiones.menorKilometraje(origen, destino);
        if (kmMinimoConseguido > kmMaximo) {
            System.out.println("Lo siento, la distancia es muy corta o no encontre un camino.");
        } else {
            System.out.println("El kilometraje minimo es: " + kmMinimoConseguido + " km.");
        }
    }

    public void caminoConAlojamiento() {
        String origen, destino;
        System.out.println("Ingrese origen: ");
        origen = TecladoIn.readLine().toUpperCase();
        System.out.println("Ingrese destino: ");
        destino = TecladoIn.readLine().toUpperCase();
        ListaStr conAloj = conexiones.caminoConAlojamiento(origen, destino, diccionario);
        System.out.println(conAloj.toString());

    }

    public void listarCiudadesAlfab() {
        System.out.println(diccionario.listarCiudadesAlfabeticamente());
    }

    public void mostrarDic() {
        System.out.println(diccionario.toString());
    }

    public void mostrarGrafo() {
        System.out.println(conexiones.toString());
    }

    public static void menu() {
        final String MENU = "\n-SERVICIOS DEL VIAJERO- v1.0\n"
                + "\u2713 1 - Alta Ciudad.\n"
                + "\u2713 2 - Baja Ciudad.\n"
                + "\u2713 3 - Alta Ruta.\n"
                + "\u2713 4 - Baja Ruta.\n"
                + "\u2713 5 - Información Ciudad.\n"
                + "\u2713 6 - Listar rango de ciudades.\n"
                + " 7 - Camino más corto.\n"
                + " 8 - Camino más corto que x kilometros.\n"
                + "\u2713 9 - Camino que pasa por la menor cantidad de ciudades.\n"
                + "\u2713 10 - Camino a traves de ciudades con alojamiento.\n"
                + "\u2713 11 - Listar ciudades por orden alfabetico.\n"
                + "\u2713 12 - Mostrar diccionario.\n"
                + "\u2713 13 - Mostrar grafo.\n"
                + "\n"
                + " 0 - SALIR.\n";
        System.out.println(MENU);
    }

    public void cargaInicial() {

        diccionario.insertar("CONCEPCION", new Ciudad("CONCEPCION", "TUCUMAN", 46194, false));
        conexiones.insertarVertice("CONCEPCION");
        diccionario.insertar("CORDOBA", new Ciudad("CORDOBA", "CORDOBA", 157010, true));
        conexiones.insertarVertice("CORDOBA");
        diccionario.insertar("ROSARIO", new Ciudad("ROSARIO", "SANTA FE", 748312, false));
        conexiones.insertarVertice("ROSARIO");
        diccionario.insertar("RESISTENCIA", new Ciudad("RESISTENCIA", "CHACO", 290723, false));
        conexiones.insertarVertice("RESISTENCIA");
        diccionario.insertar("PARANA", new Ciudad("PARANA", "ENTRE RIOS", 247863, true));
        conexiones.insertarVertice("PARANA");
        diccionario.insertar("NEUQUEN", new Ciudad("NEUQUEN", "NEUQUEN", 201868, true));
        conexiones.insertarVertice("NEUQUEN");
        diccionario.insertar("CIPOLLETI", new Ciudad("CIPOLLETI", "RIO NEGRO", 66472, true));
        conexiones.insertarVertice("CIPOLLETI");
        diccionario.insertar("VIEDMA", new Ciudad("VIEDMA", "RIO NEGRO", 46767, true));
        conexiones.insertarVertice("VIEDMA");
        diccionario.insertar("TRELEW", new Ciudad("TRELEW", "CHUBUT", 103656, true));
        conexiones.insertarVertice("TRELEW");
        diccionario.insertar("RAWSON", new Ciudad("RAWSON", "CHUBUT", 31787, false));
        conexiones.insertarVertice("RAWSON");
        diccionario.insertar("POSADAS", new Ciudad("POSADAS", "MISIONES", 354719, true));
        conexiones.insertarVertice("POSADAS");
        diccionario.insertar("AZUL", new Ciudad("AZUL", "BUENOS AIRES", 53054, true));
        conexiones.insertarVertice("AZUL");
        diccionario.insertar("RECONQUISTA", new Ciudad("RECONQUISTA", "SANTA FE", 66187, true));
        conexiones.insertarVertice("RECONQUISTA");
        diccionario.insertar("MENDOZA", new Ciudad("MENDOZA", "MENDOZA", 890312, false));
        conexiones.insertarVertice("MENDOZA");
        diccionario.insertar("USHUAIA", new Ciudad("USHUAIA", "TIERRA DEL FUEGO", 56825, false));
        conexiones.insertarVertice("USHUAIA");

        conexiones.insertarArco("VIEDMA", "TRELEW", 363);
        conexiones.insertarArco("VIEDMA", "PARANA", 1363);

        conexiones.insertarArco("CONCEPCION", "VIEDMA", 552);
        conexiones.insertarArco("CONCEPCION", "AZUL", 709);
        conexiones.insertarArco("CONCEPCION", "CORDOBA", 749);

        conexiones.insertarArco("CIPOLLETI", "RAWSON", 1250);
        conexiones.insertarArco("CIPOLLETI", "NEUQUEN", 10);

        conexiones.insertarArco("RECONQUISTA", "USHUAIA", 3279);
        conexiones.insertarArco("RECONQUISTA", "RAWSON", 279);
        conexiones.insertarArco("RECONQUISTA", "RESISTENCIA", 1532);

        conexiones.insertarArco("AZUL", "ROSARIO", 9.8);
        conexiones.insertarArco("AZUL", "CIPOLLETI", 689);

        conexiones.insertarArco("CORDOBA", "ROSARIO", 404.6);
        conexiones.insertarArco("CORDOBA", "AZUL", 531);

        conexiones.insertarArco("POSADAS", "RESISTENCIA", 626);

        conexiones.insertarArco("RESISTENCIA", "PARANA", 465);
        conexiones.insertarArco("RESISTENCIA", "CIPOLLETI", 1265);

        conexiones.insertarArco("NEUQUEN", "RESISTENCIA", 954);
        conexiones.insertarArco("NEUQUEN", "POSADAS", 408);
        conexiones.insertarArco("NEUQUEN", "CORDOBA", 2408);

        conexiones.insertarArco("TRELEW", "POSADAS", 128);
        conexiones.insertarArco("TRELEW", "PARANA", 375);
        conexiones.insertarArco("TRELEW", "RAWSON", 1068);

        conexiones.insertarArco("MENDOZA", "RESISTENCIA", 1604);
        conexiones.insertarArco("MENDOZA", "CORDOBA", 934);

        conexiones.insertarArco("RAWSON", "ROSARIO", 724);

        conexiones.insertarArco("PARANA", "CORDOBA", 323);

        conexiones.insertarArco("USHUAIA", "AZUL", 527);
        conexiones.insertarArco("USHUAIA", "MENDOZA", 184);
    }

    public void cargaTesting() {
        diccionario.insertar("D", new Ciudad("D", "D", 10, true));
        diccionario.insertar("E", new Ciudad("E", "E", 10, false));
        diccionario.insertar("C", new Ciudad("C", "C", 10, false));
        diccionario.insertar("A", new Ciudad("A", "A", 10, true));
        diccionario.insertar("B", new Ciudad("B", "B", 10, true));

        conexiones.insertarVertice("C");
        conexiones.insertarVertice("E");
        conexiones.insertarVertice("A");
        conexiones.insertarVertice("D");
        conexiones.insertarVertice("B");

        conexiones.insertarArco("A", "B", 5);
        conexiones.insertarArco("B", "D", 10);
        conexiones.insertarArco("A", "C", 5);
        conexiones.insertarArco("C", "D", 5);
        
        /*conexiones.insertarArco("A", "B", 6);
        conexiones.insertarArco("B", "A", 6);

        conexiones.insertarArco("A", "D", 1);
        conexiones.insertarArco("D", "A", 1);

        conexiones.insertarArco("D", "B", 2);
        conexiones.insertarArco("B", "D", 2);

        conexiones.insertarArco("D", "E", 1);
        conexiones.insertarArco("E", "D", 1);

        conexiones.insertarArco("B", "E", 2);
        conexiones.insertarArco("E", "B", 2);
        
        conexiones.insertarArco("E", "C", 5);
        conexiones.insertarArco("C", "E", 5);
        
        conexiones.insertarArco("C", "B", 5);
        conexiones.insertarArco("B", "C", 5);*/

    }
    
    public void cargaTesting2() {
        diccionario.insertar("NEUQUEN", new Ciudad("NEUQUEN", "NEUQUEN", 500000, true));
        diccionario.insertar("POSADAS", new Ciudad("POSADAS", "MISIONES", 344833, true));
        diccionario.insertar("CORDOBA", new Ciudad("CORDOBA", "CORDOBA", 1391000, false));
        diccionario.insertar("USUAHIA", new Ciudad("USUAHIA", "TIERRA DEL FUEGO", 54546, true));
        diccionario.insertar("SALTA", new Ciudad("SALTA", "SALTA", 535303, true));
        diccionario.insertar("MENDOZA", new Ciudad("MENDOZA", "MENDOZA", 115041, false));
        diccionario.insertar("TRELEW", new Ciudad("TRELEW", "CHUBUT", 50000, true));
        diccionario.insertar("CORRIENTES", new Ciudad("CORRIENTES", "CORRIENTES", 654325, true));
        diccionario.insertar("NECOCHEA", new Ciudad("NECOCHEA", "BUENOS AIRES", 416633, false));
        diccionario.insertar("VIEDMA", new Ciudad("VIEDMA", "CHUBUT", 232456, true));
        diccionario.insertar("TANDIL", new Ciudad("TANDIL", "BUENOS AIRES", 24564, false));
        diccionario.insertar("PARANA", new Ciudad("PARANA", "ENTRE RIOS", 247863, false));
        diccionario.insertar("RESISTENCIA", new Ciudad("RESISTENCIA", "CHACO", 291720, true));
        diccionario.insertar("ROSARIO", new Ciudad("ROSARIO", "SANTA FE", 1194000, true));
        diccionario.insertar("ANGACO", new Ciudad("ANGACO", "SAN JUAN", 45879, false));
        conexiones.insertarVertice("NEUQUEN");
        conexiones.insertarVertice("POSADAS");
        conexiones.insertarVertice("CORDOBA");
        conexiones.insertarVertice("USUAHIA");
        conexiones.insertarVertice("SALTA");
        conexiones.insertarVertice("MENDOZA");
        conexiones.insertarVertice("TRELEW");
        conexiones.insertarVertice("CORRIENTES");
        conexiones.insertarVertice("NECOCHEA");
        conexiones.insertarVertice("VIEDMA");
        conexiones.insertarVertice("TANDIL");
        conexiones.insertarVertice("PARANA");
        conexiones.insertarVertice("RESISTENCIA");
        conexiones.insertarVertice("ROSARIO");
        conexiones.insertarVertice("ANGACO");

        conexiones.insertarArco("USUAHIA", "TRELEW", 150);

        conexiones.insertarArco("VIEDMA", "NECOCHEA", 700);

        conexiones.insertarArco("TRELEW", "VIEDMA", 30);
        conexiones.insertarArco("TRELEW", "NEUQUEN", 600);
        conexiones.insertarArco("TRELEW", "USUAHIA", 150);

        conexiones.insertarArco("NEUQUEN", "TRELEW", 650);
        conexiones.insertarArco("NEUQUEN", "MENDOZA", 500);
        conexiones.insertarArco("NEUQUEN", "CORDOBA", 800);

        conexiones.insertarArco("CORDOBA", "NECOCHEA", 139);
        conexiones.insertarArco("CORDOBA", "ROSARIO", 160);
        conexiones.insertarArco("CORDOBA", "ANGACO", 111);

        conexiones.insertarArco("MENDOZA", "CORDOBA", 183);

        conexiones.insertarArco("NECOCHEA", "TANDIL", 100);
        conexiones.insertarArco("NECOCHEA", "NEUQUEN", 900);

        conexiones.insertarArco("TANDIL", "PARANA", 33);
        conexiones.insertarArco("TANDIL", "ROSARIO", 99);

        conexiones.insertarArco("ANGACO", "SALTA", 43);

        conexiones.insertarArco("ROSARIO", "CORRIENTES", 25);

        conexiones.insertarArco("POSADAS", "CORRIENTES", 40);

        conexiones.insertarArco("CORRIENTES", "POSADAS", 30);

        conexiones.insertarArco("PARANA", "POSADAS", 68);
        conexiones.insertarArco("PARANA", "ROSARIO", 49);

        conexiones.insertarArco("RESISTENCIA", "SALTA", 60);
        conexiones.insertarArco("RESISTENCIA", "CORRIENTES", 100);

        conexiones.insertarArco("SALTA", "CORDOBA", 222);
    }
}
