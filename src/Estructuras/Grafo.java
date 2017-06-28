package Estructuras;

import java.util.LinkedList;

public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(String elem) {
        boolean seInserto = false;
        NodoVert aux = ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            seInserto = true;
        }
        return seInserto;
    }

    private NodoVert ubicarVertice(String buscado) {
        NodoVert aux = inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(String origen, String destino, double etiqueta) {
        boolean seInserto = false;
        NodoVert o = ubicarVertice(origen);
        if (o != null) {
            NodoVert d = ubicarVertice(destino);
            if (d != null) {
                NodoAdy aux = o.getPrimerAdy();
                if (aux != null) {
                    while (aux.getSigAdyacente() != null) {
                        aux = aux.getSigAdyacente();
                    }
                    aux.setSigAdyacente(new NodoAdy(d, etiqueta, null));
                } else {
                    o.setPrimerAdy(new NodoAdy(d, etiqueta, null));
                }
                seInserto = true;
            }
        }

        return seInserto;
    }

    public boolean eliminarVertice(String elem) {
        boolean seElimino = false;
        NodoVert anterior = inicio;
        if (anterior != null) { //Si no esta vacio
            if (anterior.getElem().equals(elem)) {//Si es el inicial
                inicio = inicio.getSigVertice();
                seElimino = true;
            } else {//no es el inicial loopeo por los vertices
                while (anterior.getSigVertice() != null && !seElimino) {
                    if (anterior.getSigVertice().getElem().equals(elem)) {
                        //Si encontre el anterior borro el elemento.
                        anterior.setSigVertice(anterior.getSigVertice().getSigVertice());
                        seElimino = true;
                    } else {
                        //sigo buscando
                        anterior = anterior.getSigVertice();
                    }
                }
            }
            if (seElimino) {
                eliminarAdyacentesAlVertice(elem);
            }
        }
        return seElimino;
    }

    private boolean eliminarAdyacentesAlVertice(String elem) {
        boolean seElimino = false;
        NodoVert vertAux = inicio;
        NodoAdy adyAux;
        while (vertAux != null) {//por cada vertice elimino todos los adyacentes que sean igual al 'elem'
            adyAux = vertAux.getPrimerAdy();
            if (adyAux != null) {
                if (adyAux.getVertice().getElem().equals(elem)) {//Si es el inicial
                    vertAux.setPrimerAdy(adyAux.getSigAdyacente());
                    seElimino = true;
                } else {//no es el inicial loopeo por los ady
                    while (adyAux.getSigAdyacente() != null) {
                        if (adyAux.getSigAdyacente().getVertice().getElem().equals(elem)) {  //Si encontre el anterior borro el elemento.
                            adyAux.setSigAdyacente(adyAux.getSigAdyacente().getSigAdyacente());
                            seElimino = true;
                        } else {//sigo buscando
                            adyAux = adyAux.getSigAdyacente();
                        }
                    }
                }
            }
            vertAux = vertAux.getSigVertice();
        }
        return seElimino;
    }

    public boolean eliminarAdyacente(String origen, String destino) {
        boolean seElimino = false;
        NodoVert auxVer = ubicarVertice(origen);
        if (auxVer != null) {
            NodoAdy auxAdy = auxVer.getPrimerAdy();
            if (auxAdy != null) {
                if (auxAdy.getVertice().getElem().equals(destino)) {
                    auxVer.setPrimerAdy(auxAdy.getSigAdyacente());
                    seElimino = true;
                } else {
                    while (auxAdy.getSigAdyacente() != null && !seElimino) {
                        if (auxAdy.getSigAdyacente().getVertice().getElem().equals(destino)) {
                            auxAdy.setSigAdyacente(auxAdy.getSigAdyacente().getSigAdyacente());
                            seElimino = true;
                        } else {
                            auxAdy = auxAdy.getSigAdyacente();
                        }
                    }
                }
            }
        }
        return seElimino;
    }

    public ListaStr listarProfundidad() {
        ListaStr visitados = new ListaStr();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (!visitados.pertenece(aux.getElem())) {
                profundidadDesde(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void profundidadDesde(NodoVert nodov, ListaStr visitados) {
        if (nodov != null) {
            visitados.insertarAlFinal(nodov.getElem());
            NodoAdy ady = nodov.getPrimerAdy();
            while (ady != null) {
                if (!visitados.pertenece(ady.getVertice().getElem())) {
                    profundidadDesde(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public boolean existeCamino(String origen, String destino) {
        boolean existe = false;

        NodoVert o = ubicarVertice(origen);
        NodoVert d = ubicarVertice(destino);

        if (o != null && d != null) {
            ListaStr visitados = new ListaStr();
            existe = existeCaminoAux(o, destino, visitados);
        }

        return existe;
    }

    private boolean existeCaminoAux(NodoVert origen, String destino, ListaStr visitados) {
        boolean existe = false;
        if (origen != null) {
            if (origen.getElem().equals(destino)) {
                existe = true;
            } else {
                visitados.insertarAlFinal(origen.getElem());
                NodoAdy ady = origen.getPrimerAdy();
                while (!existe && ady != null) {
                    if (!visitados.pertenece(ady.getVertice().getElem())) {
                        existe = existeCaminoAux(ady.getVertice(), destino, visitados);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return existe;
    }

    public ListaStr listarAnchura() {
        ListaStr visitados = new ListaStr();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (!visitados.pertenece(aux.getElem())) {
                anchuraDesde(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void anchuraDesde(NodoVert inicial, ListaStr visitados) {
        ColaStr cola = new ColaStr();
        cola.poner(inicial.getElem());
        while (!cola.esVacia()) {
            NodoVert auxVert = new NodoVert(cola.sacar());
            visitados.insertarAlFinal(auxVert.getElem());
            NodoAdy auxAdy = auxVert.getPrimerAdy();
            while (auxAdy != null) {
                if (!visitados.pertenece(auxVert.getElem())) {
                    cola.poner(auxVert.getElem());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
    }

    public ListaStr caminoMenorCantCiudades(String partida, String llegada) {
        ListaStr visitados = new ListaStr();
        ListaStr menor = new ListaStr();
        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);
        if (vPartida != null && vLlegada != null) {
            menor = caminoMenorCantCiudadesAux(vPartida, visitados, menor, llegada);
        }
        return menor;
    }

    private ListaStr caminoMenorCantCiudadesAux(NodoVert partida, ListaStr visitados, ListaStr menor, String llegada) {
        NodoAdy aux;
        ListaStr menorAux;
        if (!visitados.pertenece(partida.getElem())) {
            visitados.insertarAlFinal(partida.getElem());
            if (partida.getElem().equals(llegada)) {
                if (menor.esVacia()) {
                    menor = visitados.clonar();
                } else {
                    if (visitados.longitud() < menor.longitud()) {
                        menor = visitados.clonar();
                    }
                }
            } else {
                aux = partida.getPrimerAdy();
                while (aux != null) {
                    menorAux = caminoMenorCantCiudadesAux(aux.getVertice(), visitados, menor, llegada);
                    if (!menorAux.esVacia()) {
                        if (!menor.esVacia()) {
                            if (menorAux.longitud() < menor.longitud()) {
                                menor = menorAux.clonar();
                            }
                        } else {
                            menor = menorAux.clonar();
                        }
                    }
                    aux = aux.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud()-1);
        }
        return menor;
    }

    public double menorKilometraje(String partida, String llegada) {
        double salida = 0;
        ListaStr visitados = new ListaStr();
        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);
        if (vPartida != null && vLlegada != null) {
            salida = menorKilometrajeAux(vPartida, visitados, llegada, -1, -1);
        }
        return salida;
    }

    private double menorKilometrajeAux(NodoVert partida, ListaStr visitados, String llegada, double minimoHastaAhora, double kilometros) {
        //System.out.println("Entra con vertice " + partida.getElem());
        //System.out.println("Visitados hasta ahora " + visitados.toString());
        NodoAdy aux;
        if (!visitados.pertenece(partida.getElem())) {
            visitados.insertarAlFinal(partida.getElem());
            if (partida.getElem().equals(llegada)) {
                if (minimoHastaAhora == -1) {
                    minimoHastaAhora = kilometros;
                } else {
                    if (kilometros < minimoHastaAhora) {
                        minimoHastaAhora = kilometros;
                    }
                }
            } else {
                aux = partida.getPrimerAdy();
                while (aux != null) {
                    //System.out.println("etiqueta del rotulo " + aux.getEtiqueta());
                    minimoHastaAhora = menorKilometrajeAux(aux.getVertice(), visitados, llegada, minimoHastaAhora, kilometros + aux.getEtiqueta());
                    if (minimoHastaAhora == -1) {
                        minimoHastaAhora = kilometros;
                    }
                    aux = aux.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud()-1);
        }
        //System.out.println("Sale del vertice " + partida.getElem());
        return minimoHastaAhora;
    }

    public ListaStr caminoConAlojamiento(String partida, String llegada, Diccionario dicc) {
        ListaStr visitados = new ListaStr();
        ListaStr menor = new ListaStr();
        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);
        if (vPartida != null && vLlegada != null) {
            menor = caminoConAlojamientoAux(vPartida, visitados, menor, llegada, dicc);
        }
        return menor;
    }

    private ListaStr caminoConAlojamientoAux(NodoVert partida, ListaStr visitados, ListaStr menor, String llegada, Diccionario dicc) {
        NodoAdy auxAdy;
        ListaStr menorAux;
        boolean alojActual = false;
        if (!visitados.pertenece(partida.getElem())) {
            visitados.insertarAlFinal(partida.getElem());
            if (partida.getElem().equals(llegada)) {
                if (menor.esVacia()) {
                    menor = visitados.clonar();
                } else {
                    if (visitados.longitud() < menor.longitud()) {
                        menor = visitados.clonar();
                    }
                }
            } else {
                auxAdy = partida.getPrimerAdy();
                while (auxAdy != null) {
                    alojActual = dicc.recuperarElemento(auxAdy.getVertice().getElem()).isAlojamientoDisp();
                    if (alojActual || auxAdy.getVertice().getElem().equals(llegada)) {
                        menorAux = caminoConAlojamientoAux(auxAdy.getVertice(), visitados, menor, llegada, dicc);
                        if (!menorAux.esVacia()) {
                            if (!menor.esVacia()) {
                                if (menorAux.longitud() < menor.longitud()) {
                                    menor = menorAux.clonar();
                                }
                            } else {
                                menor = menorAux.clonar();
                            }
                        }
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud()-1);
        }
        return menor;
    }

    /*public ListaStr existeCaminoAlojamiento(String partida, String llegada, Diccionario dicc) {
        NodoVert vertPartida = ubicarVertice(partida);
        NodoVert vertLlegada = ubicarVertice(llegada);
        ListaStr visitados = new ListaStr();
        boolean res;
        if (vertPartida != null && vertLlegada != null) {
            res = existeCaminoAlojamientoAux(vertPartida, visitados, partida, llegada, false, dicc);
        }
        if (visitados.longitud() == 1 && visitados.pertenece(partida)) {
            visitados.vaciar();
        }
        if (!visitados.pertenece(llegada)) {
            visitados.vaciar();
        }
        return visitados;
    }

    private boolean existeCaminoAlojamientoAux(NodoVert partida, ListaStr visitados, String origen, String llegada, boolean res, Diccionario dicc) {
        NodoAdy aux;
        if (!visitados.pertenece(partida.getElem())) {
            if (partida.getElem().equalsIgnoreCase(origen) || partida.getElem().equalsIgnoreCase(llegada)
                    || dicc.recuperarElemento(partida.getElem()).isAlojamientoDisp()) {
                visitados.insertar(partida.getElem(), visitados.longitud() + 1);
                aux = partida.getPrimerAdy();
                while (!res && aux != null) {
                    if (aux.getVertice().getElem().equalsIgnoreCase(llegada)) {
                        res = true;
                        visitados.insertar(llegada, visitados.longitud() + 1);
                    } else {
                        res = existeCaminoAlojamientoAux(aux.getVertice(), visitados, origen, llegada, res, dicc);
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return res;
    }*/
    @Override
    public String toString() {
        String cad = "";
        NodoVert auxVert = inicio;
        NodoAdy auxAdy;
        if (auxVert != null) {
            while (auxVert != null) {
                cad = cad + "\n----------------------------------------"
                        + "\n Ciudad: " + auxVert.getElem();
                auxAdy = auxVert.getPrimerAdy();
                if (auxAdy != null) {
                    while (auxAdy != null) {
                        cad = cad + "\n conectado con: " + auxAdy.getVertice().getElem() + " a: " + auxAdy.getEtiqueta() + " km de distancia.";
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                } else {
                    cad = cad + "\n Sin rutas!";
                }
                auxVert = auxVert.getSigVertice();
            }
        } else {
            cad = "Vacio!";
        }
        return cad;
    }

    public int cantVertices() {
        int cant = 0;
        NodoVert aux = inicio;

        while (aux != null) {
            cant++;
            aux = aux.getSigVertice();
        }

        return cant;
    }

    public long distancia(String origen, String destino) {
        return -1;
    }

    private double distanciaAux(NodoVert origen, String destino, double distancia, ListaStr visitados) {
        NodoAdy adyAux = origen.getPrimerAdy();
        if (origen.getElem().equals(destino)) {
            //es el elemento
        } else {
            while (adyAux != null) {

            }
        }

        return 1D;
    }

    public double intentoDisk(String origen, String destino) {
        NodoVert origenAux = ubicarVertice(origen);
        NodoVert destinoAux = ubicarVertice(destino);

        return 0.0;
    }

    private int leastDistance(LinkedList<Double> dist) {
        double menor;
        int p = 0;

        for (int i = 0; i < dist.size() - 1; i++) {
            menor = dist.get(i);
            if (menor > dist.get(i + 1)) {
                menor = dist.get(i + 1);
                p = i + 1;
            }
        }
        return p;
    }

    private double peso(NodoVert a, NodoAdy b) {
        NodoAdy auxAdy = a.getPrimerAdy();
        double peso = -1;
        while (auxAdy != null && peso == -1) {
            if (b.getVertice().getElem().equals(auxAdy.getVertice().getElem())) {
                peso = auxAdy.getEtiqueta();
            } else {
                auxAdy = auxAdy.getSigAdyacente();
            }
        }

        return peso;
    }

    private int posArreglo(double[] arr) {
        double menor;
        int p = -1;
        for (int i = 0; i < arr.length - 1; i++) {
            menor = arr[i];
            if (menor > arr[i + 1]) {
                menor = arr[i + 1];
                p = i + 1;
            }
        }
        return p;
    }

    public ListaStr dijkstra(String origen, String destino) {
        ListaStr camino=null, visitados, vertices;
        ColaStr colaAdy = new ColaStr();
        NodoVert auxVert;
        NodoAdy auxAdy;
        String verticeActual;
        String[] anterior;
        double[] distancia;
        int cantElementos;

        if (this.inicio != null) {//Si el grafo esta vacio, return null
            visitados = new ListaStr();
            vertices = new ListaStr();
            auxVert = this.inicio;

            while (auxVert != null) {
                verticeActual = auxVert.getElem();
                vertices.insertarAlFinal(verticeActual);
                auxVert = auxVert.getSigVertice();
            }

            cantElementos = vertices.longitud();
            anterior = new String[cantElementos];
            distancia = new double[cantElementos];
            for (int i = 0; i < cantElementos; i++) {
                distancia[i] = Integer.MAX_VALUE;
                anterior[i] = null;
            }
            
            int posModificar = 0;
            int posDeDondeSaleArco = 0;
            double nuevaDistancia = 0;
            verticeActual = origen; // ORIGEN!!!
            distancia[vertices.getPos(verticeActual)] = 0;
            anterior[vertices.getPos(verticeActual)] = null;
            while (verticeActual != null) { //HASTA QUE VERTICEACTUAL SEA NULL ES DECIR NO TENGA MAS PARA ITERAR
                auxAdy = getRefVertice(verticeActual).getPrimerAdy(); //ACA LEVANTO EL VERTICE(NODO) PARA PREGUNTARLE POR SU ADY
                while (auxAdy != null) {//MIENTRAS ESE ADY NO SEA NULLO
                    if (!visitados.pertenece(verticeActual)) { //ESTA ES LA LISTA DE VISITADOS PARA NO REPETIR
                        posModificar = vertices.getPos(auxAdy.getVertice().getElem()); //GETPOS ME DA EL INDICE PARA SABER CUAL MODIFICAR
                        posDeDondeSaleArco = vertices.getPos(verticeActual); //ACA SE DE CUAL NODO VENGO PARA SUMAR
                        nuevaDistancia = auxAdy.getEtiqueta() + distancia[posDeDondeSaleArco]; //LEVANTO LA NUEVA DISTANCIA
                        if (distancia[posModificar] > nuevaDistancia ) {//EL PASO DIJTRAM QUE HACE LA MAGIA
                            distancia[posModificar] = nuevaDistancia;
                            anterior[posModificar] = verticeActual;
                        }
                        colaAdy.poner(auxAdy.getVertice().getElem()); //ACA AGREGO AL ADY ACTUAL PARA SEGUIR USANDO ELEMENTOS DE ACA( PARA QUE NO SEA RANDOM) GRACIAS CUNI :) IDEA TUYA
                        auxAdy = auxAdy.getSigAdyacente(); 
                    } else { //SI SE VA AL ELSE SIGNIFICA QUE EL VERTICE ACTUAL YA SE HABIA VISITADO NO HACE FALTA METERTELO EN EL UPITE DIGO LA COLA
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
                visitados.insertarAlFinal(verticeActual);//inserto el vertice que anote todos sus arcos
                verticeActual = colaAdy.sacar();//ACA RECUPERO EL PRIMERO (SE PODRIA USAR COLA DE PRIORIDAD PARA QUE NO SEA GREEDY)
            }
            // DEBUG ONLY
            for (int i = 0; i < distancia.length; i++) {
                System.out.println("Vert: " + vertices.recuperar(i) + "\tDist: "+ distancia[i] + "\tPrev: " + anterior[i]);
            }
            //DEBUG ONLY
            camino = armarCaminoDijs(vertices, distancia, anterior, origen, destino);
        }
        
        return camino;
    }
    
    private NodoVert getRefVertice(String elem) {
        NodoVert buscado = null;
        NodoVert auxVert = this.inicio;
        boolean corte = false;

        while (!corte && auxVert != null) {
            if (auxVert.getElem().equals(elem)) {
                corte = true;
                buscado = auxVert;
            }
            auxVert = auxVert.getSigVertice();
        }

        return buscado;
    }
    
    private ListaStr armarCaminoDijs(ListaStr vert, double[] dist, String[] anterior, String origen, String destino) {
        ListaStr camino = new ListaStr();
        camino.insertar(destino, 0); // ultimo el destino
        int pos = -1;
        
        String vertActual = destino;
        while(!vertActual.equals(origen)) {
            pos = vert.getPos(vertActual);
            vertActual = anterior[pos];
            camino.insertar(vertActual, 0);   
        }
        return camino;
    }
}
