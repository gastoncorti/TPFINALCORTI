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
                    adyAux.setSigAdyacente(adyAux.getSigAdyacente());
                    seElimino = true;
                } else {//no es el inicial loopeo por los ady
                    while (adyAux.getSigAdyacente() != null && !seElimino) {
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
        NodoVer auxVer = ubicarVertice(origen);
        if (auxVer != null) {
            NodoAdy auxAdy = auxVer.getPrimerAdy();
            if (auxAdy != null) {
                if (auxAdy.getVertice().getElemento().equals(destino)) {
                    auxVer.setPrimerAdy(auxAdy.getSigAdy());
                    seElimino = true;
                } else {
                    while (auxAdy.getSigAdy() != null && !seElimino) {
                        if (auxAdy.getSigAdy().getVertice().getElemento().equals(destino)) {
                            auxAdy.setSigAdy(auxAdy.getSigAdy().getSigAdy());
                            seElimino = true;
                        } else {
                            auxAdy = auxAdy.getSigAdy();
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
            visitados.insertar(nodov.getElem());
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
                visitados.insertar(origen.getElem());
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
            visitados.insertar(auxVert.getElem());
            NodoAdy auxAdy = auxVert.getPrimerAdy();
            while (auxAdy != null) {
                if (!visitados.pertenece(auxVert.getElem())) {
                    cola.poner(auxVert.getElem());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
    }

    public ListaStr caminoMasCorto(String partida, String llegada) {
        ListaStr visitados = new ListaStr();
        ListaStr menor = new ListaStr();
        NodoVert vPartida = ubicarVertice(partida);
        NodoVert vLlegada = ubicarVertice(llegada);
        if (vPartida != null && vLlegada != null) {
            menor = caminoMasCortoAux(vPartida, visitados, menor, llegada);
        }
        return menor;
    }

    private ListaStr caminoMasCortoAux(NodoVert partida, ListaStr visitados, ListaStr menor, String llegada) {
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
                    menorAux = caminoMasCortoAux(aux.getVertice(), visitados, menor, llegada);
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

    public ListaStr existeCaminoAlojamiento(String partida, String llegada, Diccionario dicc) {
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
    }

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
    
     public void dijkstra(String origen, String destino) {
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
           
            lista.insertar(auxVert.getElem());
            vertices[i] = auxVert;
            
            auxVert = auxVert.getSigVertice();
        }
        
        dist.set(dist.size()-1, 0D);
        
        int pos;
        double alt;
        NodoAdy auxAdy;
        
        while (!lista.esVacia()) {
            pos = leastDistance(dist);
            //auxVert = ubicarVertice(lista.recuperar(pos));
            auxVert = vertices[pos];
            lista.eliminar(pos+1);
            auxAdy = auxVert.getPrimerAdy();
            
            while(auxAdy != null) {
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
 
    }
     private int leastDistance(LinkedList<Double> dist) {
         double menor;
         int p = 0;
         
         for (int i = 0; i < dist.size() - 1; i++) {
             menor = dist.get(i);
             if(menor > dist.get(i+1)) {
                 menor = dist.get(i+1);
                 p = i+1;
             } 
         }
         return p;
     }
     /*back up listalinked
          public void dijkstra(String origen, String destino) {
        ListaStr lista = new ListaStr();
        NodoVert auxVert = this.inicio;
        int cantVert = cantVertices();
        
        double[] dist = new double[cantVert];
        NodoVert[] prev = new NodoVert[cantVert];
        NodoVert[] vertices = new NodoVert[cantVert];
        String vertActual = "";
        
        for (int i = 0; i < cantVert; i++) {
            dist[i] = Double.MAX_VALUE;
            prev[i] = null;
           
            lista.insertar(auxVert.getElem());
            vertices[i] = auxVert;
            
            auxVert = auxVert.getSigVertice();
        }
        
        dist[cantVert-1] = 0;
        int pos;
        double alt;
        NodoAdy auxAdy;
        
        while (!lista.esVacia()) {
            pos = posArreglo(dist);
            //auxVert = ubicarVertice(lista.recuperar(pos));
            auxVert = vertices[pos];
            lista.eliminar(pos+1);
            auxAdy = auxVert.getPrimerAdy();
            
            while(auxAdy != null) {
                alt = dist[pos] + peso(auxVert, auxAdy);
                if (alt < dist[pos]) {
                    dist[pos] = alt;
                    prev[pos] = auxVert;
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        //loop de los vertices
            //loop de los ady
 
    }
     */
     private double peso(NodoVert a, NodoAdy b) {
         NodoAdy auxAdy = a.getPrimerAdy();
         double peso = -1;
         while(auxAdy != null && peso == -1) {
             if(b.getVertice().getElem().equals(auxAdy.getVertice().getElem())) {
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
             if(menor > arr[i+1]) {
                 menor = arr[i+1];
                 p = i+1;
             } 
         }
         return p;
     }
}
