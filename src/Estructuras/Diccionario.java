package Estructuras;

import Utiles.Ciudad;

public class Diccionario {

    private NodoDic raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean insertar(String clave, Ciudad nueva) {
        boolean esCorrecto;
        if (raiz != null) {
            esCorrecto = insertarAux(clave, nueva, raiz, null);
        } else {
            raiz = new NodoDic(clave, nueva);
            esCorrecto = true;
        }
        return esCorrecto;
    }

    //BALANCE = altHijoIZQ - alturaHijoDER
    private boolean insertarAux(String clave, Ciudad nueva, NodoDic raizActual, NodoDic padre) {
        boolean seInserto = false;
        if (!raizActual.getClave().equals(clave)) {
            if (raizActual.getClave().compareTo(clave) > 0) {
                if (raizActual.getIzq() != null) {
                    seInserto = insertarAux(clave, nueva, raizActual.getIzq(), raizActual);
                } else {
                    raizActual.setIzq(new NodoDic(clave, nueva));
                    seInserto = true;
                }
            } else {
                if (raizActual.getDer() != null) {
                    seInserto = insertarAux(clave, nueva, raizActual.getDer(), raizActual);
                } else {
                    raizActual.setDer(new NodoDic(clave, nueva));
                    seInserto = true;
                }
            }
            if (seInserto) {
                raizActual.setAltura(alturaNodo(raizActual));
                balancear(raizActual, padre);
            }

        }
        return seInserto;
    }

    public boolean eliminar(String clave) {
        boolean seElimino = false;
        if (raiz != null) {
            seElimino = eliminarAux(clave, raiz, null);
        }
        return seElimino;
    }

    private boolean eliminarAux(String clave, NodoDic nodoElim, NodoDic padre) {
        boolean seElimino = false;
        String cantHijos;
        String claveAux;
        Ciudad ciudadAux;
        if (nodoElim != null) {
            //Lo busco por izq
            if (clave.compareTo(nodoElim.getClave()) < 0) {
                seElimino = eliminarAux(clave, nodoElim.getIzq(), nodoElim);
                nodoElim.setAltura(alturaNodo(nodoElim));
                //Lo busco por der
            } else if (clave.compareTo(nodoElim.getClave()) > 0) {
                seElimino = eliminarAux(clave, nodoElim.getDer(), nodoElim);
                nodoElim.setAltura(alturaNodo(nodoElim));
            } else {//Lo encontre ahora ver cantidad de hijos
                //Switch de caso eliminacion
                cantHijos = hijos(nodoElim);
                switch (cantHijos) {
                    case "AMBOS":
                            //buscar sustituto hoja menor en subarbol derecho
                            NodoDic sustituto = buscarSustituto(nodoElim.getDer());
                            claveAux = sustituto.getClave();
                            ciudadAux = sustituto.getCiudad();
                            eliminarAux(sustituto.getClave(), raiz, null);
                            nodoElim.setClave(claveAux);
                            nodoElim.setCiudad(ciudadAux);
                            nodoElim.setAltura(alturaNodo(nodoElim));
                            //padre.setAltura(alturaNodo(padre));
                        break;
                    case "IZQ":
                        if (padre == null) {
                            raiz = raiz.getIzq();
                            raiz.setAltura(alturaNodo(raiz));
                        } else {
                            if (padre.getClave().compareTo(nodoElim.getClave()) > 0) {
                                padre.setIzq(nodoElim.getIzq());
                            } else {
                                padre.setDer(nodoElim.getIzq());
                            }
                            //padre.setAltura(alturaNodo(padre));
                        }
                        break;
                    case "DER":
                        if (padre == null) {
                            raiz = raiz.getDer();
                            raiz.setAltura(alturaNodo(raiz));
                        } else {
                            if (padre.getClave().compareTo(nodoElim.getClave()) > 0) {
                                padre.setIzq(nodoElim.getDer());
                            } else {
                                padre.setDer(nodoElim.getDer());
                            }
                            //padre.setAltura(alturaNodo(padre));
                        }
                        break;
                    default:
                        if (padre.getClave().compareTo(nodoElim.getClave()) > 0) {
                            padre.setIzq(null);
                        } else {
                            padre.setDer(null);
                        }
                        break;
                }
                if (padre != null) {
                    padre.setAltura(alturaNodo(padre));
                }
                seElimino = true;
            }
            if (seElimino) {
                balancear(nodoElim, padre);
            }
        }
        return seElimino;
    }

