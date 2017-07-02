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
                        }
                        break;
                    default:
                        if (padre != null) {
                            if (padre.getClave().compareTo(nodoElim.getClave()) > 0) {
                                padre.setIzq(null);
                            } else {
                                padre.setDer(null);
                            }
                        } else {
                            raiz = null;
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

    private NodoDic rotacionDerecha(NodoDic pivote) {
        NodoDic hIzq = pivote.getIzq();
        NodoDic aux = hIzq.getDer();
        hIzq.setDer(pivote);
        pivote.setIzq(aux);
        pivote.setAltura(alturaNodo(pivote));
        hIzq.setAltura(alturaNodo(hIzq));
        return hIzq;
    }

    private NodoDic rotacionIzquierda(NodoDic pivote) {
        NodoDic hDer = pivote.getDer();
        NodoDic aux = hDer.getIzq();
        hDer.setIzq(pivote);
        pivote.setDer(aux);
        pivote.setAltura(alturaNodo(pivote));
        hDer.setAltura(alturaNodo(hDer));
        return hDer;
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
            alt = -1;
        }
        return alt;
    }

    public void vaciar() {
        raiz = null;
    }

    public String listarAlfabeticamente() {
        String res = "No existen ciudades!";
        if (raiz != null) {
            res = listarAux(raiz);
        }

        return res;
    }

    private String listarAux(NodoDic nActual) {
        String res = "";
        if (nActual != null) {
            res += listarAux(nActual.getIzq());
            res += nActual.getCiudad().getNombre() + ",";
            res += listarAux(nActual.getDer());
        }

        return res;
    }

    /*public void listarAltura() {
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
    }*/

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
            lista.insertarAlFinal(raizActual.getClave());
        }

        if (fin.compareTo(raizActual.getClave()) > 0) {
            if (raizActual.getDer() != null) {
                listarClavesRangoAux(lista, raizActual.getDer(), inicio, fin);
            }
        }
    }

    @Override
    public String toString() {
        String res = "Vacio!";
        if (raiz != null) {
            res = toStringAux(raiz);
        }
        return res;
    }

    private String toStringAux(NodoDic raizActual) {
        String res = "";
        if (raizActual != null) {
            res += "Padre: " + raizActual.getClave();
            if (raizActual.getIzq() != null) {
                res += "\n HI: " + raizActual.getIzq().getClave();
            }
            if (raizActual.getDer() != null) {
                res += "\n HD: " + raizActual.getDer().getClave();
            }
            res += "\n-----------------------------------\n";
            res += toStringAux(raizActual.getIzq());
            res += toStringAux(raizActual.getDer());
        }
        return res;
    }
}
