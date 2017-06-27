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
            visitados.insertar(partida.getElem(), visitados.longitud() + 1);
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
            visitados.eliminar(visitados.longitud());
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
            visitados.insertar(partida.getElem(), visitados.longitud() + 1);
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
            visitados.eliminar(visitados.longitud());
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
            visitados.insertar(partida.getElem(), visitados.longitud() + 1);
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
            visitados.eliminar(visitados.longitud());
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
        ListaStr camino = null, visitados, vertices;
        NodoVert auxVert;
        NodoAdy auxAdy;
        String elemActual;
        String[] anterior;
        double[] distancia;
        int cantElementos;

        if (this.inicio != null) {//Si el grafo esta vacio, return null
            visitados = new ListaStr();
            vertices = new ListaStr();
            auxVert = this.inicio;

            while (auxVert != null) {
                elemActual = auxVert.getElem();
                vertices.insertarAlFinal(elemActual);
                auxVert = auxVert.getSigVertice();
            }

            cantElementos = vertices.longitud();
            anterior = new String[cantElementos];
            distancia = new double[cantElementos];
            for (int i = 0; i < cantElementos; i++) {
                distancia[i] = Integer.MAX_VALUE;
                anterior[i] = null;
            }

            int posActual = 0;
            int posModificar = 0;
            int posAnterior = 0;
            double nuevaDistancia = 0;
            elemActual = "A"; // ORIGEN!!!
            distancia[vertices.getPos("A")] = 0;
            anterior[vertices.getPos("A")] = null;
            while (posActual < cantElementos) {
                //elemActual = vertices.recuperar(posActual);
                auxAdy = getRefVertice(elemActual).getPrimerAdy();
                while (auxAdy != null) {
                    if (!visitados.pertenece(elemActual)) {
                        posModificar = vertices.getPos(auxAdy.getVertice().getElem());
                        //posAnterior = vertices.getPos(vertices.recuperar(posActual-1));
                        posAnterior = (getRefVertice(anterior[posModificar]) == null) ? vertices.getPos(elemActual) : vertices.getPos(getRefVertice(anterior[posModificar]).getElem());
                        nuevaDistancia = auxAdy.getEtiqueta() + distancia[posAnterior];
                        if (distancia[posModificar] > nuevaDistancia ) {
                            distancia[posModificar] = nuevaDistancia;
                            anterior[posModificar] = elemActual;
                        }
                        auxAdy = auxAdy.getSigAdyacente();
                    } else {
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                }
                visitados.insertarAlFinal(elemActual);
                
                posActual++;
                elemActual = vertices.recuperar(posActual);
            }

            for (int i = 0; i < distancia.length; i++) {
                System.out.println("Vert: " + vertices.recuperar(i) + "\tDist: "+ distancia[i] + "\tPrev: " + anterior[i]);
            }
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
    /* public void dijkstra(String origen, String destino) {
        ListaStr lista = new ListaStr();
        NodoVert auxVert = this.inicio;
        int cantVert = cantVertices();

        LinkedList<Double> dist = new LinkedList<Double>();
        NodoVert[] prev = new NodoVert[cantVert];
        NodoVert[] vertices = new NodoVert[cantVert];
        String vertActual = "";

        for (int i = 0; i < cantVert; i++) {
            dist.add(Double.MAX_VALUE);
            prev[i] = null;

            lista.insertarAlFinal(auxVert.getElem());
            vertices[i] = auxVert;

            auxVert = auxVert.getSigVertice();
        }

        dist.set(dist.size() - 1, 0D);

        int pos;
        double alt;
        NodoAdy auxAdy;

        while (!lista.esVacia()) {
            pos = leastDistance(dist);
            //auxVert = ubicarVertice(lista.recuperar(pos));
            auxVert = vertices[pos];
            lista.eliminar(pos + 1);
            auxAdy = auxVert.getPrimerAdy();

            while (auxAdy != null) {
                alt = dist.get(pos) + peso(auxVert, auxAdy);
                if (alt < dist.get(pos)) {
                    dist.set(pos, alt);
                    prev[pos] = auxVert;
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        //loop de los vertices
        //loop de los ady

    }*/
 /* public class Dijkstra {

        // Dijkstra's algorithm to find shortest path from s to all other nodes
        public int[] dijkstra(WeightedGraph G, int s) {

            final int[] dist = new int[G.size()];  // shortest known distance from "s"
            final int[] pred = new int[G.size()];  // preceeding node in path
            final boolean[] visited = new boolean[G.size()]; // all false initially

            for (int i = 0; i < dist.length; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            dist[s] = 0;

            for (int i = 0; i < dist.length; i++) {
                final int next = minVertex(dist, visited);
                visited[next] = true;

                // The shortest path to next is dist[next] and via pred[next].
                final int[] n = G.neighbors(next);
                for (int j = 0; j < n.length; j++) {
                    final int v = n[j];
                    final int d = dist[next] + G.getWeight(next, v);
                    if (dist[v] > d) {
                        dist[v] = d;
                        pred[v] = next;
                    }
                }
            }
            return pred;  // (ignore pred[s]==0!)
        }

        private int minVertex(int[] dist, boolean[] v) {
            int x = Integer.MAX_VALUE;
            int y = -1;   // graph not connected, or no unvisited vertices
            for (int i = 0; i < dist.length; i++) {
                if (!v[i] && dist[i] < x) {
                    y = i;
                    x = dist[i];
                }
            }
            return y;
        }

        public void printPath(WeightedGraph G, int[] pred, int s, int e) {
            final java.util.ArrayList path = new java.util.ArrayList();
            int x = e;
            while (x != s) {
                path.add(0, G.getLabel(x));
                x = pred[x];
            }
            path.add(0, G.getLabel(s));
            System.out.println(path);
        }

    }*/
}
