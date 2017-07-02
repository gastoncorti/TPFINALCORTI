package Estructuras;

public class Grafo {

    private NodoVert inicio;
    private int cantVertices;

    public Grafo() {
        this.inicio = null;
        this.cantVertices = 0;
    }

    public boolean insertarVertice(String elem) {
        this.inicio = new NodoVert(elem, this.inicio);
        this.cantVertices++;
        return true;
    }

    private NodoVert ubicarVertice(String buscado) {
        NodoVert aux = inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(String origen, String destino, double etiqueta) {
        boolean seInserto = true;
        boolean noExisteArco = true;
        NodoVert vertOrigen = ubicarVertice(origen);
        if (vertOrigen != null) {
            NodoVert vertDestino = ubicarVertice(destino);
            if (vertDestino != null) {
                NodoAdy adyActual = vertOrigen.getPrimerAdy();
                if (adyActual != null) {
                    if (!adyActual.getVertice().getElem().equals(destino)) {
                        while (adyActual.getSigAdyacente() != null && noExisteArco) {
                            adyActual = adyActual.getSigAdyacente();
                        }
                    } else {
                        noExisteArco = false;
                    }

                    if (noExisteArco) {
                        adyActual.setSigAdyacente(new NodoAdy(vertDestino, etiqueta, null));
                    } else {
                        seInserto = false;
                    }
                } else {
                    vertOrigen.setPrimerAdy(new NodoAdy(vertDestino, etiqueta, null));
                }
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
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);

        if (vertOrigen != null && vertDestino != null) {
            existe = existeCaminoAux(vertOrigen, destino, new ListaStr());
        }

        return existe;
    }

    private boolean existeCaminoAux(NodoVert origen, String destino, ListaStr visitados) {
        boolean existe = false;
        if (origen != null) {
            if (origen.getElem().equals(destino)) {
                existe = true;
            } else {
                visitados.insertar(origen.getElem(), 0);
                NodoAdy adyActual = origen.getPrimerAdy();
                while (!existe && adyActual != null) {
                    if (!visitados.pertenece(adyActual.getVertice().getElem())) {
                        existe = existeCaminoAux(adyActual.getVertice(), destino, visitados);
                    }
                    adyActual = adyActual.getSigAdyacente();
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
            visitados.insertar(auxVert.getElem(), 0);
            NodoAdy auxAdy = auxVert.getPrimerAdy();
            while (auxAdy != null) {
                if (!visitados.pertenece(auxVert.getElem())) {
                    cola.poner(auxVert.getElem());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
    }

    public ListaStr caminnoMenorCantVertices(String partida, String llegada) {
        ListaStr visitados = new ListaStr();
        ListaStr menor = new ListaStr();
        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);
        if (vPartida != null && vLlegada != null) {
            menor = caminoMenorCantVerticesAux(vPartida, visitados, menor, llegada);
        }
        return menor;
    }

    private ListaStr caminoMenorCantVerticesAux(NodoVert partida, ListaStr visitados, ListaStr menor, String llegada) {
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
                while (aux != null) { // MODIFICACION CUNI
                    menorAux = caminoMenorCantVerticesAux(aux.getVertice(), visitados, menor, llegada);
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
            visitados.eliminar(visitados.longitud() - 1);
        }
        return menor;
    }

    /*PRUEBA BFS*/
    public ListaStr BFS(String partida, String llegada) {
        ListaStr visitados = new ListaStr();
        ListaStr menor = new ListaStr();
        ColaStr cola = new ColaStr();

        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);

        if (vPartida != null && vLlegada != null) {
            NodoVert vertActual = this.inicio;
            while (vertActual != null) {
                if (!visitados.pertenece(vertActual.getElem())) {
                    cola.poner(vertActual.getElem());
                    while (!cola.esVacia()) {
                        NodoVert auxVert = new NodoVert(cola.sacar());
                        visitados.insertar(auxVert.getElem(), 0);
                        NodoAdy auxAdy = auxVert.getPrimerAdy();
                        while (auxAdy != null) {
                            if (!visitados.pertenece(auxVert.getElem())) {
                                cola.poner(auxVert.getElem());
                            }
                            auxAdy = auxAdy.getSigAdyacente();
                        }
                    }
                }
                vertActual = vertActual.getSigVertice();
            }
        }
        return menor;
    }

    /*FIN PRUEBA*/
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
        boolean alojActual;
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
            visitados.eliminar(visitados.longitud() - 1);
        }
        return menor;
    }

    @Override
    public String toString() {
        String cad = "----------------GRAFO-------------------\n";
        NodoVert auxVert = inicio;
        NodoAdy auxAdy;
        if (auxVert != null) {
            while (auxVert != null) {
                auxAdy = auxVert.getPrimerAdy();
                if (auxAdy != null) {
                    while (auxAdy != null) {
                        cad += auxVert.getElem() + " -- " + auxAdy.getEtiqueta() + " -- " + auxAdy.getVertice().getElem() + "\n";
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                } else {
                    cad += auxVert.getElem() + " Sin Arcos\n";
                }
                auxVert = auxVert.getSigVertice();
            }
            cad += "----------------------------------------\n";
        } else {
            cad = "Vacio!";
        }
        return cad;
    }

    //METODO SOLO PARA TEST!
    public ListaStr dijkstramCamino(String origen, String destino) {
        ListaStr visitados, vertices = null;
        NodoVert auxVert;
        NodoAdy auxAdy;
        String verticeActual;
        String[] previo = null;
        double[] distancia = null;
        int cantElementos = 0;

        if (this.inicio != null) {//Si el grafo esta vacio, return null
            visitados = new ListaStr();
            vertices = new ListaStr();
            auxVert = this.inicio;

            while (auxVert != null) {
                verticeActual = auxVert.getElem();
                vertices.insertarAlFinal(verticeActual);
                auxVert = auxVert.getSigVertice();
                cantElementos++;
            }

            previo = new String[cantElementos];
            distancia = new double[cantElementos];
            for (int i = 0; i < cantElementos; i++) {
                distancia[i] = Integer.MAX_VALUE;
                previo[i] = null;
            }

            int posModificar;
            int posDeDondeSaleArco;
            double nuevaDistancia;
            int posMenor;
            verticeActual = origen; // ORIGEN!!!
            distancia[vertices.getPos(verticeActual)] = 0;
            previo[vertices.getPos(verticeActual)] = null;
            while (visitados.longitud() < cantElementos) { //HASTA QUE VERTICEACTUAL SEA NULL ES DECIR NO TENGA MAS PARA ITERAR
                visitados.insertar(verticeActual, 0);
                auxAdy = getRefVertice(verticeActual).getPrimerAdy(); //ACA LEVANTO EL VERTICE(NODO) PARA PREGUNTARLE POR SU ADY
                while (auxAdy != null) {//MIENTRAS ESE ADY NO SEA NULL
                    if (!visitados.pertenece(auxAdy.getVertice().getElem())) { //ESTA ES LA LISTA DE VISITADOS PARA NO REPETIR
                        posModificar = vertices.getPos(auxAdy.getVertice().getElem()); //GETPOS ME DA EL INDICE PARA SABER CUAL MODIFICAR
                        posDeDondeSaleArco = vertices.getPos(verticeActual); //ACA SE DE CUAL NODO VENGO PARA SUMAR
                        nuevaDistancia = auxAdy.getEtiqueta() + distancia[posDeDondeSaleArco]; //LEVANTO LA NUEVA DISTANCIA
                        if (distancia[posModificar] > nuevaDistancia) {//EL PASO DIJTRAM QUE HACE LA MAGIA
                            distancia[posModificar] = nuevaDistancia;
                            previo[posModificar] = verticeActual;
                        }
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
                posMenor = 0;
                for (int i = 0; i < cantElementos; i++) { // RECUPERO EL MENOR(DISTANCIA) QUE NO FUE VISITADO 
                    if (distancia[i] < distancia[posMenor] && !visitados.pertenece(vertices.recuperar(i))) {
                        posMenor = i;
                    }
                    verticeActual = vertices.recuperar(posMenor);
                }
            }

            for (int i = 0; i < distancia.length; i++) {
                System.out.println("Vert: " + vertices.recuperar(i) + "\tDist: " + distancia[i] + "\tPrev: " + previo[i]);
            }
        }
        return armarCaminoDijs(vertices, distancia, previo, origen, destino);
    }

    public double menorDistancia(String origen, String destino) {
        double distancia = -1;
        if (this.inicio != null && ubicarVertice(origen) != null && ubicarVertice(destino) != null) {
            distancia = dijstramMenorDistancia(origen, destino);
        }
        return distancia;
    }

    private double dijstramMenorDistancia(String origen, String destino) {
        ListaStr visitados = new ListaStr(), vertices = new ListaStr();
        NodoVert auxVert = this.inicio;
        NodoAdy auxAdy;
        String  verticeActual = origen;;
        double[] distancia = new double[cantVertices];
        int cont = 0;
        int posModificar;
        int posDeDondeSaleArco;
        double nuevaDistancia;
        int posMenor;

        while (auxVert != null) {
            vertices.insertarAlFinal(auxVert.getElem());
            auxVert = auxVert.getSigVertice();
            distancia[cont] = Double.MAX_VALUE;
            cont++;
        }
       
        distancia[vertices.getPos(verticeActual)] = 0;
        while (visitados.longitud() < cantVertices) {
            visitados.insertar(verticeActual, 0);
            auxAdy = getRefVertice(verticeActual).getPrimerAdy();
            while (auxAdy != null) {
                if (!visitados.pertenece(auxAdy.getVertice().getElem())) {
                    posModificar = vertices.getPos(auxAdy.getVertice().getElem());
                    posDeDondeSaleArco = vertices.getPos(verticeActual);
                    nuevaDistancia = auxAdy.getEtiqueta() + distancia[posDeDondeSaleArco];
                    if (distancia[posModificar] > nuevaDistancia) {
                        distancia[posModificar] = nuevaDistancia;
                    }
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
            posMenor = 0;
            for (int i = 0; i < cantVertices; i++) {
                if (distancia[i] < distancia[posMenor] && !visitados.pertenece(vertices.recuperar(i))) {
                    posMenor = i;
                }
            }
            verticeActual = vertices.recuperar(posMenor);

        }
        return distancia[vertices.getPos(destino)];
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
        int pos;

        String vertActual = destino;
        while (!vertActual.equals(origen)) {
            pos = vert.getPos(vertActual);
            vertActual = anterior[pos];
            camino.insertar(vertActual, 0);
        }
        return camino;
    }
}