    private String hijos(NodoDic nodoElim) {
        String caso = "";
        if (nodoElim.getIzq() != null) {
            if (nodoElim.getDer() != null) {
                caso = "AMBOS";
            } else {
                caso = "IZQ";
            }
        } else {
            if (nodoElim.getDer() != null) {
                caso = "DER";
            }
        }
        return caso;
    }

    private NodoDic buscarSustituto(NodoDic nodoElim) {
        NodoDic sustituto = nodoElim;
        while (sustituto.getIzq() != null) {
            sustituto = sustituto.getIzq();
        }
        return sustituto;
    }

    private void balancear(NodoDic nodo, NodoDic padre) {
        int balance;
        NodoDic aux;
        balance = balance(nodo);
        if (balance < -1) {
            aux = nodo.getDer();
            balance = balance(aux);
            if (balance == 1) {
                nodo.setDer(rotacionDerecha(nodo.getDer()));
            }
            if (padre == null) {
                raiz = rotacionIzquierda(nodo);
            } else {
                if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                    padre.setIzq(rotacionIzquierda(nodo));
                } else {
                    padre.setDer(rotacionIzquierda(nodo));
                }
                padre.setAltura(alturaNodo(padre));
            }
        }
        if (balance > 1) {
            aux = nodo.getIzq();
            balance = balance(aux);
            if (balance == -1) {
                nodo.setIzq(rotacionIzquierda(nodo.getIzq()));
            }
            if (padre == null) {
                raiz = rotacionDerecha(nodo);
            } else {
                if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                    padre.setIzq(rotacionDerecha(nodo));
                } else {
                    padre.setDer(rotacionDerecha(nodo));
                }
                padre.setAltura(alturaNodo(padre));
            }
        }
    }

    private NodoDic rotacionDerecha(NodoDic nodo) {
        NodoDic aux = nodo.getIzq();
        NodoDic aux2 = aux.getDer();
        aux.setDer(nodo);
        nodo.setIzq(aux2);
        nodo.setAltura(alturaNodo(nodo));
        aux.setAltura(alturaNodo(aux));
        return aux;
    }

    private NodoDic rotacionIzquierda(NodoDic nodo) {
        NodoDic aux = nodo.getDer();
        NodoDic aux2 = aux.getIzq();
        aux.setIzq(nodo);
        nodo.setDer(aux2);
        nodo.setAltura(alturaNodo(nodo));
        aux.setAltura(alturaNodo(aux));
        return aux;
    }

    private int balance(NodoDic raizActual) {
        int res;
        if (raizActual.getIzq() != null) {
            if (raizActual.getDer() != null) {
                res = raizActual.getIzq().getAltura() - raizActual.getDer().getAltura();
            } else {
                res = raizActual.getIzq().getAltura() + 1;
            }
        } else {
            if (raizActual.getDer() != null) {
                res = -1 - raizActual.getDer().getAltura();
            } else {
                res = 0;
            }
        }
        return res;
    }

    public Ciudad recuperarElemento(String clave) {
        return recuperarElementoAux(raiz, clave);
    }

    private Ciudad recuperarElementoAux(NodoDic raizActual, String clave) {
        Ciudad ciudad = null;
        if (raizActual != null) {
            if (!raizActual.getClave().equals(clave)) {
                if (raizActual.getClave().compareTo(clave) > 0) {
                    ciudad = recuperarElementoAux(raizActual.getIzq(), clave);
                } else {
                    ciudad = recuperarElementoAux(raizActual.getDer(), clave);
                }
            } else {
                ciudad = raizActual.getCiudad();
            }
        }
        return ciudad;
    }

    public boolean esVacio() {
        return (raiz == null);
    }

    public int alturaNodo(NodoDic raizActual) {
        return alturaAux(raizActual);
    }

    public int alturaArbol() {
        return alturaAux(raiz);
    }

    private int alturaAux(NodoDic raizActual) {
        int altD = 0, altI = 0, alt;
        if (raizActual != null) {
            if (raizActual.getIzq() != null) {
                altI = 1 + alturaAux(raizActual.getIzq());
            }
            if (raizActual.getDer() != null) {
                altD = 1 + alturaAux(raizActual.getDer());
            }
            alt = (altI >= altD) ? altI : altD;
        } else {
            alt = 0;
        }
        return alt;
    }

    public int nivel(int elem) {
        return 0;
    }

    public void vaciar() {
        raiz = null;
    }

    public void listar() {
        if (raiz != null) {
            listarAux(raiz);
        } else {
            System.out.println("Sin Elem");
        }
    }

    private void listarAux(NodoDic nActual) {
        if (nActual != null) {
            listarAux(nActual.getIzq());
            System.out.print(nActual.getCiudad().getNombre() + ",");
            listarAux(nActual.getDer());
        }
    }

    public void listarAltura() {
        if (raiz != null) {
            listarAlturaAux(raiz);
            System.out.print("\n");
        } else {
            System.out.println("Sin Elem");
        }
    }

    private void listarAlturaAux(NodoDic nActual) {
        if (nActual != null) {
            listarAlturaAux(nActual.getIzq());
            System.out.print(nActual.getCiudad().getNombre() + ",Alt: " + nActual.getAltura() + "\n");
            listarAlturaAux(nActual.getDer());
        }
    }

    public void listarNivel() {
        if (raiz != null) {
            listarNivelAux(raiz);
        } else {
            System.out.println("Sin Elem");
        }
    }

    private void listarNivelAux(NodoDic raizActual) {
        ColaStr cola = new ColaStr();
        cola.poner(raizActual.getClave());
        NodoDic nodoAux = raizActual;
        while (!cola.esVacia()) {
            System.out.print(cola.sacar());

            if (nodoAux.getIzq() != null) {
                cola.poner(nodoAux.getIzq().getClave());
            }
            if (nodoAux.getDer() != null) {
                cola.poner(nodoAux.getDer().getClave());
            }

        }
    }

    public ListaStr listarClavesRango(String inicio, String fin) {
        ListaStr lista = new ListaStr();
        if (raiz != null) {
            listarClavesRangoAux(lista, raiz, inicio, fin);
        }
        return lista;
    }

    private void listarClavesRangoAux(ListaStr lista, NodoDic raizActual, String inicio, String fin) {
        if (inicio.compareTo(raizActual.getClave()) < 0) {
            if (raizActual.getIzq() != null) {
                listarClavesRangoAux(lista, raizActual.getIzq(), inicio, fin);
            }
        }
        if (raizActual.getClave().compareTo(inicio) >= 0 && raizActual.getClave().compareTo(fin) <= 0) {
            lista.insertar(raizActual.getClave());
        }

        if (fin.compareTo(raizActual.getClave()) > 0) {
            if (raizActual.getDer() != null) {
                listarClavesRangoAux(lista, raizActual.getDer(), inicio, fin);
            }
        }
    }

    public void mostrarDic() {
        if (raiz != null) {
            mostrarDicAux(raiz);
        } else {
            System.out.println("Vacio!");
        }
    }

    private void mostrarDicAux(NodoDic raizActual) {
        if (raizActual != null) {
            System.out.print("Padre: " + raizActual.getClave());
            if (raizActual.getIzq() != null) {
                System.out.print("\n HI: " + raizActual.getIzq().getClave());
            }
            if (raizActual.getDer() != null) {
                System.out.print("\n HD: " + raizActual.getDer().getClave());
            }
            System.out.println("\n-----------------------------------");
            mostrarDicAux(raizActual.getIzq());
            mostrarDicAux(raizActual.getDer());
        }
    }
}
